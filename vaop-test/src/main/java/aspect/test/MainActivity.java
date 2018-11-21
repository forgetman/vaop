package aspect.test;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import vaop.annotation.DebugLog;
import vaop.annotation.Duration;
import vaop.annotation.Permission;
import vaop.annotation.Safe;
import vaop.annotation.SingleClick;


/**
 * @author yuansui
 * @since 2018/5/31
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.main_tv);

//        testSubThread();
        testMainThread();
        testDuration();

//        testPermission();
//        testCameraPermission();
        testMultiPermissions();

//        testSafe();
        mTv.setOnClickListener(new OnClickListener() {

            @SingleClick
            @Override
            public void onClick(View v) {
                testSubThread();
            }
        });

        testDebugLog("名字", 18);
    }

//    @IOThread
    public void testSubThread() {
        Log.d(TAG, "testSubThread id = " + android.os.Process.myTid());
        mTv.setText("sssss");
    }

//    @MainThread
    public void testMainThread() {
//        Log.d(TAG, "testMainThread id = " + android.os.Process.myTid());
    }

    @Duration
    public void testDuration() {
        Log.d(TAG, "testDuration");
    }

    @Permission(value = permission.ACCESS_COARSE_LOCATION, message = "打开定位权限")
    public void testPermission() {
        Log.d(TAG, "testPermissions");
    }

    @Permission(value = permission.CAMERA, message = "打开相机权限")
    public void testCameraPermission() {
        Log.d(TAG, "testCameraPermission");
    }

    @Permission(value = {permission.CAMERA, permission.ACCESS_FINE_LOCATION}, messageId = R.string.app_name)
    public void testMultiPermissions() {
        Log.d(TAG, "testMultiPermissions");
    }

    @Safe
    public void testSafe() {
        Log.d(TAG, "testSafe");
        Integer i = null;
        i.byteValue();
    }

    @DebugLog
    public boolean testDebugLog(String name, int age) {
        return true;
    }
}
