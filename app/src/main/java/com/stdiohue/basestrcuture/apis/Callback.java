package com.stdiohue.basestrcuture.apis;

import com.android.volley.VolleyError;

/**
 * Created by kaquay on 3/29/16.
 */
public interface Callback<T> {
    void onSuccess(T result);

    void onFailed(VolleyError err);
}
