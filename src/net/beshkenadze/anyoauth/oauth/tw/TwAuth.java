
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

package net.beshkenadze.anyoauth.oauth.tw;

import android.content.Context;
import com.google.gson.Gson;
import net.beshkenadze.anyoauth.oauth.BaseOAuth;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TwAuth extends BaseOAuth {

    private static Class<? extends Api> classApi = TwitterApi.class;
    private static final String GET_USER_INFO = null;
    private static final String consumerKey = "xxxxxxxxx";
    private static final String consumerSecret = "xxxxxxxx";
    private static final String[] scopes = {};
    public static String callback = "http://site.ru";
    private static final String prefix = "Tw";

    private static final String API_HOST = "http://api.twitter.com/1/";
    private static final String API_STATUS_UPDATE = API_HOST + "statuses/update.json";

    public TwAuth(Context context) {
        super(context, classApi, prefix, true);
    }

    public static boolean isAuth(Context context) {
        return BaseOAuth.isAuth(context, prefix);
    }


    public static void post(final Context context, final String message, final Status.OnStatusRequest onStatusRequest) {
        final Gson gson = new Gson();
        new Thread() {
            @Override
            public void run() {
                OAuthRequest request = new OAuthRequest(Verb.POST, API_STATUS_UPDATE);
                request.addBodyParameter("status", message);

                // START SIGN REQUEST
                OAuthService service = BaseOAuth.getService(context, classApi, prefix);
                service.signRequest(BaseOAuth.getAccessToken(context, prefix), request);
                // END SIGN REQUEST

                Response response = request.send();
                Status status = gson.fromJson(response.getBody(), Status.class);
                if (status.isSuccess()) {
                    onStatusRequest.onSuccess(status);
                } else {
                    onStatusRequest.onError(status);
                }
            }
        }.start();
    }
}
