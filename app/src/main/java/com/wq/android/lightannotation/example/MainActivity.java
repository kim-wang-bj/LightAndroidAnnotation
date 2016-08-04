package com.wq.android.lightannotation.example;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wq.android.lightannotation.AfterTextChanged;
import com.wq.android.lightannotation.BeforeTextChanged;
import com.wq.android.lightannotation.BitmapById;
import com.wq.android.lightannotation.DrawableById;
import com.wq.android.lightannotation.FindById;
import com.wq.android.lightannotation.FindByIds;
import com.wq.android.lightannotation.FullScreen;
import com.wq.android.lightannotation.Inflate;
import com.wq.android.lightannotation.LightBinder;
import com.wq.android.lightannotation.OnCheckedChanged;
import com.wq.android.lightannotation.OnClick;
import com.wq.android.lightannotation.OnDoubleTap;
import com.wq.android.lightannotation.OnDown;
import com.wq.android.lightannotation.OnFling;
import com.wq.android.lightannotation.OnLongClick;
import com.wq.android.lightannotation.OnLongPress;
import com.wq.android.lightannotation.OnSingleTapUp;
import com.wq.android.lightannotation.OnTextChanged;
import com.wq.android.lightannotation.OrientationPortrait;
import com.wq.android.lightannotation.SystemService;

import java.util.List;

@FullScreen
@OrientationPortrait
public class MainActivity extends Activity {

    @FindById(R.id.btn_on_double_tap) Button btn;
    @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager;
    @DrawableById(R.mipmap.ic_launcher) Drawable drawable;
    @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap;
    @Inflate(R.layout.test_inflate) View v;
    @Inflate(value = R.layout.test_inflate, parent = R.id.root) View v1;

    @FindByIds({R.id.btn_touch_gesture, R.id.btn_long_click_and_click}) List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LightBinder.DEBUG = true;
        LightBinder.bind(this);
        getFragmentManager().beginTransaction().add(new MyFragment(), "").commit();
    }

    @AfterTextChanged(R.id.edit_text)
    private void AfterTextChanged(View v, Editable editable) {
        //toast("AfterTextChanged: " + editable.toString());
    }

    @BeforeTextChanged(R.id.edit_text)
    private void BeforeTextChanged(View v, CharSequence s, int a, int b, int c) {
        //toast("BeforeTextChanged");
    }

    @OnTextChanged(R.id.edit_text)
    private void OnTextChanged(View v, CharSequence s, int a, int b, int c) {
        //toast("OnTextChanged");
    }

    @OnClick(R.id.btn_long_click_and_click)
    @OnLongClick(R.id.btn_long_click_and_click)
    private void onClickAndLongClick(View v) {
        toast("OnClick/OnLongClick");
    }

    @OnDown(R.id.btn_touch_gesture)
    private void onDown(View v, MotionEvent e) {
        toast("OnDown");
    }

    @OnLongPress(R.id.btn_touch_gesture)
    private void onLongPress(View v, MotionEvent e) {
        toast("OnLongPress");
    }

    @OnDoubleTap(R.id.btn_on_double_tap)
    private void onDoubleTap(View v, MotionEvent e) {
        toast("OnDoubleTap");
    }

    @OnFling(R.id.btn_touch_gesture)
    private void onFling(View v, MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        toast("OnFling: velocityX=" + velocityX + ", velocityY=" + velocityY);
    }

    @OnSingleTapUp(R.id.btn_touch_gesture)
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
