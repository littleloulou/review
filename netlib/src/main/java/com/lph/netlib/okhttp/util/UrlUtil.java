package com.lph.netlib.okhttp.util;

import android.util.Log;

import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;

/**
 * Created by lph on 2017/10/30.
 */

public class UrlUtil {

    public static String map2QueryStr(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry entry :
                entries) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        sb = sb.replace(sb.lastIndexOf("&"), sb.lastIndexOf("&") + 1, "");
        return sb.toString();
    }

}
