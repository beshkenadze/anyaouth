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

package net.beshkenadze.anyoauth.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MyPreference {
	private SharedPreferences prefs;
	private Map<String, ?> values;

	public MyPreference(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		values = prefs.getAll();
	}
	public Editor edit() {
		return prefs.edit();
	}
	public boolean containsKey(String key) {
		return prefs.contains(key);
	}
	public Object get(String key) {
		if(values.containsKey(key)) {
			return values.get(key);
		}
		return null;
	}
	public String getString(String key, String defValue) {
		return prefs.getString(key, defValue);
	}
	public void set(String key, String value) {
		Editor e = prefs.edit();
		e.putString(key, value);
		e.commit();
	}
	public void setString(String key,String value) {
		Editor e = prefs.edit();
		e.putString(key, value);
		e.commit();
	}
	public void setBoolean(String key, Boolean value) {
		Editor e = prefs.edit();
		e.putBoolean(key, value);
		e.commit();
	}
	public void setLong(String key, Long value) {
		Editor e = prefs.edit();
		e.putLong(key, value);
		e.commit();
	}
	public void setInt(String key, Integer value) {
		Editor e = prefs.edit();
		e.putInt(key, value);
		e.commit();
	}
	public Boolean getBoolean(String key, Boolean defValue) {
		return prefs.getBoolean(key, defValue);
	}
	public Long getLong(String key, Long defValue) {
		return prefs.getLong(key, defValue);
	}
	public Integer getInt(String key, Integer defValue) {
		return prefs.getInt(key, defValue);
	}
	public String getString(String key) {
		if(prefs.contains(key)) {
			return prefs.getString(key, null);
		}
		return null;
	}
    public SharedPreferences getBasePreference(){
        return prefs;
    }
}
