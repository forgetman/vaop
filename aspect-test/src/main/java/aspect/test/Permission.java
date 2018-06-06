package aspect.test;

import android.Manifest.permission;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        Permission.CALENDAR,
        Permission.CAMERA,
        Permission.CONTACTS,
        Permission.LOCATION,
        Permission.RECORD_AUDIO,
        Permission.PHONE_STATE,
        Permission.SENSORS,
        Permission.SMS,
        Permission.STORAGE,
})
@Retention(RetentionPolicy.SOURCE)
/**
 * 按组申请
 * 同一组的任何一个权限被授权了，其他权限也自动被授权, 拒绝同理
 * PS: 某些手机厂商比如小米乐视等，对于权限组有自己的策略
 */
public @interface Permission {

    /**
     * {@link permission#READ_CALENDAR}
     * {@link permission#WRITE_CALENDAR}
     */
    String CALENDAR = permission.READ_CALENDAR;

    String CAMERA = permission.CAMERA;

    /**
     * {@link permission#READ_CONTACTS}
     * {@link permission#WRITE_CONTACTS}
     * {@link permission#GET_ACCOUNTS}
     */
    String CONTACTS = permission.READ_CONTACTS;

    /**
     * {@link permission#ACCESS_COARSE_LOCATION}
     * {@link permission#ACCESS_FINE_LOCATION}
     */
    String LOCATION = permission.ACCESS_COARSE_LOCATION;

    String RECORD_AUDIO = permission.RECORD_AUDIO;

    /**
     * {@link permission#READ_PHONE_STATE}
     * {@link permission#CALL_PHONE}
     * {@link permission#READ_CALL_LOG}
     * {@link permission#WRITE_CALL_LOG}
     * {@link permission#USE_SIP}
     * {@link permission#PROCESS_OUTGOING_CALLS}
     */
    String PHONE_STATE = permission.READ_PHONE_STATE;

    String SENSORS = permission.BODY_SENSORS;

    /**
     * {@link permission#SEND_SMS}
     * {@link permission#RECEIVE_SMS}
     * {@link permission#READ_SMS}
     * {@link permission#RECEIVE_WAP_PUSH}
     * {@link permission#RECEIVE_MMS}
     */
    String SMS = permission.SEND_SMS;

    /**
     * {@link permission#READ_EXTERNAL_STORAGE}
     * {@link permission#WRITE_EXTERNAL_STORAGE}
     */
    String STORAGE = permission.READ_EXTERNAL_STORAGE;
}