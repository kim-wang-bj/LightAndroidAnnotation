package com.wq.android.lightannotation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by qwang on 2016/7/5.
 */
public class Injector {

    private static final String TAG = Injector.class.getSimpleName();
    public static boolean DEBUG = false;

    private Injector() {
    }

    public static void inject(Object obj) {
        inject(obj, null);
    }

    public static void inject(Object obj, View view) {
        if (obj == null) {
            throw new NullPointerException("Object parameter must not be null !");
        }
        try {
            long startTime = System.currentTimeMillis();
            log("Injection start: -> " + obj.getClass().getName());
            injectActivityFeature(obj);
            for (Method method : obj.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                injectOnClick(method, obj, view);
                injectOnLongClick(method, obj, view);
                injectOnDrag(method, obj, view);
                injectOnItemClick(method, obj, view);
                injectOnItemLongClick(method, obj, view);
                injectOnItemSelected(method, obj, view);
                injectOnEditorAction(method, obj, view);
                injectOnTouch(method, obj, view);
                injectOnKey(method, obj, view);
                injectGesture(method, obj, view);
                injectTextWatcher(method, obj, view);
                injectOnPreDraw(method, obj, view);
                injectOnDraw(method, obj, view);
                injectOnGlobalLayout(method, obj, view);
                injectOnScrollChanged(method, obj, view);
                injectOnPageChanged(method, obj, view);
            }
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                injectView(field, obj, view);
                injectSystemService(field, obj, view);
                injectResource(field, obj, view);
            }
            log("Injection finish:(cost " + (System.currentTimeMillis() - startTime) + "ms)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void injectActivityFeature(Object obj) {
        if (!(obj instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) obj;
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(FullScreen.class)) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            log("Inject feature: FullScreen -> " + obj.getClass().getName());
        }
        if (clazz.isAnnotationPresent(OrientationPortrait.class)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            log("Inject feature: OrientationPortrait -> " + obj.getClass().getName());
        } else if (clazz.isAnnotationPresent(OrientationLandscape.class)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            log("Inject feature: OrientationLandscape -> " + obj.getClass().getName());
        } else if (clazz.isAnnotationPresent(OrientationSensor.class)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            log("Inject feature: OrientationSensor -> " + obj.getClass().getName());
        }
    }

    private static void injectView(Field field, Object obj, View view) throws Exception {
        FindById annotation = field.getAnnotation(FindById.class);
        if (annotation == null) {
            return;
        }
        int id = annotation.value();
        field.set(obj, getView(obj, view, id));
        log("Inject view: " + Integer.toHexString(id) + " -> " + field);
    }

    @SuppressWarnings("WrongConstant")
    private static void injectSystemService(Field field, Object obj, View view) throws Exception {
        SystemService annotation = field.getAnnotation(SystemService.class);
        if (annotation == null) {
            return;
        }
        String serviceType = annotation.value();
        Object service = getContext(null, obj, view).getSystemService(serviceType);
        field.set(obj, service);
        log("Inject SystemService: " + service.getClass().getName() + " -> " + field);
    }

    private static void injectResource(Field field, Object obj, View view) throws Exception {
        Context context = getContext(null, obj, view);
        BitmapById bitmapById = field.getAnnotation(BitmapById.class);
        if (bitmapById != null) {
            field.set(obj, BitmapFactory.decodeResource(context.getResources(), bitmapById.value()));
            log("Inject BitmapById: " + Integer.toHexString(bitmapById.value()) + " -> " + field);
        }
        BitmapByFile bitmapByFile = field.getAnnotation(BitmapByFile.class);
        if (bitmapByFile != null) {
            field.set(obj, BitmapFactory.decodeFile(bitmapByFile.value()));
            log("Inject BitmapByFile: " + bitmapByFile.value() + " -> " + field);
        }
        DrawableById drawableById = field.getAnnotation(DrawableById.class);
        if (drawableById != null) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableById.value());
            field.set(obj, new BitmapDrawable(context.getResources(), bitmap));
            log("Inject DrawableById: " + Integer.toHexString(drawableById.value()) + " -> " + field);
        }
        DrawableByFile drawableByFile = field.getAnnotation(DrawableByFile.class);
        if (drawableByFile != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(drawableByFile.value());
            field.set(obj, new BitmapDrawable(context.getResources(), bitmap));
            log("Inject DrawableByFile: " + drawableByFile.value() + " -> " + field);
        }
        Inflate inflate = field.getAnnotation(Inflate.class);
        if (inflate != null) {
            View v = LayoutInflater.from(getContext(null, obj, view)).inflate(inflate.value(), null);
            field.set(obj, v);
            log("Inject inflate: " + inflate.value() + " -> " + field);
        }
    }

    private static void injectOnClick(final Method method, final Object obj, View view) throws Exception {
        OnClick annotation = method.getAnnotation(OnClick.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            View v = getView(obj, view, id);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invoke(method, obj, v);
                }
            });
            log("Inject OnClick: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnDrag(final Method method, final Object obj, View view) throws Exception {
        OnDrag annotation = method.getAnnotation(OnDrag.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            View v = getView(obj, view, id);
            v.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    Object result = invoke(method, obj, v, event);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnDrag: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnLongClick(final Method method, final Object obj, View view) throws Exception {
        OnLongClick annotation = method.getAnnotation(OnLongClick.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            View v = getView(obj, view, id);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Object result = invoke(method, obj, v);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnLongClick: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnItemClick(final Method method, final Object obj, View view) throws Exception {
        OnItemClick annotation = method.getAnnotation(OnItemClick.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            ListView v = (ListView) getView(obj, view, id);
            v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    invoke(method, obj, parent, view, position, id);
                }
            });
            log("Inject OnItemClick: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnItemLongClick(final Method method, final Object obj, View view) throws Exception {
        OnItemLongClick annotation = method.getAnnotation(OnItemLongClick.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            ListView v = (ListView) getView(obj, view, id);
            v.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Object result = invoke(method, obj, parent, view, position, id);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnItemLongClick: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnEditorAction(final Method method, final Object obj, View view) throws Exception {
        OnEditorAction annotation = method.getAnnotation(OnEditorAction.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            View v = getView(obj, view, id);
            ((TextView) v).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Object result = invoke(method, obj, v, actionId, event);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnEditorAction: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnTouch(final Method method, final Object obj, View view) throws Exception {
        OnTouch annotation = method.getAnnotation(OnTouch.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            View v = getView(obj, view, id);
            v.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Object result = invoke(method, obj, v, event);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnTouch: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnKey(final Method method, final Object obj, View view) throws Exception {
        OnKey annotation = method.getAnnotation(OnKey.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            View v = getView(obj, view, id);
            v.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    Object result = invoke(method, obj, v, keyCode, event);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnKey: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnPreDraw(final Method method, final Object obj, View view) throws Exception {
        OnPreDraw annotation = method.getAnnotation(OnPreDraw.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            final View v = getView(obj, view, id);
            v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    Object result = invoke(method, obj, v);
                    return result instanceof Boolean ? (Boolean) result : true;
                }
            });
            log("Inject OnPreDraw: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnDraw(final Method method, final Object obj, View view) throws Exception {
        OnDraw annotation = method.getAnnotation(OnDraw.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            final View v = getView(obj, view, id);
            v.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                @Override
                public void onDraw() {
                    invoke(method, obj, v);
                }
            });
            log("Inject OnDraw: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnGlobalLayout(final Method method, final Object obj, View view) throws Exception {
        OnGlobalLayout annotation = method.getAnnotation(OnGlobalLayout.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            final View v = getView(obj, view, id);
            v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    invoke(method, obj, v);
                }
            });
            log("Inject OnGlobalLayout: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static void injectOnScrollChanged(final Method method, final Object obj, View view) throws Exception {
        OnScrollChanged annotation = method.getAnnotation(OnScrollChanged.class);
        if (annotation == null) {
            return;
        }
        int[] ids = annotation.value();
        for (int id : ids) {
            final View v = getView(obj, view, id);
            v.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    invoke(method, obj, v);
                }
            });
            log("Inject OnGlobalLayout: " + Integer.toHexString(id) + " -> " + method);
        }
    }

    private static Map<View, MyTextWatcher> txtWatcherMap = new WeakHashMap<View, MyTextWatcher>();

    private static void injectTextWatcher(final Method method, final Object obj, View view) throws Exception {
        Map<Annotation, Method> annotationMethodMap = new HashMap<Annotation, Method>();
        annotationMethodMap.put(method.getAnnotation(AfterTextChanged.class), method);
        annotationMethodMap.put(method.getAnnotation(BeforeTextChanged.class), method);
        annotationMethodMap.put(method.getAnnotation(OnTextChanged.class), method);

        for (Annotation annotation : annotationMethodMap.keySet()) {
            if (annotation == null) {
                continue;
            }
            int[] ids = (int[]) annotation.getClass().getDeclaredMethod("value").invoke(annotation);
            for (final int id : ids) {
                TextView tv = (TextView) getView(obj, view, id);
                MyTextWatcher textWatcher = txtWatcherMap.get(tv);
                if (textWatcher == null) {
                    textWatcher = new MyTextWatcher(obj, tv);
                    txtWatcherMap.put(tv, textWatcher);
                }
                textWatcher.putMethod(annotation.annotationType(), method);
                tv.addTextChangedListener(textWatcher);
            }
        }
    }

    private static Map<View, MyOnPageChangeListener> pageChangedListenerMap = new WeakHashMap<View, MyOnPageChangeListener>();

    private static void injectOnPageChanged(final Method method, final Object obj, View view) throws Exception {
        Map<Annotation, Method> annotationMethodMap = new HashMap<Annotation, Method>();
        annotationMethodMap.put(method.getAnnotation(OnPageScrolled.class), method);
        annotationMethodMap.put(method.getAnnotation(OnPageSelected.class), method);
        annotationMethodMap.put(method.getAnnotation(OnPageScrollStateChanged.class), method);

        for (Annotation annotation : annotationMethodMap.keySet()) {
            if (annotation == null) {
                continue;
            }
            int[] ids = (int[]) annotation.getClass().getDeclaredMethod("value").invoke(annotation);
            for (final int id : ids) {
                ViewPager v = (ViewPager) getView(obj, view, id);
                MyOnPageChangeListener listener = pageChangedListenerMap.get(v);
                if (listener == null) {
                    listener = new MyOnPageChangeListener(obj, v);
                    pageChangedListenerMap.put(v, listener);
                }
                listener.putMethod(annotation.annotationType(), method);
                v.addOnPageChangeListener(listener);
            }
        }
    }

    private static Map<View, MyItemSelectedListener> itemSelectedListenerMap = new WeakHashMap<View, MyItemSelectedListener>();

    private static void injectOnItemSelected(final Method method, final Object obj, View view) throws Exception {
        Map<Annotation, Method> annotationMethodMap = new HashMap<Annotation, Method>();
        annotationMethodMap.put(method.getAnnotation(OnItemSelected.class), method);
        annotationMethodMap.put(method.getAnnotation(OnItemSelectedNothing.class), method);

        for (Annotation annotation : annotationMethodMap.keySet()) {
            if (annotation == null) {
                continue;
            }
            int[] ids = (int[]) annotation.getClass().getDeclaredMethod("value").invoke(annotation);
            for (final int id : ids) {
                ListView v = (ListView) getView(obj, view, id);
                MyItemSelectedListener listener = itemSelectedListenerMap.get(v);
                if (listener == null) {
                    listener = new MyItemSelectedListener(obj, v);
                    itemSelectedListenerMap.put(v, listener);
                }
                listener.putMethod(annotation.annotationType(), method);
                v.setOnItemSelectedListener(listener);
            }
        }
    }

    private static Map<View, MyOnTouchListener> gestureMap = new WeakHashMap<View, MyOnTouchListener>();

    private static void injectGesture(final Method method, final Object obj, View view) throws Exception {
        Map<Annotation, Method> annotationMethodMap = new HashMap<Annotation, Method>();
        annotationMethodMap.put(method.getAnnotation(OnDoubleTap.class), method);
        annotationMethodMap.put(method.getAnnotation(OnDown.class), method);
        annotationMethodMap.put(method.getAnnotation(OnSingleTapConfirmed.class), method);
        annotationMethodMap.put(method.getAnnotation(OnLongPress.class), method);
        annotationMethodMap.put(method.getAnnotation(OnFling.class), method);
        annotationMethodMap.put(method.getAnnotation(OnSingleTapUp.class), method);
        annotationMethodMap.put(method.getAnnotation(OnScroll.class), method);
        annotationMethodMap.put(method.getAnnotation(OnShowPress.class), method);
        annotationMethodMap.put(method.getAnnotation(OnDoubleTapEvent.class), method);
        annotationMethodMap.put(method.getAnnotation(OnContextClick.class), method);

        for (Annotation annotation : annotationMethodMap.keySet()) {
            if (annotation == null) {
                continue;
            }
            int[] ids = (int[]) annotation.getClass().getDeclaredMethod("value").invoke(annotation);
            for (int id : ids) {
                View v = getView(obj, view, id);
                MyOnTouchListener onTouchListener = gestureMap.get(v);
                if (onTouchListener == null) {
                    onTouchListener = new MyOnTouchListener(getContext(method, obj, view), obj, v);
                    gestureMap.put(v, onTouchListener);
                }
                onTouchListener.putMethod(annotation.annotationType(), method);
                v.setOnTouchListener(onTouchListener);
            }
        }
    }

    private static View getView(Object obj, View view, int id) throws Exception {
        if (view != null && id != View.NO_ID && view.findViewById(id) != null) {
            return view.findViewById(id);
        }
        if (view != null) {
            return view;
        }
        if (obj instanceof Fragment) {
            obj = ((Fragment) obj).getView();
        }
        if (obj instanceof android.support.v4.app.Fragment) {
            obj = ((android.support.v4.app.Fragment) obj).getView();
        }
        if (obj == null) {
            throw new NullPointerException("Fragment.getView() == null when inject events to it.");
        }
        Method method = obj.getClass().getMethod("findViewById", int.class);
        if (method != null && id != View.NO_ID) {
            return (View) method.invoke(obj, id);
        }
        if (obj instanceof View) {
            return (View) obj;
        }
        return null;
    }

    private static Object invoke(Method method, Object obj, Object... params) {
        try {
            log("Invoke: " + method);
            return method.invoke(obj, params);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause() == null ? e : e.getCause());
            throw new RuntimeException(e);
        }
    }

    private static Context getContext(Method method, Object obj, View view) throws Exception {
        if (obj instanceof Activity) {
            return ((Activity) obj).getApplicationContext();
        }
        if (obj instanceof View) {
            return ((View) obj).getContext();
        }
        if (view != null) {
            return view.getContext();
        }
        if (obj instanceof Fragment) {
            Fragment fragment = ((Fragment) obj);
            return fragment.getActivity().getApplicationContext();
        }
        if (obj instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment fragment = ((android.support.v4.app.Fragment) obj);
            return fragment.getActivity().getApplicationContext();
        }
        return null;
    }

    private static class MyOnPageChangeListener extends BaseListener implements ViewPager.OnPageChangeListener {

        MyOnPageChangeListener(Object obj, View view) {
            super(obj, view);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            innerInvoke(OnPageScrolled.class, view.get(), position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            innerInvoke(OnPageSelected.class, view.get(), position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            innerInvoke(OnPageScrollStateChanged.class, view.get(), state);
        }
    }

    private static class MyItemSelectedListener extends BaseListener implements AdapterView.OnItemSelectedListener {

        MyItemSelectedListener(Object obj, View view) {
            super(obj, view);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            innerInvoke(OnItemSelected.class, parent, view, position, id);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            innerInvoke(OnItemSelectedNothing.class, parent);
        }
    }

    private static class MyTextWatcher extends BaseListener implements TextWatcher {

        MyTextWatcher(Object obj, View view) {
            super(obj, view);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            innerInvoke(BeforeTextChanged.class, view.get(), s, start, count, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            innerInvoke(OnTextChanged.class, view.get(), s, start, before, count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            innerInvoke(AfterTextChanged.class, view.get(), s);
        }
    }

    private static class MyOnTouchListener implements View.OnTouchListener {

        private GestureDetector gestureDetector;
        private GestureListener gestureListener;

        private MyOnTouchListener(Context context, Object obj, View view) {
            gestureListener = new GestureListener(obj, view);
            gestureDetector = new GestureDetector(context, gestureListener, new Handler(Looper.getMainLooper()));
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private void putMethod(Class<? extends Annotation> clazz, Method method) {
            log("Inject " + clazz.getSimpleName() + ": -> " + method);
            gestureListener.baseListener.eventMethodMap.put(clazz, method);
        }
    }

    private static class GestureListener extends android.view.GestureDetector.SimpleOnGestureListener {
        BaseListener baseListener;

        private GestureListener(Object obj, View view) {
            baseListener = new BaseListener(obj, view);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return innerInvoke(OnSingleTapUp.class, baseListener.view.get(), e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            innerInvoke(OnLongPress.class, baseListener.view.get(), e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return innerInvoke(OnScroll.class, baseListener.view.get(), e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return innerInvoke(OnFling.class, baseListener.view.get(), e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            innerInvoke(OnShowPress.class, baseListener.view.get(), e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return innerInvoke(OnDown.class, baseListener.view.get(), e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return innerInvoke(OnDoubleTap.class, baseListener.view.get(), e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return innerInvoke(OnDoubleTapEvent.class, baseListener.view.get(), e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return innerInvoke(OnSingleTapConfirmed.class, baseListener.view.get(), e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return innerInvoke(OnContextClick.class, baseListener.view.get(), e);
        }

        private boolean innerInvoke(Class<? extends Annotation> clazz, Object... params) {
            Object result = baseListener.innerInvoke(clazz, params);
            return result instanceof Boolean ? (Boolean) result : true;
        }
    }

    private static class BaseListener {
        protected WeakReference<Object> obj;
        protected WeakReference<View> view;
        protected Map<Class<? extends Annotation>, Method> eventMethodMap = new HashMap<Class<? extends Annotation>, Method>();

        protected BaseListener(Object obj, View view) {
            this.obj = new WeakReference<Object>(obj);
            this.view = new WeakReference<View>(view);
        }

        protected void putMethod(Class<? extends Annotation> clazz, Method method) {
            log("Inject " + clazz.getSimpleName() + ": -> " + method);
            eventMethodMap.put(clazz, method);
        }

        protected Object innerInvoke(Class<? extends Annotation> annotation, Object... params) {
            Method method = eventMethodMap.get(annotation);
            if (obj.get() == null || method == null) {
                return null;
            }
            return Injector.invoke(method, obj.get(), params);
        }
    }

    private static void log(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }
}