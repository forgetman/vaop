package vaop;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.example.rompermission.PermissionCallback;
import com.example.rompermission.RomPermission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import vaop.annotation.Permission;

@Aspect
public class PermissionAspect extends BaseAspect {

    @Pointcut(START + "Permission" + " * *(..)) && @annotation(value)")
    public void methodCut(Permission value) {
    }

    @Around("methodCut(value)")
    public void interceptPermissionRequest(final ProceedingJoinPoint joinPoint, final Permission value) {
        Context context = null;
        final Object object = joinPoint.getThis();
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        }

        if (context == null) {
            Log.e("interceptPermission", "UnKnown Context");
            return;
        }

        final Context context4Toast = context;

        String message;
        if (value.messageId() != 0) {
            message = context4Toast.getString(value.messageId());
        } else {
            message = value.message();
        }

        RomPermission.checkAndRequest(context, value.value(), message, new PermissionCallback(){
            @Override
            public void onResult(boolean success) {
                if (success) {
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        });

//        PermissionRequestActivity.permissionRequest(context, value.value(), success -> {
//            if (success) {
//                try {
//                    joinPoint.proceed();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            } else {
//                String message;
//                if (value.messageId() != 0) {
//                    message = context4Toast.getString(value.messageId());
//                } else {
//                    message = value.message();
//                }
//                Toast.makeText(context4Toast, message, Toast.LENGTH_SHORT).show();
//            }
//
//            PermissionRequestActivity.clearCallback();
//        });
    }
}