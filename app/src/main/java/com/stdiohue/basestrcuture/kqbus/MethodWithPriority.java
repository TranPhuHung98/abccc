package com.stdiohue.basestrcuture.kqbus;

import java.lang.reflect.Method;

public class MethodWithPriority {
    private Method method;
    private int priority;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MethodWithPriority(Method method, int priority) {
        this.method = method;
        this.priority = priority;
    }

}
