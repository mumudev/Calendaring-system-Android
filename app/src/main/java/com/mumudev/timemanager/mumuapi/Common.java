package com.mumudev.timemanager.mumuapi;

import com.google.gson.reflect.TypeToken;
import com.mumudev.timemanager.beans.LoginInfo;
import com.mumudev.timemanager.beans.LoginResult;
import com.mumudev.timemanager.beans.RegisterInfo;
import com.mumudev.timemanager.beans.ResultDataInfo;
import com.mumudev.timemanager.util.GsonUtil;
import com.mumudev.timemanager.volley.GsonRequest.OnRespnoseListener;
import com.mumudev.timemanager.volley.VolleyHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 木木工程师 on 2016/3/9.
 */
public class Common {

    private static final String TAG = Common.class.getSimpleName();
    private static Map<String, String> params = new HashMap<String, String>();


    /**
     * 注册
     *
     * @param listener
     * @param phone
     * @param password
     */
    public static void register(OnRespnoseListener<LoginResult> listener,
                                String phone, String password) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setPhone(phone);
        registerInfo.setPassword(password);
        setParams(registerInfo);
        //TODO
        post("IP路由", params, listener,
                new TypeToken<ResultDataInfo<LoginResult>>() {
                });
    }

    /**
     * 登陆
     *
     * @param listener
     * @param account
     * @param password
     */
    public static void login(OnRespnoseListener<LoginResult> listener,
                             final String account, final String password, final String imei) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAccount(account);
        loginInfo.setPassword(password);
        loginInfo.setImei(imei);
        setParams(loginInfo);
//        TODO
        post("", params, listener,
                new TypeToken<ResultDataInfo<LoginResult>>() {
                });
    }

    private static <T> void post(final String partUrl,
                                 Map<String, String> params, OnRespnoseListener<T> listener,
                                 final TypeToken<ResultDataInfo<T>> token) {
        VolleyHelper.post(getAbsoluteUrl(partUrl), params, listener, token);
    }

    /**
     * 设置params的值
     *
     * @param dataInfo
     */
    private static <T> void setParams(T dataInfo, int requestCode) {
        ResultDataInfo<T> result = new ResultDataInfo<T>();
        result.setData(dataInfo);
        result.setCode(requestCode);
        params.put("params", GsonUtil.toJson(result));
    }

    /**
     * 设置params的值
     *
     * @param dataInfo
     */
    private static <T> void setParams(T dataInfo) {
        setParams(dataInfo, 0);
    }

    public static final String URL_BASE = ControllerURL.HOST + "%s";
    //public static final String URL_BASE = "http://192.168.1.177:8288/mask_server/%s";

    public static String getAbsoluteUrl(String partUrl) {
        String url = String.format(URL_BASE, partUrl);
        return url;
    }
}
