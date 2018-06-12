package aspect.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import aspect.annotation.Duration;
import aspect.annotation.MainThread;
import aspect.annotation.RequirePermission;
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

//        testPermission();
//        testCameraPermission();
        testMultiPermissions();

//        Observable.fromCallable(() -> {null})
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
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

    @RequirePermission(value = Permission.LOCATION, message = "打开定位权限")
    public void testPermission() {
        Log.d(TAG, "testPermissions");
    }

    @RequirePermission(value = Permission.CAMERA, message = "打开相机权限")
    public void testCameraPermission() {
        Log.d(TAG, "testCameraPermission");
    }

    @RequirePermission(value = {Permission.CAMERA, Permission.LOCATION}, messageId = R.string.app_name)
    public void testMultiPermissions() {
        Log.d(TAG, "testMultiPermissions");
    }
}
