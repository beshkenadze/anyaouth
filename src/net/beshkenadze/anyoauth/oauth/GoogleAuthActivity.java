package net.beshkenadze.anyoauth.oauth;

import net.beshkenadze.anyoauth.oauth.google.GoogleAuth;
import android.os.Bundle;

/**
 * @author aleksej.grigorjev@gmail.com
 */

public class GoogleAuthActivity extends BaseOAuthViewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	GoogleAuth auth = new GoogleAuth(this);
        setBaseAuth(auth);
        super.onCreate(savedInstanceState);
    }
}