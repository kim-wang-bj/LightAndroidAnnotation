package com.wq.android.lightannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import com.wq.android.lightannotation.annotations.AnimById;
import com.wq.android.lightannotation.annotations.ArrayById;
import com.wq.android.lightannotation.annotations.BeforeTextChanged;
import com.wq.android.lightannotation.annotations.BitmapByFile;
import com.wq.android.lightannotation.annotations.BitmapById;
import com.wq.android.lightannotation.annotations.BoolById;
import com.wq.android.lightannotation.annotations.ColorById;
import com.wq.android.lightannotation.annotations.DimenById;
import com.wq.android.lightannotation.annotations.DrawableByFile;
import com.wq.android.lightannotation.annotations.DrawableById;
import com.wq.android.lightannotation.annotations.FullScreen;
import com.wq.android.lightannotation.annotations.Inflate;
import com.wq.android.lightannotation.annotations.IntById;
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
import com.wq.android.lightannotation.annotations.StringById;
import com.wq.android.lightannotation.annotations.SystemService;
import com.wq.android.lightannotation.annotations.ViewById;
import com.wq.android.lightannotation.annotations.ViewByIds;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by qwang on 2016/8/3.
 */
public final class LightBinder {

    public static boolean DEBUG = false;
    private final static String TAG = LightBinder.class.getSimpleName();
    private static Context context = null;

    static {
        try {
            context = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
            if (context == null) {
                context = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null, (Object[]) null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LightBinder() {
    }

    public static void bind(Object obj) {
        bind(obj, null);
    }

    public static void bind(Object obj, View view) {
        if (obj == null) {
            throw new NullPointerException("Object parameter must not be null !");
        }
        initContextIfNeed(obj, view);
        try {
            long startTime = System.currentTimeMillis();
            Class clazz = obj.getClass();
            if (DEBUG) {
                Log.i(TAG, "Binding start: -> " + clazz.getName());
            }
            if (obj instanceof Activity) {
                for (Annotation annotation : clazz.getDeclaredAnnotations()) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) binder.bind(new BindParams(annotation, obj, view));
                }
            }
            for (Method method : obj.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) binder.bind(new BindParams(annotation, obj, method, view));
                }
            }
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) binder.bind(new BindParams(annotation, obj, field, view));
                }
            }
            if (DEBUG) {
                Log.i(TAG, "Binding finish: (costs " + (System.currentTimeMillis() - startTime) + "ms)");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getCause() != null ? e.getCause() : e);
        }
    }

    private static void initContextIfNeed(Object obj, View view) {
        if (context == null) {
            if (view != null) {
                context = view.getContext().getApplicationContext();
            } else if (obj instanceof Activity) {
                context = ((Activity) obj).getApplicationContext();
            } else if (obj instanceof View) {
                context = ((View) obj).getContext().getApplicationContext();
            } else if (obj instanceof Fragment) {
                context = ((Fragment) obj).getActivity().getApplicationContext();
            } else if (obj instanceof android.support.v4.app.Fragment) {
                context = ((android.support.v4.app.Fragment) obj).getActivity().getApplicationContext();
            }
        }
    }

    final static class ActivityFeatureBinder extends Binder {

        @Override
        Object bind(BindParams params) throws Exception {
            Activity activity = (Activity) params.obj.get();
            if (params.annotation instanceof FullScreen) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            }
            if (params.annotation instanceof OrientationPortrait) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (params.annotation instanceof OrientationLandscape) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (params.annotation instanceof OrientationSensor) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
            return null;
        }
    }

    final static class ViewBinder extends Binder {

        @Override
        Object bind(BindParams params) throws Exception {
            Object result = null;
            if (params.annotation instanceof ViewById) {
                int id = ((ViewById) params.annotation).value();
                result = params.getView(id);
            } else if (params.annotation instanceof ViewByIds) {
                String type = params.paramType != null ? params.paramType.toString() : params.field.getGenericType().toString();
                List<View> list = new ArrayList<View>();
                for (int id : ((ViewByIds) params.annotation).value()) {
                    list.add(params.getView(id));
                }
                if ("class [Landroid.view.View;".equals(type)) {
                    result = list.toArray(new View[list.size()]);
                } else {
                    result = list;
                }
            }
            if (params.field != null) params.field.set(params.obj.get(), result);
            return result;
        }
    }

    final static class SystemServiceBinder extends Binder {

        @Override
        @SuppressWarnings("WrongConstant")
        Object bind(BindParams params) throws Exception {
            String serviceType = ((SystemService) params.annotation).value();
            Object service = context.getSystemService(serviceType);
            if (params.field != null) params.field.set(params.obj.get(), service);
            return service;
        }
    }

    final static class ResourceBinder extends Binder {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        Object bind(BindParams params) throws Exception {
            Object result = null;
            if (params.annotation instanceof BitmapById) {
                result = BitmapFactory.decodeResource(context.getResources(), ((BitmapById) params.annotation).value());
            } else if (params.annotation instanceof BitmapByFile) {
                result = BitmapFactory.decodeFile(((BitmapByFile) params.annotation).value());
            } else if (params.annotation instanceof DrawableById) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), ((DrawableById) params.annotation).value());
                result = new BitmapDrawable(context.getResources(), bitmap);
            } else if (params.annotation instanceof DrawableByFile) {
                String file = ((DrawableByFile) params.annotation).value();
                Bitmap bitmap = BitmapFactory.decodeFile(file);
                result = new BitmapDrawable(context.getResources(), bitmap);
            } else if (params.annotation instanceof Inflate) {
                Inflate inflate = (Inflate) params.annotation;
                int id = inflate.value();
                int parent = inflate.parent();
                result = LayoutInflater.from(context).inflate(id, (ViewGroup) params.getView(parent));
            } else if (params.annotation instanceof ArrayById) {
                int id = ((ArrayById) params.annotation).value();
                String type = params.paramType != null ? params.paramType.toString() : params.field.getGenericType().toString();
                if ("class [Ljava.lang.String;".equals(type)) {
                    result = context.getResources().getStringArray(id);
                } else if ("class [I".equals(type)) {
                    result = context.getResources().getIntArray(id);
                }
            } else if (params.annotation instanceof BoolById) {
                result = context.getResources().getBoolean(((BoolById) params.annotation).value());
            } else if (params.annotation instanceof ColorById) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    result = context.getResources().getColor(((ColorById) params.annotation).value());
                } else {
                    result = context.getResources().getColor(((ColorById) params.annotation).value(), context.getTheme());
                }
            } else if (params.annotation instanceof DimenById) {
                result = context.getResources().getDimension(((DimenById) params.annotation).value());
            } else if (params.annotation instanceof IntById) {
                result = context.getResources().getInteger(((IntById) params.annotation).value());
            } else if (params.annotation instanceof StringById) {
                result = context.getResources().getString(((StringById) params.annotation).value());
            } else if (params.annotation instanceof AnimById) {
                result = context.getResources().getAnimation(((AnimById) params.annotation).value());
            }
            if (params.field != null) params.field.set(params.obj.get(), result);
            return result;
        }
    }

    final static class OnClickBinder extends Binder {
        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnClick) params.annotation).value();
            for (int id : ids) {
                View v = params.getView(id);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoke(params.method, params.obj.get(), v);
                    }
                });
            }
            return null;
        }
    }

    final static class OnCheckedChangedBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnCheckedChanged) params.annotation).value();
            for (int id : ids) {
                CompoundButton v = (CompoundButton) params.getView(id);
                v.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        invoke(params.method, params.obj.get(), buttonView, isChecked);
                    }
                });
            }
            return null;
        }
    }

    final static class OnDragBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnDrag) params.annotation).value();
            for (int id : ids) {
                View v = params.getView(id);
                v.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        Object result = invoke(params.method, params.obj.get(), v, event);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
            }
            return null;
        }
    }

    final static class OnLongClickBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnLongClick) params.annotation).value();
            for (int id : ids) {
                View v = params.getView(id);
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Object result = invoke(params.method, params.obj.get(), v);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
            }
            return null;
        }
    }

    final static class OnItemClickBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnItemClick) params.annotation).value();
            for (int id : ids) {
                ListView v = (ListView) params.getView(id);
                v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        invoke(params.method, params.obj.get(), parent, view, position, id);
                    }
                });
            }
            return null;
        }
    }

    final static class OnItemLongClickBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnItemLongClick) params.annotation).value();
            for (int id : ids) {
                ListView v = (ListView) params.getView(id);
                v.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Object result = invoke(params.method, params.obj.get(), parent, view, position, id);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
            }
            return null;
        }
    }

    final static class OnEditorActionBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnEditorAction) params.annotation).value();
            for (int id : ids) {
                View v = params.getView(id);
                ((TextView) v).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Object result = invoke(params.method, params.obj.get(), v, actionId, event);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
            }
            return null;
        }
    }

    final static class OnTouchBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnTouch) params.annotation).value();
            for (int id : ids) {
                View v = params.getView(id);
                v.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Object result = invoke(params.method, params.obj.get(), v, event);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
            }
            return null;
        }
    }

    final static class OnKeyBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnKey) params.annotation).value();
            for (int id : ids) {
                View v = params.getView(id);
                v.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        Object result = invoke(params.method, params.obj.get(), v, keyCode, event);
                        return result instanceof Boolean ? (Boolean) result : false;
                    }
                });
            }
            return null;
        }
    }

    final static class OnPreDrawBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnPreDraw) params.annotation).value();
            for (int id : ids) {
                final View v = params.getView(id);
                v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        Object result = invoke(params.method, params.obj.get(), v);
                        return result instanceof Boolean ? (Boolean) result : true;
                    }
                });
            }
            return null;
        }
    }

    final static class OnDrawBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnDraw) params.annotation).value();
            for (int id : ids) {
                final View v = params.getView(id);
                v.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                    @Override
                    public void onDraw() {
                        invoke(params.method, params.obj.get(), v);
                    }
                });
            }
            return null;
        }
    }

    final static class OnGlobalLayoutBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnGlobalLayout) params.annotation).value();
            for (int id : ids) {
                final View v = params.getView(id);
                v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        invoke(params.method, params.obj.get(), v);
                    }
                });
            }
            return null;
        }
    }

    final static class OnScrollChangedBinder extends Binder {

        @Override
        Object bind(final BindParams params) throws Exception {
            int[] ids = ((OnScrollChanged) params.annotation).value();
            for (int id : ids) {
                final View v = params.getView(id);
                v.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        invoke(params.method, params.obj.get(), v);
                    }
                });
            }
            return null;
        }
    }

    final static class TextWatcherBinder extends Binder {
        private final static Map<View, MyTextWatcher> txtWatcherMap = new WeakHashMap<View, MyTextWatcher>();

        @Override
        Object bind(BindParams params) throws Exception {
            Method annotationMethod = params.annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(params.annotation);
            for (final int id : ids) {
                TextView tv = (TextView) params.getView(id);
                MyTextWatcher textWatcher = txtWatcherMap.get(tv);
                if (textWatcher == null) {
                    textWatcher = new MyTextWatcher(params.obj.get(), tv);
                    txtWatcherMap.put(tv, textWatcher);
                    tv.addTextChangedListener(textWatcher);
                }
                textWatcher.putMethod(params.annotation.annotationType(), params.method);
            }
            return null;
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
        Object bind(BindParams params) throws Exception {
            Method annotationMethod = params.annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(params.annotation);
            for (final int id : ids) {
                ViewPager v = (ViewPager) params.getView(id);
                MyOnPageChangeListener listener = pageChangedListenerMap.get(v);
                if (listener == null) {
                    listener = new MyOnPageChangeListener(params.obj.get(), v);
                    pageChangedListenerMap.put(v, listener);
                    v.addOnPageChangeListener(listener);
                }
                listener.putMethod(params.annotation.annotationType(), params.method);
            }
            return null;
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
        private final static Map<View, MyItemSelectedListener> itemSelectedListenerMap = new WeakHashMap<View, MyItemSelectedListener>();

        @Override
        Object bind(BindParams params) throws Exception {
            Method annotationMethod = params.annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(params.annotation);
            for (final int id : ids) {
                ListView v = (ListView) params.getView(id);
                MyItemSelectedListener listener = itemSelectedListenerMap.get(v);
                if (listener == null) {
                    listener = new MyItemSelectedListener(params.obj.get(), v);
                    itemSelectedListenerMap.put(v, listener);
                }
                listener.putMethod(params.annotation.annotationType(), params.method);
                v.setOnItemSelectedListener(listener);
            }
            return null;
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
        private final static Map<View, MyOnTouchListener> gestureMap = new WeakHashMap<View, MyOnTouchListener>();

        @Override
        Object bind(BindParams params) throws Exception {
            Method annotationMethod = params.annotation.getClass().getDeclaredMethod("value");
            annotationMethod.setAccessible(true);
            int[] ids = (int[]) annotationMethod.invoke(params.annotation);
            for (int id : ids) {
                View v = params.getView(id);
                MyOnTouchListener onTouchListener = gestureMap.get(v);
                if (onTouchListener == null) {
                    onTouchListener = new MyOnTouchListener(context, params.obj.get(), v);
                    gestureMap.put(v, onTouchListener);
                }
                onTouchListener.putMethod(params.annotation.annotationType(), params.method);
                v.setOnTouchListener(onTouchListener);
            }
            return null;
        }

        final static class MyOnTouchListener implements View.OnTouchListener {

            private final GestureDetector gestureDetector;
            private final GestureListener gestureListener;

            private MyOnTouchListener(Context context, Object obj, View view) {
                gestureListener = new GestureListener(obj, view);
                gestureDetector = new GestureDetector(context, gestureListener, new Handler(Looper.getMainLooper()));
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }

            private void putMethod(Class<? extends Annotation> clazz, Method method) {
                gestureListener.baseListener.eventMethodMap.put(clazz, method);
            }
        }

        final static class GestureListener extends android.view.GestureDetector.SimpleOnGestureListener {
            final BaseListener baseListener;

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
        abstract Object bind(BindParams params) throws Exception;
    }

    private static class BaseListener {
        final WeakReference<Object> obj;
        final WeakReference<View> view;
        final Map<Class<? extends Annotation>, Method> eventMethodMap = new HashMap<Class<? extends Annotation>, Method>();

        BaseListener(Object obj, View view) {
            this.obj = new WeakReference<Object>(obj);
            this.view = new WeakReference<View>(view);
        }

        void putMethod(Class<? extends Annotation> clazz, Method method) {
            eventMethodMap.put(clazz, method);
        }

        Object innerInvoke(Class<? extends Annotation> annotation, Object... params) {
            Method method = eventMethodMap.get(annotation);
            if (method == null) {
                return null;
            }
            return LightBinder.invoke(method, obj.get(), params);
        }
    }

    private final static class BindParams {
        final Annotation annotation;
        final Method method;
        final Field field;
        final WeakReference<Object> obj;
        final WeakReference<View> view;
        final Class<?> paramType;

        BindParams(Annotation annotation, Object obj, View view) {
            this(annotation, obj, null, null, view, null);
        }

        BindParams(Annotation annotation, Object obj, Method method, View view) {
            this(annotation, obj, method, null, view, null);
        }

        BindParams(Annotation annotation, Object obj, Field field, View view) {
            this(annotation, obj, null, field, view, null);
        }

        BindParams(Annotation annotation, Object obj, Class<?> paramType) {
            this(annotation, obj, null, null, null, paramType);
        }

        BindParams(Annotation annotation, Object obj, Method method, Field field, View view, Class<?> paramType) {
            this.annotation = annotation;
            this.obj = new WeakReference<Object>(obj);
            this.method = method;
            this.field = field;
            this.view = new WeakReference<View>(view);
            this.paramType = paramType;
            if (DEBUG) {
                String msg = method != null ? method.toGenericString() : field != null ? field.toGenericString() : paramType != null ? paramType.getName() : "";
                Log.i(TAG, "Bind " + annotation.annotationType().getSimpleName() + ": -> " + msg);
            }
        }

        private View getView(int id) {
            if (view.get() != null && id != View.NO_ID && view.get().findViewById(id) != null) {
                return view.get().findViewById(id);
            }
            if (view.get() != null) {
                return view.get();
            }
            if (obj.get() instanceof Activity) {
                return ((Activity) obj.get()).findViewById(id);
            }
            View view = null;
            if (obj.get() instanceof Fragment) {
                view = ((Fragment) obj.get()).getView();
            }
            if (obj.get() instanceof android.support.v4.app.Fragment) {
                view = ((android.support.v4.app.Fragment) obj.get()).getView();
            }
            if (view == null) {
                throw new NullPointerException(obj.getClass().getSimpleName() + ".getView() == null when bind events to it.");
            }
            if (obj.get() instanceof View) {
                return ((View) obj.get()).findViewById(id);
            }
            if (view == null) {
                throw new NullPointerException("Find view failed: id=" + Integer.toHexString(id) + ". Wrong view id used.");
            }
            return null;
        }
    }

    private static Object invoke(Method method, Object obj, Object... params) {
        try {
            long startTime = System.currentTimeMillis();
            Object result = method.invoke(obj, assembleParams(method, obj, params));
            if (DEBUG) {
                Log.i(TAG, "Invoke ->" + method.toGenericString() + " (costs " + (System.currentTimeMillis() - startTime) + "ms)");
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() == null ? "" : e.getMessage(), e.getCause() == null ? e : e.getCause());
            throw new RuntimeException(e);
        }
    }

    private static Object[] assembleParams(Method method, Object obj, Object... params) throws Exception {
        List<Object> result = new ArrayList<Object>();
        List<Object> paramsList = new LinkedList<>();
        Collections.addAll(paramsList, params);
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0, size = types.length; i < size; i++) {
            Class<?> clazz = types[i];
            Annotation[] paramAnnotations = annotations[i];
            if (paramAnnotations != null && paramAnnotations.length > 0) {
                for (Annotation annotation : paramAnnotations) {
                    Binder binder = AnnotationRegister.supportedAnnotations.get(annotation.annotationType());
                    if (binder != null) {
                        result.add(binder.bind(new BindParams(annotation, obj, clazz)));
                        break;
                    }
                }
                continue;
            }
            for (Object o : paramsList) {
                boolean isAssignable = clazz.isAssignableFrom(o.getClass());
                boolean isPrimitiveEquals = clazz.isPrimitive() && o.getClass().getName().toLowerCase().contains(clazz.getName().toLowerCase());
                if (isAssignable || isPrimitiveEquals) {
                    result.add(o);
                    paramsList.remove(o);
                    break;
                }
            }
        }
        return result.toArray();
    }
}