package com.wq.android.lightannotation.example;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wq.android.lightannotation.LightBinder;
import com.wq.android.lightannotation.annotations.AfterTextChanged;
import com.wq.android.lightannotation.annotations.BeforeTextChanged;
import com.wq.android.lightannotation.annotations.BindArray;
import com.wq.android.lightannotation.annotations.BindBool;
import com.wq.android.lightannotation.annotations.BindColor;
import com.wq.android.lightannotation.annotations.BindDimen;
import com.wq.android.lightannotation.annotations.BindInt;
import com.wq.android.lightannotation.annotations.BindString;
import com.wq.android.lightannotation.annotations.BitmapById;
import com.wq.android.lightannotation.annotations.DrawableById;
import com.wq.android.lightannotation.annotations.FindById;
import com.wq.android.lightannotation.annotations.FindByIds;
import com.wq.android.lightannotation.annotations.FullScreen;
import com.wq.android.lightannotation.annotations.Inflate;
import com.wq.android.lightannotation.annotations.OnCheckedChanged;
import com.wq.android.lightannotation.annotations.OnClick;
import com.wq.android.lightannotation.annotations.OnDoubleTap;
import com.wq.android.lightannotation.annotations.OnDown;
import com.wq.android.lightannotation.annotations.OnDraw;
import com.wq.android.lightannotation.annotations.OnFling;
import com.wq.android.lightannotation.annotations.OnGlobalLayout;
import com.wq.android.lightannotation.annotations.OnKey;
import com.wq.android.lightannotation.annotations.OnLongClick;
import com.wq.android.lightannotation.annotations.OnLongPress;
import com.wq.android.lightannotation.annotations.OnSingleTapConfirmed;
import com.wq.android.lightannotation.annotations.OnSingleTapUp;
import com.wq.android.lightannotation.annotations.OnTextChanged;
import com.wq.android.lightannotation.annotations.OrientationPortrait;
import com.wq.android.lightannotation.annotations.SystemService;

import java.util.Collection;

@FullScreen
@OrientationPortrait
public class MainActivity extends Activity {

    @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager;
    @DrawableById(R.mipmap.ic_launcher) Drawable drawable;
    @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap;
    @Inflate(R.layout.test_inflate) View v;
    @Inflate(value = R.layout.test_inflate, parent = R.id.root) View v1;
    @BindArray(R.array.array) String[] array;
    @BindArray(R.array.string_array) String[] array1;
    @BindArray(R.array.int_array) int[] intArray;
    @BindColor(R.color.colorAccent) int color;
    @BindDimen(R.dimen.activity_horizontal_margin) float margin;
    @BindInt(R.integer.test_int) int integer;
    @BindString(R.string.app_name) String name;
    @BindBool(R.bool.test_bool) boolean bool;

    @FindById(R.id.btn_on_double_tap) Button btn;
    @FindByIds({R.id.btn_click, R.id.btn_long_click}) View[] views;
    @FindByIds({R.id.btn_click, R.id.btn_long_click}) Collection<View> views1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LightBinder.DEBUG = true;
        LightBinder.bind(this);
        getFragmentManager().beginTransaction().add(new MyFragment(), "").commit();
    }

    @OnDraw(R.id.root)
    private void onDraw(View v) {
        //toast("OnDraw");
    }

//    @OnPreDraw(R.id.root)
//    private void onPreDraw(View v) {
//        //toast("OnPreDraw");
//    }

    @OnGlobalLayout(R.id.root)
    private void onGlobalLayout(View v) {
        //toast("OnGlobalLayout");
    }

    @OnKey(R.id.edit_text)
    private void onKey(View v, int keyCode, KeyEvent event) {
        //toast("OnKey");
    }

    @AfterTextChanged(R.id.edit_text)
    private void AfterTextChanged(View v, Editable editable) {
        //toast("AfterTextChanged: " + editable.toString());
    }

    @BeforeTextChanged(R.id.edit_text)
    private void BeforeTextChanged(View v, CharSequence s, int start, int count, int after) {
        toast("BeforeTextChanged");
    }

    @OnTextChanged(R.id.edit_text)
    private void OnTextChanged(View v, CharSequence s, int start, int count, int after) {
        //toast("OnTextChanged");
    }

    @OnClick(R.id.btn_click)
    private void onClick(View v,
                         @BindArray(R.array.array) String[] array,
                         @BindArray(R.array.string_array) String[] array1,
                         @BindArray(R.array.int_array) int[] intArray,
                         @BindColor(R.color.colorAccent) int color,
                         @BindDimen(R.dimen.activity_horizontal_margin) float margin,
                         @BindInt(R.integer.test_int) int integer,
                         @BindString(R.string.app_name) String name,
                         @BindBool(R.bool.test_bool) boolean bool,
                         @FindById(R.id.btn_on_double_tap) Button btn,
                         @FindByIds({R.id.btn_click, R.id.btn_long_click}) View[] views,
                         @FindByIds({R.id.btn_click, R.id.btn_long_click}) Collection<View> views1,
                         @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager,
                         @DrawableById(R.mipmap.ic_launcher) Drawable drawable,
                         @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap,
                         @Inflate(R.layout.test_inflate) View v1,
                         @Inflate(value = R.layout.test_inflate, parent = R.id.root) View v2) {
        toast("OnClick");
    }

    @OnLongClick(R.id.btn_long_click)
    private void onLongClick(View v) {
        toast("OnLongClick");
    }

    @OnDown(R.id.btn_down)
    private void onDown(View v, MotionEvent e) {
        toast("OnDown");
    }

    @OnLongPress(R.id.btn_long_press)
    private void onLongPress(View v, MotionEvent e) {
        toast("OnLongPress");
    }

    @OnSingleTapConfirmed(R.id.btn_single_tap_confirmed)
    private void onSingleTapConfirmed(View v, MotionEvent e) {
        toast("OnSingleTapConfirmed");
    }

    @OnDoubleTap(R.id.btn_on_double_tap)
    private void onDoubleTap(View v, MotionEvent e) {
        toast("OnDoubleTap");
    }

    @OnFling(R.id.btn_fling)
    private void onFling(View v, MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        toast("OnFling: velocityX=" + velocityX + ", velocityY=" + velocityY);
    }

    @OnSingleTapUp(R.id.btn_single_tap_up)
    private void onSingleTapUp(View v, MotionEvent e) {
        toast("OnSingleTapUp");
    }

    @OnCheckedChanged(R.id.checkbox)
    private void onCheckedChanged(View v, boolean checked) {
        toast("onCheckedChanged: " + checked);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
