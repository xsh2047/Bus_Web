package com.jamaica.bus.busapp.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jamica.bus.busapp.activities.R;

public class WebActivity extends Activity {

    HTML5WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        browser = new HTML5WebView(this);
        WebSettings settings = browser.getSettings();

        //browser.getSettings().setAppCacheEnabled(true);
        //browser.getSettings().setDomStorageEnabled(true);
        browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        browser.setScrollbarFadingEnabled(false);
        browser.setWebViewClient(new MyBrowser());
        // Enable Javascript
        settings.setJavaScriptEnabled(true);
        // Use WideViewport and Zoom out if there is no viewport defined
        //settings.setUseWideViewPort(true);
        //settings.setLoadWithOverviewMode(true);

        settings.setGeolocationEnabled(true);
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36\n");

        // Disable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);



        browser.loadUrl("http://vidalcreates.github.io/busApp/");
        setContentView(browser.getLayout());
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
