
/*
 *
 *  * Copyright (C) 2013 Aleksandr Beshkenadze <behskenadze@gmail.com>
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package net.beshkenadze.anyoauth.oauth.fq;

import android.content.Context;
import com.google.gson.Gson;
import net.beshkenadze.anyoauth.oauth.BaseOAuth;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.Foursquare2Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FqAuth extends BaseOAuth {

    private static Class<? extends Api> classApi = Foursquare2Api.class;
    private static final String GET_USER_INFO = null;
    private static final String consumerKey = "xxxxxxxx";
    private static final String consumerSecret = "xxxxxxxx";
    private static final String[] scopes = {};
    public static String callback = "http://site.ru";
    private static final String prefix = "Fq";

    private static final String API_HOST = "https://api.foursquare.com/";
    private static final String CHECKINS = API_HOST + "v2/users/self/checkins?oauth_token=";

    public FqAuth(Context context) {
        super(context, classApi, prefix);
    }

    public static boolean isAuth(Context context) {
        return BaseOAuth.isAuth(context, prefix);
    }

    public static void getCheckins(final Context context, final Checkins.OnCheckinsRequest onCheckinsRequest) {
        final Gson gson = new Gson();
        new Thread() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

                String check = dateFormat.format(calendar.getTime());

                Token accessToken = BaseOAuth.getAccessToken(context, prefix);
                OAuthRequest request = new OAuthRequest(Verb.GET, CHECKINS + accessToken.getToken());
                request.addQuerystringParameter("v", check);

                // START SIGN REQUEST
                OAuthService service = BaseOAuth.getService(context, classApi, prefix);
                service.signRequest(accessToken, request);
                // END SIGN REQUEST

                org.scribe.model.Response response = request.send();
                Body fqBody = gson.fromJson(response.getBody(), Body.class);
                if (fqBody.isSuccess()) {
                    onCheckinsRequest.onSuccess(fqBody.getResponse().getCheckins());
                } else {
                    onCheckinsRequest.onError(fqBody.getMeta());
                }
            }
        }.start();
    }
}
