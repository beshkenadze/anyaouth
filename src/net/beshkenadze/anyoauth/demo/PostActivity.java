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

package net.beshkenadze.anyoauth.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.beshkenadze.anyoauth.R;
import net.beshkenadze.anyoauth.oauth.FbAuthActivity;
import net.beshkenadze.anyoauth.oauth.FqAuthActivity;
import net.beshkenadze.anyoauth.oauth.TwAuthActivity;
import net.beshkenadze.anyoauth.oauth.VkAuthActivity;
import net.beshkenadze.anyoauth.oauth.fb.FbAuth;
import net.beshkenadze.anyoauth.oauth.fb.FbError;
import net.beshkenadze.anyoauth.oauth.fb.FbFeed;
import net.beshkenadze.anyoauth.oauth.fq.Checkins;
import net.beshkenadze.anyoauth.oauth.fq.FqAuth;
import net.beshkenadze.anyoauth.oauth.fq.Meta;
import net.beshkenadze.anyoauth.oauth.tw.Status;
import net.beshkenadze.anyoauth.oauth.tw.TwAuth;
import net.beshkenadze.anyoauth.oauth.vk.VkAuth;
import net.beshkenadze.anyoauth.oauth.vk.VkError;
import net.beshkenadze.anyoauth.oauth.vk.VkWall;

public class PostActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int VK_AUTH = 1;
    private static final int FQ_AUTH = 2;
    private static final int TW_AUTH = 3;
    private static final int FB_AUTH = 4;

    private CheckBox mVkBox;
    private CheckBox mFqBox;
    private CheckBox mTwBox;
    private CheckBox mFbBox;

    private boolean isPostInFq;
    private boolean isPostInTw;
    private boolean isPostInVk;
    private boolean isPostInFb;
    private EditText mPostMessage;
    private Button mPostBtn;
    private TextView mTextCheckins;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        mVkBox = (CheckBox) findViewById(R.id.cBoxVk);
        mVkBox.setOnCheckedChangeListener(this);

        if (VkAuth.isAuth(PostActivity.this)) {
            mVkBox.setChecked(true);
        }

        mFqBox = (CheckBox) findViewById(R.id.cBoxFq);
        mFqBox.setOnCheckedChangeListener(this);

        if (FqAuth.isAuth(PostActivity.this)) {
            mFqBox.setChecked(true);
        }

        mTwBox = (CheckBox) findViewById(R.id.cBoxTw);
        mTwBox.setOnCheckedChangeListener(this);

        if (TwAuth.isAuth(PostActivity.this)) {
            mTwBox.setChecked(true);
        }

        mFbBox = (CheckBox) findViewById(R.id.cBoxFb);
        mFbBox.setOnCheckedChangeListener(this);


        if (FbAuth.isAuth(PostActivity.this)) {
            mFbBox.setChecked(true);
        }

        mPostMessage = (EditText) findViewById(R.id.eTextPost);

        mPostBtn = (Button) findViewById(R.id.btnPost);
        mPostBtn.setOnClickListener(this);


        mTextCheckins = (TextView) findViewById(R.id.eTextCheckins);


        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES|PackageManager.GET_META_DATA);
            Log.i("ApplicationInfo", "TwKey: " + ai.metaData.getString("TwKey"));
            Log.i("ApplicationInfo", "TwSecret: " + ai.metaData.getString("TwSecret"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getCheckins() {
        // GET CHECKINS
        FqAuth.getCheckins(PostActivity.this, new Checkins.OnCheckinsRequest() {
            @Override
            public void onSuccess(final Checkins checkins) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String total = String.format("Total checkins: %d", checkins.getCount());
                        Toast.makeText(PostActivity.this, total,
                                Toast.LENGTH_SHORT).show();
                        mTextCheckins.setText(total);
                    }
                });
            }

            @Override
            public void onError(final Meta meta) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PostActivity.this, meta.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        // GET CHECKINS
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPost:
                postMessage();
                break;
        }
    }

    // POSTING
    private void postMessage() {
        if (mPostMessage.getText().length() > 0) {
            String message = mPostMessage.getText().toString();
            if (isPostInTw) {
                TwAuth.post(PostActivity.this, message, new Status.OnStatusRequest() {
                    @Override
                    public void onSuccess(final Status status) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostActivity.this, String.format("Vokntakte post id: %s", status.getId()),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(final Status status) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostActivity.this, status.getError(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            if (isPostInVk) {
                VkAuth.post(PostActivity.this, message, new VkWall.OnWallRequest() {
                    @Override
                    public void onSuccess(final VkWall wall) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostActivity.this, String.format("Vokntakte post id: %s", wall.getPostId()),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(final VkError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostActivity.this, error.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            if (isPostInFb) {
                FbAuth.post(PostActivity.this, message, new FbFeed.OnFeedRequest() {
                    @Override
                    public void onError(final FbError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final FbFeed feed) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostActivity.this, String.format("Facebook post id: %s", feed.getId()),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cBoxVk:
                if (VkAuth.isAuth(PostActivity.this)) {
                    isPostInVk = b;
                } else {
                    mVkBox.setChecked(false);
                    startActivityForResult(new Intent(PostActivity.this, VkAuthActivity.class), VK_AUTH);
                }
                break;
            case R.id.cBoxFq:
                if (FqAuth.isAuth(PostActivity.this)) {
                    isPostInFq = b;
                    if (b) {
                        getCheckins();
                    }
                } else {
                    mFqBox.setChecked(false);
                    startActivityForResult(new Intent(PostActivity.this, FqAuthActivity.class), FQ_AUTH);
                }
                break;
            case R.id.cBoxTw:
                if (TwAuth.isAuth(PostActivity.this)) {
                    isPostInTw = b;
                } else {
                    mTwBox.setChecked(false);
                    startActivityForResult(new Intent(PostActivity.this, TwAuthActivity.class), TW_AUTH);
                }
                break;
            case R.id.cBoxFb:
                if (FbAuth.isAuth(PostActivity.this)) {
                    isPostInFb = b;
                } else {
                    mFbBox.setChecked(false);
                    startActivityForResult(new Intent(PostActivity.this, FbAuthActivity.class), FB_AUTH);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data
    ) {
        switch (requestCode) {
            case VK_AUTH:
                if (resultCode == Activity.RESULT_OK) {
                    mVkBox.setChecked(true);
                }
                break;
            case FQ_AUTH:
                if (resultCode == Activity.RESULT_OK) {
                    mFqBox.setChecked(true);
                }
                break;
            case TW_AUTH:
                if (resultCode == Activity.RESULT_OK) {
                    mTwBox.setChecked(true);
                }
                break;
            case FB_AUTH:
                if (resultCode == Activity.RESULT_OK) {
                    mFbBox.setChecked(true);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}