
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

package net.beshkenadze.anyoauth.oauth.fb;

import android.content.Context;
import com.google.gson.Gson;
import net.beshkenadze.anyoauth.oauth.BaseOAuth;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class FbAuth extends BaseOAuth {

    private static Class<? extends Api> classApi = FacebookApi.class;
    private static final String GET_USER_INFO = null;
    private static final String consumerKey = "xxxxxxxx";
    private static final String consumerSecret = "xxxxxxxx";
    private static final String[] scopes = {"user_status", "user_likes", "user_location", "user_activities", "publish_stream"};
    public static String callback = "http://caboo.com/facebook_auth";
    private static final String prefix = "Fb";

    private static final String GRAPH_API = "https://graph.facebook.com/";
    private static final String GRAPH_FEED_API = GRAPH_API + "me/feed/";

    public FbAuth(Context context) {
        super(context, classApi, prefix);
    }

    public static boolean isAuth(Context context) {
        return BaseOAuth.isAuth(context, prefix);
    }


    public static void post(final Context context, final String message, final FbFeed.OnFeedRequest onFeedRequest) {
        final Gson gson = new Gson();
        new Thread() {
            @Override
            public void run() {
                OAuthRequest request = new OAuthRequest(Verb.POST, GRAPH_FEED_API);
                request.addBodyParameter("message", message);

                // START SIGN REQUEST
                OAuthService service = BaseOAuth.getService(context, classApi, prefix);
                service.signRequest(BaseOAuth.getAccessToken(context, prefix), request);
                // END SIGN REQUEST

                Response response = request.send();
                FbFeed feed = gson.fromJson(response.getBody(), FbFeed.class);
                if (feed.isSuccess()) {
                    onFeedRequest.onSuccess(feed);
                } else {
                    onFeedRequest.onError(feed.getError());
                }
            }
        }.start();
    }
}
