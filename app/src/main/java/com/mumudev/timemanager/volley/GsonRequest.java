package com.mumudev.timemanager.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mumudev.timemanager.beans.ResultDataInfo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T> ResultDataInfo中指定的数据类型
 * @author zes and benio
 */
public class GsonRequest<T> extends Request<ResultDataInfo<T>> {
    private static final String TAG = GsonRequest.class.getSimpleName();

    private final static Gson sGson = new Gson();

    private final OnRespnoseListener<T> mListener;
    /**
     * 参数信息
     */
    private Map<String, String> mParams;
    /**
     * 头部信息
     */
    private Map<String, String> mHeaders;

    private Type mType;

    /**
     * GsonRequest回调接口
     *
     * @param <T>
     * @author Benio
     */
    public interface OnRespnoseListener<T> extends Response.Listener<ResultDataInfo<T>> {
    }

    /**
     * Create a new request with the given method.
     *
     * @param method        the request {@link Method} to use
     * @param url           URL to fetch the JSON from
     * @param type          The specific genericized type of src. You can obtain this type
     *                      by using the TypeToken class. For example, to get the type for
     *                      Collection<Foo>, you should use: Type type = new
     *                      TypeToken<Collection<Foo>>(){}.getType();
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public GsonRequest(int method, String url, Type type,
                       OnRespnoseListener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mType = type;
    }

    /**
     * Create a new request with the given method, ignore errors.
     *
     * @param method   the request {@link Method} to use
     * @param url      URL to fetch the JSON from
     * @param type     The specific genericized type of src. You can obtain this type
     *                 by using the TypeToken class. For example, to get the type for
     *                 Collection<Foo>, you should use: Type type = new
     *                 TypeToken<Collection<Foo>>(){}.getType();
     * @param listener Listener to receive the JSON response
     */
    public GsonRequest(int method, String url, Type type,
                       OnRespnoseListener<T> listener) {
        this(method, url, type, listener, null);
    }

    /**
     * Create a new GET request.
     *
     * @param url           URL to fetch the JSON from
     * @param type          The specific genericized type of src. You can obtain this type
     *                      by using the TypeToken class. For example, to get the type for
     *                      Collection<Foo>, you should use: Type type = new
     *                      TypeToken<Collection<Foo>>(){}.getType();
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public GsonRequest(String url, Type type, OnRespnoseListener<T> listener,
                       ErrorListener errorListener) {
        this(Method.GET, url, type, listener, errorListener);
    }

    /**
     * Create a new GET request, ignore errors.
     *
     * @param url      URL to fetch the JSON from
     * @param type     The specific genericized type of src. You can obtain this type
     *                 by using the TypeToken class. For example, to get the type for
     *                 Collection<Foo>, you should use: Type type = new
     *                 TypeToken<Collection<Foo>>(){}.getType();
     * @param listener Listener to receive the JSON response
     */
    public GsonRequest(String url, Type type, OnRespnoseListener<T> listener) {
        this(Method.GET, url, type, listener, null);
    }

    @Override
    protected Response<ResultDataInfo<T>> parseNetworkResponse(
            NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            ResultDataInfo<T> result = sGson.fromJson(jsonString, mType);

            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(ResultDataInfo<T> response) {
        if (null == mListener) {
        } else {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams != null ? mParams : super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    /**
     * 设置头部
     *
     * @param headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }

    /**
     * 设置传递的参数
     *
     * @param params
     */
    public void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    /**
     * 添加请求参数
     *
     * @param key   the key
     * @param value the value
     * @return the value of any previous parameter with the specified key or
     * null if there was no parameter.
     */
    public String addParam(String key, String value) {
        if (null == mParams) {
            mParams = new HashMap<String, String>();
        }
        return mParams.put(key, value);
    }

    /**
     * 删除指定key的参数
     *
     * @param key the key of the parameter to remove.
     * @return the value of the removed parameter or null if no parameter for
     * the specified key was found.
     */
    public String removeParam(Object key) {
        if (null == mParams)
            return null;
        return mParams.remove(key);
    }

    /**
     * 参数中是否含有指定key
     *
     * @param key the key to search for.
     * @return true if parameters contain the specified key, false otherwise.
     */
    public boolean containsParamKey(Object key) {
        if (null == mParams)
            return false;
        return mParams.containsKey(key);
    }

    /**
     * 参数中是否含有指定value
     *
     * @param value the value to search for.
     * @return true if parameter contain the specified value, false otherwise.
     */
    public boolean containsParamValue(Object value) {
        if (null == mParams)
            return false;
        return mParams.containsValue(value);
    }
}