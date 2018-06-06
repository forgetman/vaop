package aspect.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aspect.R;

/**
 * @author yuansui
 * @since 2018/6/6
 */
public class PermissionRequestActivity extends Activity {

    private static final String PERMISSION_KEY = "permission_key";
    private final int PERMISSION_CODE = 10000;

    private String[] mPermissions;
    private static PermissionCallback mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_request);

        mPermissions = getIntent().getStringArrayExtra(PERMISSION_KEY);
        if (mPermissions == null || mPermissions.length == 0) {
            finish();
            return;
        }
        requestPermission();
    }

    private void requestPermission() {
        List<String> uncheckPermissions = new ArrayList<>();
        for (String p : mPermissions) {
            //权限检测如果未允许，添加至待申请列表
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                uncheckPermissions.add(p);
            }
        }

        if (!uncheckPermissions.isEmpty()) {
            String[] strings = new String[uncheckPermissions.size()];
            uncheckPermissions.toArray(strings);
            ActivityCompat.requestPermissions(this, strings, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            for (int result : grantResults) {
                Log.e("onPermissionsResult", "grantResult: = " + result);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    if (mCallback != null) {
                        mCallback.onResult(true);
                    }
                } else {
                    if (mCallback != null) {
                        mCallback.onResult(false);
                    }
                }
            }
        }

        finish();
        overridePendingTransition(0, 0);
    }

    public static void permissionRequest(Context context, String[] permissions, PermissionCallback callback) {
        mCallback = callback;

        Intent i = new Intent(context, PermissionRequestActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putStringArray(PERMISSION_KEY, permissions);
        i.putExtras(b);
        context.startActivity(i);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    public static void clearCallback() {
        mCallback = null;
    }
}
