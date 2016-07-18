package com.wq.android.lightannotation;

import android.content.Context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qwang on 2016/7/15.
 *
 * @see Context#WINDOW_SERVICE
 * @see android.view.WindowManager
 * @see Context#LAYOUT_INFLATER_SERVICE
 * @see android.view.LayoutInflater
 * @see Context#ACTIVITY_SERVICE
 * @see android.app.ActivityManager
 * @see Context#POWER_SERVICE
 * @see android.os.PowerManager
 * @see Context#ALARM_SERVICE
 * @see android.app.AlarmManager
 * @see Context#NOTIFICATION_SERVICE
 * @see android.app.NotificationManager
 * @see Context#KEYGUARD_SERVICE
 * @see android.app.KeyguardManager
 * @see Context#LOCATION_SERVICE
 * @see android.location.LocationManager
 * @see Context#SEARCH_SERVICE
 * @see android.app.SearchManager
 * @see Context#SENSOR_SERVICE
 * @see android.hardware.SensorManager
 * @see Context#STORAGE_SERVICE
 * @see android.os.storage.StorageManager
 * @see Context#VIBRATOR_SERVICE
 * @see android.os.Vibrator
 * @see Context#CONNECTIVITY_SERVICE
 * @see android.net.ConnectivityManager
 * @see Context#WIFI_SERVICE
 * @see android.net.wifi.WifiManager
 * @see Context#AUDIO_SERVICE
 * @see android.media.AudioManager
 * @see Context#MEDIA_ROUTER_SERVICE
 * @see android.media.MediaRouter
 * @see Context#TELEPHONY_SERVICE
 * @see android.telephony.TelephonyManager
 * @see Context#TELEPHONY_SUBSCRIPTION_SERVICE
 * @see android.telephony.SubscriptionManager
 * @see Context#CARRIER_CONFIG_SERVICE
 * @see android.telephony.CarrierConfigManager
 * @see Context#INPUT_METHOD_SERVICE
 * @see android.view.inputmethod.InputMethodManager
 * @see Context#UI_MODE_SERVICE
 * @see android.app.UiModeManager
 * @see Context#DOWNLOAD_SERVICE
 * @see android.app.DownloadManager
 * @see Context#BATTERY_SERVICE
 * @see android.os.BatteryManager
 * @see Context#JOB_SCHEDULER_SERVICE
 * @see android.app.job.JobScheduler
 * @see Context#NETWORK_STATS_SERVICE
 * @see android.app.usage.NetworkStatsManager
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemService {
    public String value();
}
