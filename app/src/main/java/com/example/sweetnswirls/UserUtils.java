package com.example.sweetnswirls;

 // <- change to your package if different

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONObject;
import org.json.JSONException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserUtils {
    private static final String PREFS = "SweetNSwirlsApp";
    private static final String USERS_KEY = "users";

    public static Map<String,String> getUsersMap(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String json = prefs.getString(USERS_KEY, "{}");
        Map<String,String> map = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(json);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                map.put(key, obj.getString(key));
            }
        } catch (JSONException e) { e.printStackTrace(); }
        return map;
    }

    public static void saveUsersMap(Context ctx, Map<String,String> map) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        JSONObject obj = new JSONObject();
        try {
            for (Map.Entry<String,String> e : map.entrySet()) obj.put(e.getKey(), e.getValue());
            prefs.edit().putString(USERS_KEY, obj.toString()).apply();
        } catch (JSONException ex) { ex.printStackTrace(); }
    }

    public static String sha256(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(s.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception ex) { throw new RuntimeException(ex); }
    }
}

