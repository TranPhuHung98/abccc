package com.stdiohue.basestrcuture.apis.base;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.stdiohue.basestrcuture.ProjectApplication;
import com.stdiohue.basestrcuture.apis.Callback;
import com.stdiohue.basestrcuture.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaquay on 3/29/16.
 */
public abstract class BaseJsonRequest<T> extends JsonRequest<T> {
    private Class<T> clazz;
    private Callback<T> mListener;

    public BaseJsonRequest(int method, String url, String bodyRequest, Class<T> tClass, Callback<T> baseResponse) {
        super(method, url, bodyRequest, null, null);
        clazz = tClass;
        mListener = baseResponse;
        setRetryPolicy(new DefaultRetryPolicy(20 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    public String getUrl() {
        String url = super.getUrl();
        LogUtils.logD(url);
        return url;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        LogUtils.logD(jsonString);
        try {
            T object = new Gson().fromJson(jsonString, clazz);
            return Response.success(object, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new VolleyError(response));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onSuccess(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        mListener.onFailed(error);
    }
}
