package com.jsware.eventpass;

import android.content.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/2/14.
 */
public class EventHandler {

    /** Object sporting the handler method. */
    private final Object target;
    /** Handler method. */
    private final Method method;

    public EventHandler(Object target, Method method) {
        if (target == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
        }
        this.target = target;
        this.method = method;
    }

    /**
     * Invokes the wrapped handler method to handle {@code event}.
     *
     * @param resultCode  resultCode to handle
     * @param data  extra data
     * @throws IllegalStateException  if previously invalidated.
     * @throws InvocationTargetException  if the wrapped method throws any {@link Throwable} that is not
     *     an {@link Error} ({@code Error}s are propagated as-is).
     */
    public void handleEvent(int resultCode,Intent data) throws InvocationTargetException {
        try {
            method.invoke(target, resultCode,data);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }
}
