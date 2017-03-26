package com.stdiohue.basestrcuture.kqbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class AnnotatedHandlerFinder {

    /**
     * Cache event bus producer methods for each class.
     */
    private static final ConcurrentMap<Class<?>, Map<Class<?>, MethodWithPriority>> PRODUCERS_CACHE = new ConcurrentHashMap<Class<?>, Map<Class<?>, MethodWithPriority>>();

    /**
     * Cache event bus subscriber methods for each class.
     */
    private static final ConcurrentMap<Class<?>, Map<Class<?>, Set<MethodWithPriority>>> SUBSCRIBERS_CACHE = new ConcurrentHashMap<Class<?>, Map<Class<?>, Set<MethodWithPriority>>>();

    private static void loadAnnotatedProducerMethods(Class<?> listenerClass,
                                                     Map<Class<?>, MethodWithPriority> producerMethods) {
        Map<Class<?>, Set<MethodWithPriority>> subscriberMethods = new HashMap<Class<?>, Set<MethodWithPriority>>();
        loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
    }

    private static void loadAnnotatedSubscriberMethods(Class<?> listenerClass,
                                                       Map<Class<?>, Set<MethodWithPriority>> subscriberMethods) {
        Map<Class<?>, MethodWithPriority> producerMethods = new HashMap<Class<?>, MethodWithPriority>();
        loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
    }

    /**
     * Load all methods annotated with {@link Produce} or {@link Subscribe} into
     * their respective caches for the specified class.
     */
    private static void loadAnnotatedMethods(Class<?> listenerClass, Map<Class<?>, MethodWithPriority> producerMethods,
                                             Map<Class<?>, Set<MethodWithPriority>> subscriberMethods) {
        for (Method method : listenerClass.getMethods()) {
            // The compiler sometimes creates synthetic bridge methods as part
            // of the
            // type erasure process. As of JDK8 these methods now include the
            // same
            // annotations as the original declarations. They should be ignored
            // for
            // subscribe/produce.
            if (method.isBridge()) {
                continue;
            }
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires "
                            + parameterTypes.length + " arguments.  Methods must require a single argument.");
                }

                Class<?> eventType = parameterTypes[0];
                if (eventType.isInterface()) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + eventType
                            + " which is an interface.  Subscription must be on a concrete class type.");
                }

                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + eventType
                            + " but is not 'public'.");
                }
                Subscribe annotation = method.getAnnotation(Subscribe.class);
                int priority = annotation.priority();
                if (priority < 0 || priority > 5) {
                    throw new IllegalArgumentException(
                            "Method " + method + " has @Subscribe .Priority must be in [0,5]");
                }
                Set<MethodWithPriority> methods = subscriberMethods.get(eventType);
                if (methods == null) {
                    methods = new HashSet<MethodWithPriority>();
                    subscriberMethods.put(eventType, methods);
                }

                methods.add(new MethodWithPriority(method, priority));

            } else if (method.isAnnotationPresent(Produce.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 0) {
                    throw new IllegalArgumentException("Method " + method + "has @Produce annotation but requires "
                            + parameterTypes.length + " arguments.  Methods must require zero arguments.");
                }
                if (method.getReturnType() == Void.class) {
                    throw new IllegalArgumentException(
                            "Method " + method + " has a return type of void.  Must declare a non-void type.");
                }

                Class<?> eventType = method.getReturnType();
                if (eventType.isInterface()) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + eventType
                            + " which is an interface.  Producers must return a concrete class type.");
                }
                if (eventType.equals(Void.TYPE)) {
                    throw new IllegalArgumentException(
                            "Method " + method + " has @Produce annotation but has no return type.");
                }

                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    throw new IllegalArgumentException(
                            "Method " + method + " has @Produce annotation on " + eventType + " but is not 'public'.");
                }

                if (producerMethods.containsKey(eventType)) {
                    throw new IllegalArgumentException(
                            "Producer for type " + eventType + " has already been registered.");
                }
                Produce annotation = method.getAnnotation(Produce.class);
                producerMethods.put(eventType, new MethodWithPriority(method, annotation.priority()));
            }
        }

        PRODUCERS_CACHE.put(listenerClass, producerMethods);
        SUBSCRIBERS_CACHE.put(listenerClass, subscriberMethods);
    }

    /**
     * This implementation finds all methods marked with a {@link Produce}
     * annotation.
     */
    static Map<Class<?>, EventProducer> findAllProducers(Object listener) {
        final Class<?> listenerClass = listener.getClass();
        Map<Class<?>, EventProducer> handlersInMethod = new HashMap<Class<?>, EventProducer>();

        Map<Class<?>, MethodWithPriority> methods = PRODUCERS_CACHE.get(listenerClass);
        if (null == methods) {
            methods = new HashMap<Class<?>, MethodWithPriority>();
            loadAnnotatedProducerMethods(listenerClass, methods);
        }
        if (!methods.isEmpty()) {
            for (Entry<Class<?>, MethodWithPriority> e : methods.entrySet()) {
                EventProducer producer = new EventProducer(listener, e.getValue());
                handlersInMethod.put(e.getKey(), producer);
            }
        }
        // return handlersInMethod;
        return sortProducerHandleByPriority(handlersInMethod);
    }

    /**
     * This implementation finds all methods marked with a {@link Subscribe}
     * annotation.
     */
    static Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object listener) {
        Class<?> listenerClass = listener.getClass();
        Map<Class<?>, Set<EventHandler>> handlersInMethod = new HashMap<Class<?>, Set<EventHandler>>();

        Map<Class<?>, Set<MethodWithPriority>> methods = SUBSCRIBERS_CACHE.get(listenerClass);
        if (null == methods) {
            methods = new HashMap<Class<?>, Set<MethodWithPriority>>();
            loadAnnotatedSubscriberMethods(listenerClass, methods);
        }
        if (!methods.isEmpty()) {
            for (Entry<Class<?>, Set<MethodWithPriority>> e : methods.entrySet()) {
                Set<EventHandler> handlers = new HashSet<EventHandler>();
                for (MethodWithPriority m : e.getValue()) {
                    handlers.add(new EventHandler(listener, m));
                }
                handlersInMethod.put(e.getKey(), handlers);
            }
        }
        // return handlersInMethod;
        return sortEventHandleByPriority(handlersInMethod);
    }

    private AnnotatedHandlerFinder() {
        // No instances.
    }

    static Map<Class<?>, Set<EventHandler>> sortEventHandleByPriority(Map<Class<?>, Set<EventHandler>> input) {
        LinkedList<Entry<Class<?>, Set<EventHandler>>> list = new LinkedList<>(input.entrySet());
        for (Entry<Class<?>, Set<EventHandler>> item : list) {
            List<EventHandler> subList = new ArrayList<>(item.getValue());
            Collections.sort(subList, new Comparator<EventHandler>() {

                @Override
                public int compare(EventHandler lhs, EventHandler rhs) {
                    return lhs.getMethod().getPriority() - rhs.getMethod().getPriority();
                }
            });
            Set<EventHandler> sortedSet = new HashSet<EventHandler>();
            for (EventHandler a : subList) {
                sortedSet.add(a);
            }
            item.setValue(sortedSet);
        }

        Map<Class<?>, Set<EventHandler>> result = new LinkedHashMap<Class<?>, Set<EventHandler>>();
        for (Entry<Class<?>, Set<EventHandler>> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    static Map<Class<?>, EventProducer> sortProducerHandleByPriority(Map<Class<?>, EventProducer> input) {
        LinkedList<Entry<Class<?>, EventProducer>> list = new LinkedList<>(input.entrySet());
        Collections.sort(list, new Comparator<Entry<Class<?>, EventProducer>>() {

            @Override
            public int compare(Entry<Class<?>, EventProducer> lhs, Entry<Class<?>, EventProducer> rhs) {
                return rhs.getValue().getMethod().getPriority() - lhs.getValue().getMethod().getPriority();
            }
        });

        Map<Class<?>, EventProducer> result = new LinkedHashMap<Class<?>, EventProducer>();
        // for (Iterator<Entry<Class<?>, Set<EventHandler>>> it =
        // list.iterator(); it.hasNext();) {
        // Map.Entry entry = (Map.Entry) it.next();
        // result.put(entry.getKey(), entry.getValue());
        // }
        for (Entry<Class<?>, EventProducer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
