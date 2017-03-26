package com.stdiohue.basestrcuture.apis.base;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.stdiohue.basestrcuture.apis.Callback;
import com.stdiohue.basestrcuture.utils.LogUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kaquay on 4/25/16.
 */
public abstract class BaseRequest<T> extends Request<T> {
    private Class<T> clazz;
    private Callback<T> mListener;

    public BaseRequest(int method, String url, Class<T> tClass, Callback<T> baseResponse) {
        super(method, url, null);
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
    public Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        return params;
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
    public String getUrl() {
        if (getMethod() == Method.GET) {
            try {
                String currentURL = super.getUrl();
                StringBuilder stringBuilder = new StringBuilder(currentURL);
                Iterator<Map.Entry<String, String>> iterator = getParams()
                        .entrySet().iterator();
                int i = 1;
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    if (i == 1) {
                        stringBuilder.append("?" + entry.getKey() + "=" + entry
                                .getValue());
                    } else {
                        stringBuilder.append("&" + entry.getKey() + "=" + entry
                                .getValue());
                    }
                    iterator.remove(); // avoids a ConcurrentModificationException
                    i++;
                }
                LogUtils.logD(stringBuilder.toString());
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url = super.getUrl();
        LogUtils.logD(url);
        return url;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onSuccess(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError != null && volleyError.networkResponse != null) {
            String er = new String(volleyError.networkResponse.data);
            LogUtils.logD(er);
        }
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        mListener.onFailed(error);
    }
}
