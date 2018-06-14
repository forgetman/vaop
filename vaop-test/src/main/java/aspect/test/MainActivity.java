package aspect.test;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import vaop.annotation.Duration;
import vaop.annotation.IOThread;
import vaop.annotation.MainThread;
import vaop.annotation.Permission;


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

        testSubThread();
        testSubThread();
        testMainThread();
        testDuration();

//        testPermission();
//        testCameraPermission();
        testMultiPermissions();
    }

    @IOThread
    public void testSubThread() {
        Log.d("www", "testSubThread id = " + android.os.Process.myTid());
    }

    @MainThread
    public void testMainThread() {
        Log.d("www", "testMainThread id = " + android.os.Process.myTid());
    }

    @Duration
    public void testDuration() {
        Log.d(TAG, "testDuration");
    }

    @Permission(value = permission.ACCESS_FINE_LOCATION, message = "打开定位权限")
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
}
