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

public class VkUser {
	private String user_id = "0";
	private String  user_name = "";
	public String getUserId() {
		return user_id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	public String getUserName() {
		return user_name;
	}
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}
}
