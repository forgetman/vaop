package aspect;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import aspect.annotation.RequirePermission;
import aspect.permission.PermissionRequestActivity;

@Aspect
public class RequirePermissionAspect extends BaseAspect {
    @Pointcut(START + "RequirePermission" + " * *(..)) && @annotation(value)")
    public void methodCut(RequirePermission value) {
    }

    @Around("methodCut(value)")
    public void interceptPermissionRequest(final ProceedingJoinPoint joinPoint, final RequirePermission value) {
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

        PermissionRequestActivity.permissionRequest(context, value.value(), success -> {
            if (success) {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else {
                String message;
                if (value.messageId() != 0) {
                    message = context4Toast.getString(value.messageId());
                } else {
                    message = value.message();
                }
                Toast.makeText(context4Toast, message, Toast.LENGTH_SHORT).show();
            }

            PermissionRequestActivity.clearCallback();
        });
    }
}