# LightAndroidAnnotation

    A small Android annotation library.

### Start
    1. Import libs/light-android-annotation.jar to your project.
    2. Invoke LightBinder.bind(Object) Or LightBinder.bind(Object, View)
    3. Enjoy.
### Usage Example

```java
//Supports method parameters binding:
@OnClick(R.id.btn_click)
private void onClick(View v,
                     @ArrayById(R.array.array) String[] array,
                     @ArrayById(R.array.string_array) String[] array1,
                     @ArrayById(R.array.int_array) int[] intArray,
                     @ColorById(R.color.colorAccent) int color,
                     @DimenById(R.dimen.activity_horizontal_margin) float margin,
                     @IntById(R.integer.test_int) int integer,
                     @StringById(R.string.app_name) String name,
                     @BoolById(R.bool.test_bool) boolean bool,
                     @ViewById(R.id.btn_on_double_tap) Button btn,
                     @ViewByIds({R.id.btn_click, R.id.btn_long_click}) View[] views,
                     @ViewByIds({R.id.btn_click, R.id.btn_down}) Collection<View> views1,
                     @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager,
                     @DrawableById(R.mipmap.ic_launcher) Drawable drawable,
                     @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap,
                     @Inflate(R.layout.test_inflate) View v1,
                     @Inflate(value = R.layout.test_inflate, parent = R.id.root) View v2) {
    // do onClick
}

```

```java
@FullScreen
@OrientationPortrait
public class MainActivity extends Activity {

    @SystemService(Context.ALARM_SERVICE) AlarmManager alarmManager;
    @DrawableById(R.mipmap.ic_launcher) Drawable drawable;
    @BitmapById(R.mipmap.ic_launcher) Bitmap bitmap;
    @Inflate(R.layout.test_inflate) View v;
    @Inflate(value = R.layout.test_inflate, parent = R.id.root) View v1;
    @ArrayById(R.array.array) String[] array;
    @ArrayById(R.array.string_array) String[] array1;
    @ArrayById(R.array.int_array) int[] intArray;
    @ColorById(R.color.colorAccent) int color;
    @DimenById(R.dimen.activity_horizontal_margin) float margin;
    @IntById(R.integer.test_int) int integer;
    @StringById(R.string.app_name) String name;
    @BoolById(R.bool.test_bool) boolean bool;

    @ViewById(R.id.btn_on_double_tap) Button btn;
    @ViewByIds({R.id.btn_click, R.id.btn_long_click}) View[] views;
    @ViewByIds({R.id.btn_click, R.id.btn_long_click}) Collection<View> views1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.DEBUG = true;
        Injector.inject(this);
    }

   @OnDraw(R.id.root)
    private void onDraw(View v) {
        //toast("OnDraw");
    }

    @OnPreDraw(R.id.root)
    private void onPreDraw(View v) {
        //toast("OnPreDraw");
    }

    @OnGlobalLayout(R.id.root)
    private void onGlobalLayout(View v) {
        //toast("OnGlobalLayout");
    }

    @OnKey(R.id.edit_text)
    private void onKey(View v, int keyCode, KeyEvent event) {
        toast("OnKey");
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

    @OnClick(R.id.btn_click)
    private void onClick(View v) {
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
```
###Supported Annotations
```java
AfterTextChanged
AnimById
ArrayById
BeforeTextChanged
BitmapByFile
BitmapById
BoolById
ColorById
DimenById
DrawableByFile
DrawableById
FullScreen
Inflate
IntById
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
StringById
SystemService
ViewById
ViewByIds
```
