package com.eventpass;

import android.content.Intent;

import com.eventpass.annotation.Receiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/14.
 */
public class EventManager {

    static Map<Class<?>,Map<Integer,Map<Integer,EventHandler>>> handlersByServer = new HashMap<>();
    static int currentCaller = -1;
    static Object currentObj = null;

    public static void register(Object object){
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }

        Class<?> listenerClass = object.getClass();
        if (handlersByServer.get(listenerClass) == null){
            handlersByServer.put(listenerClass,new HashMap<Integer,Map<Integer,EventHandler>>());
        }
        Map<Integer,Map<Integer,EventHandler>> handlerMap = handlersByServer.get(listenerClass);
        for (Method method : listenerClass.getDeclaredMethods()) {
            if (method.isBridge()) {
                continue;
            }
            if (method.isAnnotationPresent(Receiver.class)) {
                Receiver receiver = method.getAnnotation(Receiver.class);
                Map<Integer,EventHandler> handlers = new HashMap<>();
                handlers.put(receiver.server(),new EventHandler(object,method));
                handlerMap.put(receiver.caller(),handlers);
            }
        }
    }

    public static void unRegister(Object object){
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        Class<?> listenerClass = object.getClass();
        if (handlersByServer.get(listenerClass) != null){
            handlersByServer.put(listenerClass,new HashMap<Integer,Map<Integer,EventHandler>>());
        }
    }

    public static void post(int resultCode,int serverCode,Intent intent) throws InvocationTargetException {
        Class<?> listenerClass = currentObj.getClass();
        if (handlersByServer.get(listenerClass) != null){
            EventHandler eventHandler = handlersByServer.get(listenerClass).get(currentCaller).get(serverCode);
            if(eventHandler == null){
                throw new NullPointerException("eventhandler must not be null.");
            }
            eventHandler.handleEvent(resultCode,intent);
        }
    }

    public static void subcribe(Object object,int caller){
        currentCaller = caller;
        currentObj = object;
    }

}
