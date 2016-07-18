# LightAndroidAnnotation

    1. Import libs/light-android-annotation.jar to your project.
    2. Invoke Injector.inject(obj extends Activity or View or Fragment)
    3. Or Injector.inject(Obj, view)
    4. Enjoy.

```java
@FullScreen
@OrientationPortrait
public class MainActivity extends Activity {

    @FindById(R.id.btn_on_double_tap) Button btn;
    @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager;
    @DrawableById(R.mipmap.ic_launcher) Drawable drawable;
    @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.DEBUG = true;
        Injector.inject(this);
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
```

```java
AfterTextChanged
BeforeTextChanged
BitmapByFile
BitmapById
DrawableByFile
DrawableById
FindById
FullScreen
Injector
OnClick
OnContextClick
OnDoubleTap
OnDoubleTapEvent
OnDown
OnDrag
OnDraw
OnEditorAction
OnFling
OnGlobalLayout
OnItemClick
OnItemLongClick
OnItemSelected
OnItemSelectedNothing
OnKey
OnLongClick
OnLongPress
OnPreDraw
OnScroll
OnScrollChanged
OnShowPress
OnSingleTapConfirmed
OnSingleTapUp
OnTextChanged
OnTouch
OrientationLandscape
OrientationPortrait
OrientationSensor
SystemService
```
