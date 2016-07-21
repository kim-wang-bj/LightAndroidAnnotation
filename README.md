# LightAndroidAnnotation

    A smallest and fastest Android annotation library.

### Start
    1. Import libs/light-android-annotation.jar to your project.
    2. Invoke Injector.inject(Object extends Activity or View or Fragment or android.support.v4.app.Fragment) Or Injector.inject(Object, View)
    3. Enjoy.
### Usage Example
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
###Supported Annotations
```java
DrawableByFile
OnItemClick
Inflate
OnEditorAction
FindById
DrawableById
OnKey
OnScrollChanged
OnPreDraw
OnItemSelected
BitmapById
OnScroll
OnDown
OnShowPress
OnContextClick
OnDraw
OnDrag
OnFling
OrientationLandscape
OnSingleTapUp
OrientationSensor
AfterTextChanged
OnTextChanged
OnTouch
OnLongPress
OnItemSelectedNothing
OnGlobalLayout
OnDoubleTapEvent
OnClick
OnSingleTapConfirmed
OnDoubleTap
OnItemLongClick
OnPageScrollStateChanged
SystemService
FullScreen
BeforeTextChanged
OnPageSelected
BitmapByFile
OnPageScrolled
OnLongClick
OrientationPortrait
```
