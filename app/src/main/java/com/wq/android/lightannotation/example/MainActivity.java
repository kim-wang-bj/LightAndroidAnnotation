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
import com.wq.android.lightannotation.BitmapById;
import com.wq.android.lightannotation.DrawableById;
import com.wq.android.lightannotation.FindById;
import com.wq.android.lightannotation.FullScreen;
import com.wq.android.lightannotation.Inflate;
import com.wq.android.lightannotation.Injector;
import com.wq.android.lightannotation.OnClick;
import com.wq.android.lightannotation.OnDoubleTap;
import com.wq.android.lightannotation.OnDown;
import com.wq.android.lightannotation.OnFling;
import com.wq.android.lightannotation.OnLongClick;
import com.wq.android.lightannotation.OnLongPress;
import com.wq.android.lightannotation.OnSingleTapUp;
import com.wq.android.lightannotation.OrientationPortrait;
import com.wq.android.lightannotation.SystemService;

@FullScreen
@OrientationPortrait
public class MainActivity extends Activity {

    @FindById(R.id.btn_on_double_tap) Button btn;
    @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager;
    @DrawableById(R.mipmap.ic_launcher) Drawable drawable;
    @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap;
    @Inflate(R.layout.activity_main) View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.DEBUG = true;
        Injector.inject(this);
        getFragmentManager().beginTransaction().add(new MyFragment(), "").commit();
    }

    @AfterTextChanged(R.id.edit_text)
    private void onEditTextChanged(View v, Editable editable) {
        toast("AfterTextChanged: " + editable.toString());
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

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
