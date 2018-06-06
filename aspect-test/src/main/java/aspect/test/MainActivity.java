package aspect.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import aspect.annotation.Duration;
import aspect.annotation.MainThread;
import aspect.annotation.RequestPermission;
import aspect.annotation.SubThread;

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

        testPermission();
        testCameraPermission();
    }

    @SubThread
    public void testSubThread() {
        Log.d(TAG, "testSubThread");
    }

    @MainThread
    public void testMainThread() {
        Log.d("www", "testMainThread id = " + android.os.Process.myTid());
    }

    @Duration
    public void testDuration() {
        Log.d(TAG, "testDuration");
    }

    @RequestPermission(request = Permission.LOCATION, message = "打开定位权限")
    public void testPermission() {
        Log.d(TAG, "testPermissions");
    }

    @RequestPermission(request = Permission.CAMERA, message = "打开相机权限")
    public void testCameraPermission() {
        Log.d(TAG, "testCameraPermission");
    }
}
