package net.beshkenadze.anyoauth.oauth;

import net.beshkenadze.anyoauth.oauth.qq.QQAuth;
import android.os.Bundle;

/**
 * @author aleksej.grigorjev@gmail.com
 */

public class QQAuthActivity extends BaseOAuthViewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	QQAuth auth = new QQAuth(this);
        setBaseAuth(auth);
        super.onCreate(savedInstanceState);
    }
}