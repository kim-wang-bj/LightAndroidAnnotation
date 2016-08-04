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
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.wq.android.lightannotation.annotations.AfterTextChanged;
import com.wq.android.lightannotation.annotations.BeforeTextChanged;
import com.wq.android.lightannotation.annotations.BitmapByFile;
import com.wq.android.lightannotation.annotations.BitmapById;
import com.wq.android.lightannotation.annotations.DrawableByFile;
import com.wq.android.lightannotation.annotations.DrawableById;
import com.wq.android.lightannotation.annotations.FindById;
import com.wq.android.lightannotation.annotations.FindByIds;
import com.wq.android.lightannotation.annotations.FullScreen;
import com.wq.android.lightannotation.annotations.Inflate;
import com.wq.android.lightannotation.annotations.OnCheckedChanged;
import com.wq.android.lightannotation.annotations.OnClick;
import com.wq.android.lightannotation.annotations.OnContextClick;
import com.wq.android.lightannotation.annotations.OnDoubleTap;
import com.wq.android.lightannotation.annotations.OnDoubleTapEvent;
import com.wq.android.lightannotation.annotations.OnDown;
import com.wq.android.lightannotation.annotations.OnDrag;
import com.wq.android.lightannotation.annotations.OnDraw;
import com.wq.android.lightannotation.annotations.OnEditorAction;
import com.wq.android.lightannotation.annotations.OnFling;
import com.wq.android.lightannotation.annotations.OnGlobalLayout;
import com.wq.android.lightannotation.annotations.OnItemClick;
import com.wq.android.lightannotation.annotations.OnItemLongClick;
import com.wq.android.lightannotation.annotations.OnItemSelected;
import com.wq.android.lightannotation.annotations.OnItemSelectedNothing;
import com.wq.android.lightannotation.annotations.OnKey;
import com.wq.android.lightannotation.annotations.OnLongClick;
import com.wq.android.lightannotation.annotations.OnLongPress;
import com.wq.android.lightannotation.annotations.OnPageScrollStateChanged;
import com.wq.android.lightannotation.annotations.OnPageScrolled;
import com.wq.android.lightannotation.annotations.OnPageSelected;
import com.wq.android.lightannotation.annotations.OnPreDraw;
import com.wq.android.lightannotation.annotations.OnScroll;
import com.wq.android.lightannotation.annotations.OnScrollChanged;
import com.wq.android.lightannotation.annotations.OnShowPress;
import com.wq.android.lightannotation.annotations.OnSingleTapConfirmed;
import com.wq.android.lightannotation.annotations.OnSingleTapUp;
import com.wq.android.lightannotation.annotations.OnTextChanged;
import com.wq.android.lightannotation.annotations.OnTouch;
import com.wq.android.lightannotation.annotations.OrientationLandscape;
import com.wq.android.lightannotation.annotations.OrientationPortrait;
import com.wq.android.lightannotation.annotations.OrientationSensor;
import com.wq.android.lightannotation.annotations.SystemService;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by qwang on 2016/8/3.
 */
public final class LightBinder {

    private final static String TAG = LightBinder.class.getSimpleName();
    public static boolean DEBUG = false;

    private LightBinder() {
    }

    public static void bind(Object obj) {
        bind(obj, null);
    }

    public static void bind(Object obj, View view) {
        if (obj == null) {
            throw new NullPointerException("Object parameter must not be null !");
        }
        try {
            long startTime = System.currentTimeMillis();
            Class clazz = obj.getClass();
            log("Bind start: -> " + clazz.getName());
            if (obj instanceof Activity) {
                for (Annotation annotation : clazz.getDeclaredAnnotations()) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) binder.bind(annotation, null, null, obj, view);
                }
            }
            for (Method method : obj.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) binder.bind(annotation, method, null, obj, view);
                }
            }
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) binder.bind(annotation, null, field, obj, view);
                }
            }
            log("Bind finish: (cost " + (System.currentTimeMillis() - startTime) + "ms)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    final static class ActivityFeatureBinder extends Binder {

        @Override
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            Activity activity = (Activity) obj;
            if (annotation instanceof FullScreen) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                log("Bind feature: FullScreen -> " + obj.getClass().getName());
            }
            if (annotation instanceof OrientationPortrait) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                log("Bind feature: OrientationPortrait -> " + obj.getClass().getName());
            } else if (annotation instanceof OrientationLandscape) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                log("Bind feature: OrientationLandscape -> " + obj.getClass().getName());
            } else if (annotation instanceof OrientationSensor) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                log("Bind feature: OrientationSensor -> " + obj.getClass().getName());
            }
        }
    }

    final static class ViewBinder extends Binder {

        @Override
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            if (annotation instanceof FindById) {
                int id = ((FindById) annotation).value();
                field.set(obj, getView(obj, view, id));
                log("Bind view: " + Integer.toHexString(id) + " -> " + field);
            } else if (annotation instanceof FindByIds) {
                ArrayList<View> list = new ArrayList<View>();
                for (int id : ((FindByIds) annotation).value()) {
                    list.add(getView(obj, view, id));
                }
                field.set(obj, list);
                log("Bind views: " + list.size() + " views -> " + field);
            }
        }
    }

    final static class SystemServiceBinder extends Binder {

        @Override
        @SuppressWarnings("WrongConstant")
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            String serviceType = ((SystemService) annotation).value();
            Object service = getContext(null, obj, view).getSystemService(serviceType);
            field.set(obj, service);
            log("Bind SystemService: " + service.getClass().getName() + " -> " + field);
        }
    }

    final static class ResourceBinder extends Binder {

        @Override
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            Context context = getContext(null, obj, view);
            if (annotation instanceof BitmapById) {
                int id = ((BitmapById) annotation).value();
                field.set(obj, BitmapFactory.decodeResource(context.getResources(), id));
                log("Bind BitmapById: " + Integer.toHexString(id) + " -> " + field);
            } else if (annotation instanceof BitmapByFile) {
                String file = ((BitmapByFile) annotation).value();
                field.set(obj, BitmapFactory.decodeFile(file));
                log("Bind BitmapByFile: " + file + " -> " + field);
            } else if (annotation instanceof DrawableById) {
                int id = ((DrawableById) annotation).value();
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
                field.set(obj, new BitmapDrawable(context.getResources(), bitmap));
                log("Bind DrawableById: " + Integer.toHexString(id) + " -> " + field);
            } else if (annotation instanceof DrawableByFile) {
                String file = ((DrawableByFile) annotation).value();
                Bitmap bitmap = BitmapFactory.decodeFile(file);
                field.set(obj, new BitmapDrawable(context.getResources(), bitmap));
                log("Bind DrawableByFile: " + file + " -> " + field);
            } else if (annotation instanceof Inflate) {
                Inflate inflate = (Inflate) annotation;
                int id = inflate.value();
                int parent = inflate.parent();
                View v = LayoutInflater.from(context).inflate(id, (ViewGroup) getView(obj, view, parent));
                field.set(obj, v);
                log("Bind Inflate: " + Integer.toHexString(id) + " -> " + field);
            }
        }
    }

    final static class OnClickBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnClick) annotation).value();
            for (int id : ids) {
                View v = getView(obj, view, id);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoke(method, obj, v);
                    }
                });
                log("Bind OnClick: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnCheckedChangedBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnCheckedChanged) annotation).value();
            for (int id : ids) {
                CompoundButton v = (CompoundButton) getView(obj, view, id);
                v.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        invoke(method, obj, buttonView, isChecked);
                    }
                });
                log("Bind OnCheckedChanged: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnDragBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnDrag) annotation).value();
            for (int id : ids) {
                View v = getView(obj, view, id);
                v.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        Object result = invoke(method, obj, v, event);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
                log("Bind OnDrag: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnLongClickBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnLongClick) annotation).value();
            for (int id : ids) {
                View v = getView(obj, view, id);
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Object result = invoke(method, obj, v);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
                log("Bind OnLongClick: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnItemClickBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnItemClick) annotation).value();
            for (int id : ids) {
                ListView v = (ListView) getView(obj, view, id);
                v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        invoke(method, obj, parent, view, position, id);
                    }
                });
                log("Bind OnItemClick: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnItemLongClickBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnItemLongClick) annotation).value();
            for (int id : ids) {
                ListView v = (ListView) getView(obj, view, id);
                v.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Object result = invoke(method, obj, parent, view, position, id);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
                log("Bind OnItemLongClick: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnEditorActionBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnEditorAction) annotation).value();
            for (int id : ids) {
                View v = getView(obj, view, id);
                ((TextView) v).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Object result = invoke(method, obj, v, actionId, event);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
                log("Bind OnEditorAction: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnTouchBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnTouch) annotation).value();
            for (int id : ids) {
                View v = getView(obj, view, id);
                v.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Object result = invoke(method, obj, v, event);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
                log("Bind OnTouch: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnKeyBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnKey) annotation).value();
            for (int id : ids) {
                View v = getView(obj, view, id);
                v.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        Object result = invoke(method, obj, v, keyCode, event);
                        return result instanceof Boolean ? (Boolean) result : false;
                    }
                });
                log("Bind OnKey: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnPreDrawBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnPreDraw) annotation).value();
            for (int id : ids) {
                final View v = getView(obj, view, id);
                v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        Object result = invoke(method, obj, v);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
                log("Bind OnPreDraw: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnDrawBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnDraw) annotation).value();
            for (int id : ids) {
                final View v = getView(obj, view, id);
                v.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                    @Override
                    public void onDraw() {
                        invoke(method, obj, v);
                    }
                });
                log("Bind OnDraw: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnGlobalLayoutBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnGlobalLayout) annotation).value();
            for (int id : ids) {
                final View v = getView(obj, view, id);
                v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        invoke(method, obj, v);
                    }
                });
                log("Bind OnGlobalLayout: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class OnScrollChangedBinder extends Binder {

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            int[] ids = ((OnScrollChanged) annotation).value();
            for (int id : ids) {
                final View v = getView(obj, view, id);
                v.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        invoke(method, obj, v);
                    }
                });
                log("Bind OnScrollChanged: " + Integer.toHexString(id) + " -> " + method);
            }
        }
    }

    final static class TextWatcherBinder extends Binder {
        private final static Map<View, MyTextWatcher> txtWatcherMap = new WeakHashMap<View, MyTextWatcher>();

        @Override
        void bind(Annotation annotation, final Method method, Field field, final Object obj, View view) throws Exception {
            Method annotationMethod = annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(annotation);
            for (final int id : ids) {
                TextView tv = (TextView) getView(obj, view, id);
                MyTextWatcher textWatcher = txtWatcherMap.get(tv);
                if (textWatcher == null) {
                    textWatcher = new MyTextWatcher(obj, tv);
                    txtWatcherMap.put(tv, textWatcher);
                    tv.addTextChangedListener(textWatcher);
                }
                textWatcher.putMethod(annotation.annotationType(), method);
            }
        }

        final static class MyTextWatcher extends BaseListener implements TextWatcher {

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
    }

    final static class OnPageChangedBinder extends Binder {
        private final static Map<View, MyOnPageChangeListener> pageChangedListenerMap = new WeakHashMap<View, MyOnPageChangeListener>();

        @Override
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            Method annotationMethod = annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(annotation);
            for (final int id : ids) {
                ViewPager v = (ViewPager) getView(obj, view, id);
                MyOnPageChangeListener listener = pageChangedListenerMap.get(v);
                if (listener == null) {
                    listener = new MyOnPageChangeListener(obj, v);
                    pageChangedListenerMap.put(v, listener);
                    v.addOnPageChangeListener(listener);
                }
                listener.putMethod(annotation.annotationType(), method);
            }
        }

        final static class MyOnPageChangeListener extends BaseListener implements ViewPager.OnPageChangeListener {

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
    }

    final static class OnItemSelectedBinder extends Binder {
        private static Map<View, MyItemSelectedListener> itemSelectedListenerMap = new WeakHashMap<View, MyItemSelectedListener>();

        @Override
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            Method annotationMethod = annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(annotation);
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

        final static class MyItemSelectedListener extends BaseListener implements AdapterView.OnItemSelectedListener {

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
    }


    final static class OnGestureBinder extends Binder {
        private static Map<View, MyOnTouchListener> gestureMap = new WeakHashMap<View, MyOnTouchListener>();

        @Override
        void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception {
            Method annotationMethod = annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(annotation);
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

        final static class MyOnTouchListener implements View.OnTouchListener {

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
                log("Bind " + clazz.getSimpleName() + ": -> " + method);
                gestureListener.baseListener.eventMethodMap.put(clazz, method);
            }
        }

        final static class GestureListener extends android.view.GestureDetector.SimpleOnGestureListener {
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
    }

    abstract static class Binder {
        abstract void bind(Annotation annotation, Method method, Field field, Object obj, View view) throws Exception;
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
            log("Bind " + clazz.getSimpleName() + ": -> " + method);
            eventMethodMap.put(clazz, method);
        }

        protected Object innerInvoke(Class<? extends Annotation> annotation, Object... params) {
            Method method = eventMethodMap.get(annotation);
            if (obj.get() == null || method == null) {
                return null;
            }
            return LightBinder.invoke(method, obj.get(), params);
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
            throw new NullPointerException(obj.getClass().getSimpleName() + ".getView() == null when bind events to it.");
        }
        if (obj instanceof View) {
            return ((View) obj).findViewById(id);
        }
        if (obj instanceof Activity) {
            return ((Activity) obj).findViewById(id);
        }
        return null;
    }

    private static Context getContext(Method method, Object obj, View view) throws Exception {
        if (obj instanceof Activity) {
            return ((Activity) obj).getApplicationContext();
        }
        if (obj instanceof View) {
            return ((View) obj).getContext().getApplicationContext();
        }
        if (view != null) {
            return view.getContext().getApplicationContext();
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

    private static Object invoke(Method method, Object obj, Object... params) {
        try {
            log("Invoke: " + method);
            return method.invoke(obj, params);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() == null ? "" : e.getMessage(), e.getCause() == null ? e : e.getCause());
            throw new RuntimeException(e);
        }
    }

    private static void log(String msg) {
        if (DEBUG && msg != null) {
            Log.i(TAG, msg);
        }
    }
}