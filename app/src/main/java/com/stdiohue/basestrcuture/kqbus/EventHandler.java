/*
 * Copyright (C) 2012 Square, Inc.
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stdiohue.basestrcuture.kqbus;

import java.lang.reflect.InvocationTargetException;

/**
 * Wraps a single-argument 'handler' method on a specific object.
 * <p/>
 * <p/>
 * This class only verifies the suitability of the method and event type if
 * something fails. Callers are expected t verify their uses of this class.
 * <p/>
 * <p/>
 * Two EventHandlers are equivalent when they refer to the same method on the
 * same object (not class). This property is used to ensure that no handler
 * method is registered more than once.
 *
 * @author Cliff Biffle
 */
class EventHandler {

    /**
     * Object sporting the handler method.
     */
    private final Object target;
    /**
     * Handler method.
     */
    private final MethodWithPriority method;
    /**
     * Object hash code.
     */
    private final int hashCode;
    /**
     * Should this handler receive events?
     */
    private boolean valid = true;

    EventHandler(Object target, MethodWithPriority method) {
        if (target == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
        }

        this.target = target;
        this.method = method;
        method.getMethod().setAccessible(true);

        // Compute hash code eagerly since we know it will be used frequently
        // and we cannot estimate the runtime of the
        // target's hashCode call.
        final int prime = 31;
        hashCode = (prime + method.hashCode()) * prime + target.hashCode();
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * If invalidated, will subsequently refuse to handle events.
     * <p/>
     * Should be called when the wrapped object is unregistered from the Bus.
     */
    public void invalidate() {
        valid = false;
    }

    /**
     * Invokes the wrapped handler method to handle {@code event}.
     *
     * @param event event to handle
     * @throws IllegalStateException     if previously invalidated.
     * @throws InvocationTargetException if the wrapped method throws any {@link Throwable} that is
     *                                   not an {@link Error} ({@code Error}s are propagated as-is).
     */
    public void handleEvent(Object event) throws InvocationTargetException {
        if (!valid) {
            throw new IllegalStateException(toString() + " has been invalidated and can no longer handle events.");
        }
        try {
            method.getMethod().invoke(target, event);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }

    @Override
    public String toString() {
        return "[EventHandler " + method + " " + method.getPriority() + "]";
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public MethodWithPriority getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final EventHandler other = (EventHandler) obj;

        return method.equals(other.method) && target == other.target;
    }

}