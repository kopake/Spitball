package io.github.kopake.spitball.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.kopake.spitball.event.listeners.Listener;

public class EventManager {

    private static EventManager instance = new EventManager();
    private Collection<Listener> listeners = new ArrayList<>();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private EventManager() {
    }

    public static EventManager getInstance() {
        return instance;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void dispatchEvent(Event event) {
        // Submit a new task to the executor service
        executorService.submit(() -> {
            for (Listener listener : listeners) {
                dispatchEventToListener(event, listener);
            }
        });
    }

    private void dispatchEventToListener(Event event, Listener listener) {
        for (Method method : getAllMethods(listener)) {

            EventHandler eventHandler = method.getAnnotation(EventHandler.class);

            if (eventHandler == null)
                continue;

            final Class<?> checkClass;
            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0]))
                continue;

            final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
            method.setAccessible(true);

            if (!eventClass.isAssignableFrom(event.getClass()))
                continue;

            try {
                method.invoke(listener, event);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<Method> getAllMethods(Object object) {
        Set<Method> methods = new HashSet<>();
        if (object == null)
            return methods;
        Method[] publicMethods = object.getClass().getMethods();
        Method[] privateMethods = object.getClass().getDeclaredMethods();

        for (Method method : publicMethods)
            methods.add(method);
        for (Method method : privateMethods)
            methods.add(method);

        return methods;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
