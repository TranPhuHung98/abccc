package com.stdiohue.basestrcuture.kqbus;

/**
 */
public class Dispatcher {

    public static Dispatcher sInstance;

    private KQBus mBus;

    Dispatcher() {
        mBus = new KQBus(ThreadEnforcer.ANY);
    }

    public static Dispatcher getInstance() {
        if (sInstance == null) {
            sInstance = new Dispatcher();
        }

        return sInstance;
    }

    public void register(Object obj) {
        mBus.register(obj);
    }

    public void unregister(Object obj) {
        mBus.unregister(obj);
    }

    public void post(Object obj) {
        mBus.post(obj);
    }
}
