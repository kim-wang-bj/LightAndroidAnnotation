# LightAndroidAnnotation

    A small Android annotation library.

### Start
    1. Import libs/light-android-annotation.jar to your project.
    2. Invoke LightBinder.bind(Object) Or LightBinder.bind(Object, View)
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
    @Inflate(value = R.layout.test_inflate, parent = R.id.root) View v;
    @FindByIds({R.id.btn_touch_gesture, R.id.btn_long_click_and_click}) List<View> views;

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
AfterTextChanged
BeforeTextChanged
BitmapByFile
BitmapById
DrawableByFile
DrawableById
FindById
FindByIds
FullScreen
Inflate
OnCheckedChanged
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
OnPageScrolled
OnPageScrollStateChanged
OnPageSelected
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
