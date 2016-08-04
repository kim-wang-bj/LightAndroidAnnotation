package com.wq.android.lightannotation;

import com.wq.android.lightannotation.LightBinder.ActivityFeatureBinder;
import com.wq.android.lightannotation.LightBinder.OnCheckedChangedBinder;
import com.wq.android.lightannotation.LightBinder.OnClickBinder;
import com.wq.android.lightannotation.LightBinder.OnDragBinder;
import com.wq.android.lightannotation.LightBinder.OnDrawBinder;
import com.wq.android.lightannotation.LightBinder.OnEditorActionBinder;
import com.wq.android.lightannotation.LightBinder.OnGestureBinder;
import com.wq.android.lightannotation.LightBinder.OnGlobalLayoutBinder;
import com.wq.android.lightannotation.LightBinder.OnItemClickBinder;
import com.wq.android.lightannotation.LightBinder.OnItemLongClickBinder;
import com.wq.android.lightannotation.LightBinder.OnItemSelectedBinder;
import com.wq.android.lightannotation.LightBinder.OnKeyBinder;
import com.wq.android.lightannotation.LightBinder.OnLongClickBinder;
import com.wq.android.lightannotation.LightBinder.OnPageChangedBinder;
import com.wq.android.lightannotation.LightBinder.OnPreDrawBinder;
import com.wq.android.lightannotation.LightBinder.OnScrollChangedBinder;
import com.wq.android.lightannotation.LightBinder.OnTouchBinder;
import com.wq.android.lightannotation.LightBinder.ResourceBinder;
import com.wq.android.lightannotation.LightBinder.SystemServiceBinder;
import com.wq.android.lightannotation.LightBinder.TextWatcherBinder;
import com.wq.android.lightannotation.LightBinder.ViewBinder;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qwang on 2016/8/4.
 */
final class AnnotationRegister {
    final static Map<Class<? extends Annotation>, LightBinder.Binder> supportedAnnotations = new HashMap<>();

    static {
        TextWatcherBinder textWatcherBinder = new TextWatcherBinder();
        supportedAnnotations.put(AfterTextChanged.class, textWatcherBinder);
        supportedAnnotations.put(BeforeTextChanged.class, textWatcherBinder);
        supportedAnnotations.put(OnTextChanged.class, textWatcherBinder);

        ResourceBinder resourceBinder = new ResourceBinder();
        supportedAnnotations.put(BitmapByFile.class, resourceBinder);
        supportedAnnotations.put(BitmapById.class, resourceBinder);
        supportedAnnotations.put(DrawableByFile.class, resourceBinder);
        supportedAnnotations.put(DrawableById.class, resourceBinder);

        ViewBinder viewBinder = new ViewBinder();
        supportedAnnotations.put(FindById.class, viewBinder);
        supportedAnnotations.put(FindByIds.class, viewBinder);

        supportedAnnotations.put(Inflate.class, resourceBinder);

        supportedAnnotations.put(OnCheckedChanged.class, new OnCheckedChangedBinder());
        supportedAnnotations.put(OnClick.class, new OnClickBinder());
        supportedAnnotations.put(OnDrag.class, new OnDragBinder());
        supportedAnnotations.put(OnDraw.class, new OnDrawBinder());

        OnGestureBinder onGestureBinder = new OnGestureBinder();
        supportedAnnotations.put(OnContextClick.class, onGestureBinder);
        supportedAnnotations.put(OnDoubleTap.class, onGestureBinder);
        supportedAnnotations.put(OnDoubleTapEvent.class, onGestureBinder);
        supportedAnnotations.put(OnDown.class, onGestureBinder);
        supportedAnnotations.put(OnScroll.class, onGestureBinder);
        supportedAnnotations.put(OnFling.class, onGestureBinder);
        supportedAnnotations.put(OnLongPress.class, onGestureBinder);
        supportedAnnotations.put(OnShowPress.class, onGestureBinder);
        supportedAnnotations.put(OnSingleTapConfirmed.class, onGestureBinder);
        supportedAnnotations.put(OnSingleTapUp.class, onGestureBinder);

        supportedAnnotations.put(OnEditorAction.class, new OnEditorActionBinder());
        supportedAnnotations.put(OnGlobalLayout.class, new OnGlobalLayoutBinder());
        supportedAnnotations.put(OnItemClick.class, new OnItemClickBinder());
        supportedAnnotations.put(OnItemLongClick.class, new OnItemLongClickBinder());

        OnItemSelectedBinder onItemSelectedBinder = new OnItemSelectedBinder();
        supportedAnnotations.put(OnItemSelected.class, onItemSelectedBinder);
        supportedAnnotations.put(OnItemSelectedNothing.class, onItemSelectedBinder);

        supportedAnnotations.put(OnKey.class, new OnKeyBinder());
        supportedAnnotations.put(OnLongClick.class, new OnLongClickBinder());

        OnPageChangedBinder onPageChangedBinder = new OnPageChangedBinder();
        supportedAnnotations.put(OnPageScrolled.class, onPageChangedBinder);
        supportedAnnotations.put(OnPageScrollStateChanged.class, onPageChangedBinder);
        supportedAnnotations.put(OnPageSelected.class, onPageChangedBinder);

        supportedAnnotations.put(OnPreDraw.class, new OnPreDrawBinder());
        supportedAnnotations.put(OnScrollChanged.class, new OnScrollChangedBinder());
        supportedAnnotations.put(OnTouch.class, new OnTouchBinder());

        ActivityFeatureBinder activityFeatureBinder = new ActivityFeatureBinder();
        supportedAnnotations.put(FullScreen.class, activityFeatureBinder);
        supportedAnnotations.put(OrientationLandscape.class, activityFeatureBinder);
        supportedAnnotations.put(OrientationPortrait.class, activityFeatureBinder);
        supportedAnnotations.put(OrientationSensor.class, activityFeatureBinder);
        supportedAnnotations.put(SystemService.class, new SystemServiceBinder());
    }
}
