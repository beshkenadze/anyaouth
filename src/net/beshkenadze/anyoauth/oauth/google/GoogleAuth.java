package net.beshkenadze.anyoauth.oauth.google;

import net.beshkenadze.anyoauth.oauth.BaseOAuth;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.GoogleApi;

import android.content.Context;

/**
 * @author aleksej.grigorjev@gmail.com
 */

public class GoogleAuth extends BaseOAuth {

    private static Class<? extends Api> classApi = GoogleApi.class;
    private static final String prefix = "Google";
    
    public GoogleAuth(Context context) {
        super(context, classApi, prefix, true);
    }

    public static boolean isAuth(Context context) {
        return BaseOAuth.isAuth(context, prefix);
    }
}

