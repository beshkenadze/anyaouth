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

/**
 * Contact: akira
 * Date: 24.12.12
 */
public class VkWallResponse {
    private VkWall response = new VkWall();
    private VkError error = new VkError();

    public VkWall getWall() {
        return response;
    }

    public void setWall(VkWall wall) {
        this.response = wall;
    }

    public VkError getError() {
        return error;
    }

    public void setError(VkError error) {
        this.error = error;
    }

    public boolean isSuccess() {
        if (getError().getErrorCode() > 0) {
            return false;
        }
        return true;
    }
}
