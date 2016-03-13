package com.mumudev.timemanager.volley;

import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.mumudev.timemanager.application.AppContext;
import com.mumudev.timemanager.beans.ResultDataInfo;

import java.util.List;
import java.util.Map;

public class VolleyHelper {
    private static final String TAG = VolleyHelper.class.getSimpleName();
    //请求队列
    private static RequestQueue sRequestQueue;
    //图片下载
    private static ImageLoader sImageLoader;

    public static final String JSON2LIST = "JSON2LIST";
    public static final String JSON2BEAN = "JSON2BEAN";

    private VolleyHelper() {
    }

    public static <T> void post(final String url, Map<String, String> params,
                                GsonRequest.OnRespnoseListener<T> listener,
                                final TypeToken<ResultDataInfo<T>> token) {
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.POST, url,
                token.getType(), listener);
        gsonRequest.setParams(params);
        addToRequestQueue(gsonRequest, JSON2LIST);
    }

    /**
     * @param url      请求的url
     * @param listener 成功回调的接口
     * @param token    目标类型器，标识需要转换成的目标对象
     */
    public static <T> void json2Bean(final String url,
                                     final GsonRequest.OnRespnoseListener<T> listener,
                                     final TypeToken<ResultDataInfo<T>> token) {
        json2Bean(url, listener, null, token);
    }

    /**
     * @param url           请求的url
     * @param listener      请求成功时回调该接口
     * @param errorListener 请求失败时回调该接口
     * @param token         目标类型器，标识需要转换成的目标对象
     */
    public static <T> void json2Bean(final String url,
                                     final GsonRequest.OnRespnoseListener<T> listener,
                                     final Response.ErrorListener errorListener,
                                     final TypeToken<ResultDataInfo<T>> token) {
        GsonRequest<T> gsonRequest = new GsonRequest<T>(url, token.getType(),
                listener, errorListener);
        addToRequestQueue(gsonRequest, JSON2LIST);
    }

    /**
     * @param url      请求的url
     * @param listener 成功回调的接口
     * @param token    目标类型器，标识需要转换成的目标List对象
     */
    public static <T> void json2List(final String url,
                                     final GsonRequest.OnRespnoseListener<List<T>> listener,
                                     final TypeToken<ResultDataInfo<List<T>>> token) {
        json2List(url, listener, null, token);
    }

    /**
     * @param url           请求的url
     * @param listener      成功回调的接口
     * @param errorListener 请求失败时回调该接口
     * @param token         目标类型器，标识需要转换成的目标List对象
     */
    public static <T> void json2List(final String url,
                                     final GsonRequest.OnRespnoseListener<List<T>> listener,
                                     final Response.ErrorListener errorListener,
                                     final TypeToken<ResultDataInfo<List<T>>> token) {
        //MKLog.i(TAG, "type:" + token.getType().toString());
        GsonRequest<List<T>> gsonRequest = new GsonRequest<List<T>>(url,
                token.getType(), listener, errorListener);
        addToRequestQueue(gsonRequest, JSON2BEAN);
    }

    // // T泛型会丢失
    // // Log输出：type:com.bbl.mask.pojo.ResultDataInfo<java.util.List<T>>
    // //------------------------------------------------------
    // public static <T> void json2List(final String url,
    // final OnRespnoseListener<List<T>> listener) {
    // Type type = new TypeToken<ResultDataInfo<List<T>>>() {}.getType();
    //
    // MKLog.i(TAG, "type:"+type.toString());
    // GsonRequest<List<T>> gsonRequest = new GsonRequest<List<T>>(url, type,
    // listener);
    // VolleyHelper.addToRequestQueue(gsonRequest, TAG);
    // }

    /**
     * Volley的ImageLoader实例
     *
     * @return ImageLoader
     */
    public static ImageLoader getImageLoader() {
        if (null == sImageLoader) {
            getRequestQueue();
            sImageLoader = new ImageLoader(sRequestQueue, new LruBitmapCache());
        }

        return sImageLoader;
    }

    /**
     * Volley的RequestQueue实例
     *
     * @return RequestQueue
     */
    public static RequestQueue getRequestQueue() {
        if (null == sRequestQueue) {
            sRequestQueue = Volley.newRequestQueue(AppContext
                    .getAppContext());
        }

        return sRequestQueue;
    }

    /**
     * 添加请求
     *
     * @param req 请求
     * @param tag 标记
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        if (null == req) {
            return;
        }

        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * 添加请求
     *
     * @param req 请求
     */
    public static <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, null);
    }

    /**
     * 取消指定的请求
     *
     * @param tag
     */
    public static void cancelPendingRequests(Object tag) {
        if (tag != null && sRequestQueue != null) {
            sRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 加载图片
     *
     * @param url 图片url
     * @param iv  imageView
     */
    public static void loadImageByUrl(String url, ImageView iv) {
        loadImageByUrl(url, iv, 0, 0);
    }

    /**
     * 加载图片，可设置默认显示图片，defaultImageResId为0则不设置
     *
     * @param url               图片url
     * @param iv                imageView
     * @param defaultImageResId 默认显示图片id
     */
    public static void loadImageByUrl(String url, ImageView iv,
                                      int defaultImageResId) {
        loadImageByUrl(url, iv, defaultImageResId, 0);
    }

    /**
     * 加载图片，可设置默认显示图片，加载失败图片 ,defaultImageResId为0则不设置，errorImageResId为0则不设置
     *
     * @param url               图片url
     * @param iv                imageView
     * @param defaultImageResId 默认显示图片id
     * @param errorImageResId   加载失败图片id
     */
    public static void loadImageByUrl(String url, ImageView iv,
                                      int defaultImageResId, int errorImageResId) {
        if (TextUtils.isEmpty(url) || null == iv) {
            return;
        }

        getImageLoader().get(
                url,
                ImageLoader.getImageListener(iv, defaultImageResId,
                        errorImageResId));
    }

    /**
     * 加载图片
     *
     * @param url 图片url
     * @param niv NetworkImageView
     */
    public static void loadImageByUrl(String url, NetworkImageView niv) {
        if (TextUtils.isEmpty(url) || null == niv) {
            return;
        }

        niv.setImageUrl(url, getImageLoader());
    }

    /**
     * Invalidate means we are invalidating the cached data instead of deleting
     * it. Volley will still uses the cached object until the new data received
     * from server. Once it receives the response from the server it will
     * override the older cached response.
     *
     * @param url
     */
    public static void invalidateCache(String url) {
        invalidateCache(url, true);
    }

    /**
     * Invalidates an entry in the cache.
     *
     * @param url
     * @param fullExpire True to fully expire the entry, false to soft expire
     */
    public static void invalidateCache(String url, boolean fullExpire) {
        if (sRequestQueue != null) {
            sRequestQueue.getCache().invalidate(url, fullExpire);
        }
    }

    /**
     * Use remove() to delete cache of an URL
     *
     * @param url
     */
    public static void removeCache(String url) {
        if (sRequestQueue != null) {
            sRequestQueue.getCache().remove(url);
        }
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        if (sRequestQueue != null) {
            sRequestQueue.getCache().clear();
        }
    }

}
