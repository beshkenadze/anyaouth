
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

package net.beshkenadze.anyoauth.oauth.vk;

import net.beshkenadze.anyoauth.oauth.BaseOAuth;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.VkontakteApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import com.google.gson.Gson;

import android.content.Context;
import org.scribe.oauth.OAuthService;

public class VkAuth extends BaseOAuth {

    private static Class<? extends Api> classApi = VkontakteApi.class;
    private static final String GET_USER_INFO = null;
    private static final String consumerKey = "xxxxxxxxx";
    private static final String consumerSecret = "xxxxxxxx";
    private static final String[] scopes = {"offline", "wall", "messages"};
    public static String callback = "http://api.vk.com/blank.html";
    private static final String prefix = "Vk";

    private static final String API_HOST = "https://api.vk.com/method/";
    private static final String WALL_POST = API_HOST + "wall.post";

    public VkAuth(Context context) {
        super(context, classApi, prefix);
    }

    public static boolean isAuth(Context context) {
        return BaseOAuth.isAuth(context, prefix);
    }

    public static void post(final Context context, final String message, final VkWall.OnWallRequest onWallRequest) {
        final Gson gson = new Gson();
        new Thread() {
            @Override
            public void run() {
                OAuthRequest request = new OAuthRequest(Verb.POST, WALL_POST);
                request.addBodyParameter("message", message);

                // START SIGN REQUEST
                OAuthService service = BaseOAuth.getService(context, classApi, prefix);
                service.signRequest(BaseOAuth.getAccessToken(context, prefix), request);
                // END SIGN REQUEST

                Response response = request.send();
                VkWallResponse vkResponse = gson.fromJson(response.getBody(), VkWallResponse.class);
                if (vkResponse.isSuccess()) {
                    onWallRequest.onSuccess(vkResponse.getWall());
                } else {
                    onWallRequest.onError(vkResponse.getError());
                }
            }
        }.start();
    }
}
