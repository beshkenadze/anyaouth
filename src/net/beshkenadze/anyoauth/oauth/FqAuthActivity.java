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

package net.beshkenadze.anyoauth.oauth;

import android.os.Bundle;
import net.beshkenadze.anyoauth.oauth.fq.FqAuth;

/**
 * Contact: akira
 * Date: 23.12.12
 * Time: 18:14
 */
public class FqAuthActivity extends BaseOAuthViewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FqAuth auth = new FqAuth(FqAuthActivity.this);
        setBaseAuth(auth);
        super.onCreate(savedInstanceState);
    }
}
