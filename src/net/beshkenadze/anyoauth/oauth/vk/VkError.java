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

public class VkError {
	private int error_code = 0;
	private String error_msg = "";

	public String getErrorMsg() {
		return error_msg;
	}

	public void setErrorMsg(String error_msg) {
		this.error_msg = error_msg;
	}

	public int getErrorCode() {
		return error_code;
	}

	public void setErrorCode(int error_code) {
		this.error_code = error_code;
	}
}
