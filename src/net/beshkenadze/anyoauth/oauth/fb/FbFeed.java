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


public class FbFeed {
    private String id = "0";
    private FbError error = new FbError();

    public interface OnFeedRequest {
        void onError(FbError error);
        void onSuccess(FbFeed feed);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FbError getError() {
        return error;
    }

    public void setError(FbError error) {
        this.error = error;
    }

    public boolean isSuccess() {
        if (getError().getCode() > 0) {
            return false;
        }
        return true;
    }
}
