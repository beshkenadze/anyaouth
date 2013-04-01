package net.beshkenadze.anyoauth.oauth.qq;

import net.beshkenadze.anyoauth.oauth.BaseOAuth;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.QWeiboApi;

import android.content.Context;

/**
 * @author aleksej.grigorjev@gmail.com
 */

public class QQAuth extends BaseOAuth {

    private static Class<? extends Api> classApi = QWeiboApi.class;
    private static final String prefix = "QQ";
    
    public QQAuth(Context context) {
        super(context, classApi, prefix);
    }

    public static boolean isAuth(Context context) {
        return BaseOAuth.isAuth(context, prefix);
    }
}
