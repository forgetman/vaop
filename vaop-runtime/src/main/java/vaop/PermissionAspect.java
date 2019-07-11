package vaop;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.rompermission.RomPermission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            try {
                context = getContext(joinPoint.getThis());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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

        RomPermission.checkAndRequest(context, value.value(), message, success -> {
            if (success) {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    private Context getContext(Object object) throws IllegalAccessException {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = object.getClass();
        while (tempClass != null && !tempClass.equals(Object.class)) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }

        for (Field field : fieldList) {
            field.setAccessible(true);
            if (field.get(object) instanceof Context) {
                return (Context) field.get(object);

            } else if (field.get(object) instanceof android.support.v4.app.Fragment) {
                return ((android.support.v4.app.Fragment) field.get(object)).getActivity();

            } else if (field.get(object) instanceof android.app.Fragment) {
                return ((android.app.Fragment) field.get(object)).getActivity();
            }
        }

        return null;
    }
}