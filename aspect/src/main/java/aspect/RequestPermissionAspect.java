package aspect;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import aspect.annotation.RequestPermission;
import aspect.permission.PermissionRequestActivity;

@Aspect
public class RequestPermissionAspect extends BaseAspect {
    @Pointcut(START + "RequestPermission" + " * *(..)) && @annotation(request)")
    public void requestPermissionMethod(RequestPermission request) {
    }

    @Around("requestPermissionMethod(request)")
    public void interceptPermissionRequest(final ProceedingJoinPoint joinPoint, final RequestPermission request) {
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

        PermissionRequestActivity.permissionRequest(context, request.request(), success -> {
            if (success) {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else {
                Toast.makeText(context4Toast, request.message(), Toast.LENGTH_SHORT).show();
            }

            PermissionRequestActivity.clearCallback();
        });
    }
}