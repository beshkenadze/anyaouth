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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.scribe.model.Verifier;

public class BaseOAuthViewActivity extends Activity {
    private WebView mWebview;
    private BaseOAuth baseAuth;
    private Handler mHandler = new Handler();
    private boolean mFlagOAuthCallbackStop = false;
    private BaseOAuth.ReturnCallback returnCallback = new BaseOAuth.ReturnCallback() {
        @Override
        public void onSuccess() {
            Intent _result = new Intent();
            setResult(Activity.RESULT_OK, _result);
            finish();
        }

        @Override
        public void onError() {
            Intent _result = new Intent();
            setResult(Activity.RESULT_CANCELED, _result);
            finish();
        }
    };

    public interface BaseOnApiAuthUrlReturn {
        void onReturn(String authorizationUrl);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.startsWith(BaseOAuth.getCallback())
                    && !mFlagOAuthCallbackStop) {
                oauthCallback(url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseAuth().setReturnCallback(returnCallback);
        initWebView(BaseOAuthViewActivity.this);
    }

    protected void initWebView(Context context) {
        mWebview = new WebView(context);
        setContentView(mWebview);
        getBaseAuth().getAuthUrl(new BaseOAuth.OnApiAuthUrlReturn() {
            @Override
            public void onReturn(final String authorizationUrl) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebview.getSettings().setJavaScriptEnabled(true);
                        mWebview.loadUrl(authorizationUrl);
                        mWebview.setWebViewClient(new MyWebViewClient());
                    }
                });
            }
        });
    }

    public void oauthCallback(String url) {
        mFlagOAuthCallbackStop = true;
        Uri uri = Uri.parse(url);

        String code = uri.getQueryParameter("code");
        String oauthToken = uri.getQueryParameter("oauth_token");
        String oauthVerifier = uri.getQueryParameter("oauth_verifier");

        if (code == null && uri.getFragment() != null && uri.getFragment().length() > 0) {
            String pseudoUrl = "http://example.com/?" + uri.getFragment();
            uri = Uri.parse(pseudoUrl);
            code = uri.getQueryParameter("code");
        }

        try {
            if (code != null) {
                Verifier verifier = new Verifier(code);
                getBaseAuth().requestAccessToken(verifier);
            } else if (oauthToken != null && oauthVerifier != null) {
                getBaseAuth().requestAccessToken(oauthToken, oauthVerifier);
            } else {
                getBaseAuth().getReturnCallback().onError();
            }
        } catch (Exception e) {
            getBaseAuth().getReturnCallback().onError();
        }
    }

    public BaseOAuth getBaseAuth() {
        return baseAuth;
    }

    public void setBaseAuth(BaseOAuth baseAuth) {
        this.baseAuth = baseAuth;
    }

    public BaseOAuth.ReturnCallback getReturnCallback() {
        return returnCallback;
    }

    public void setReturnCallback(BaseOAuth.ReturnCallback returnCallback) {
        this.returnCallback = returnCallback;
    }
}
