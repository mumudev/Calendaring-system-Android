package com.mumudev.timemanager.util;

import com.google.gson.Gson;

public class GsonUtil {

	private static Gson sGson;

	public static Gson getGson() {
		if (null == sGson) {
			sGson = new Gson();
		}
		return sGson;
	}

	public static String toJson(Object o) {
		return getGson().toJson(o);
	}

}
