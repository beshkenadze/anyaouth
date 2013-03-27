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

/**
 * Contact: akira
 * Date: 24.12.12
 */
public class Status {
    private String id_str = "0";
    private String created_at = "0";
    private String error = "0";

    public interface OnStatusRequest {

        void onSuccess(Status status);

        void onError(Status status);
    }

    public String getId() {
        return id_str;
    }

    public void setId(String id_str) {
        this.id_str = id_str;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return getError() == null;
    }
}
