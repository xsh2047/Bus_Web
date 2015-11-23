package com.jamaica.bus.busapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.jamica.bus.busapp.activities.R;


public class HTML5WebView extends WebView {

    private Context                             mContext;
    private MyWebChromeClient                   mWebChromeClient;
    private View                                mCustomView;
    private FrameLayout                         mCustomViewContainer;
    private WebChromeClient.CustomViewCallback  mCustomViewCallback;

    private FrameLayout                         mContentView;
    private FrameLayout                         mBrowserFrameLayout;
    private FrameLayout                         mLayout;

    //Activity a;
    //ProgressDialog progressDialog;

    static final String LOGTAG = "HTML5WebView";

    private void init(Context context) {
        mContext = context;     
       // a = (Activity) mContext;

        mLayout = new FrameLayout(context);

        mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_screen, null);
        //mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(a).inflate(R.layout.custom_screen, null);
        mContentView = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.main_content);
        mCustomViewContainer = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.fullscreen_custom_content);

        mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);

        // Configure the webview
        WebSettings s = getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
      //  s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        setInitialScale(1);
        mWebChromeClient = new MyWebChromeClient();
        setWebChromeClient(mWebChromeClient);
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // enable navigator.geolocation 
       // s.setGeolocationEnabled(true);
       // s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");

        // enable Web Storage: localStorage, sessionStorage
        s.setDomStorageEnabled(true);

        mContentView.addView(this);

      /*  //For Progress Dialog
        progressDialog = new ProgressDialog(a);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); */
    }

    public HTML5WebView(Context context) {
        super(context);
        init(context);
    }

    public HTML5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HTML5WebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FrameLayout getLayout() {
        return mLayout;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    private class MyWebChromeClient extends WebChromeClient {
        private Bitmap      mDefaultVideoPoster;
        private View        mVideoProgressView;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback)
        {
            //Log.i(LOGTAG, "here in on ShowCustomView");
            HTML5WebView.this.setVisibility(View.GONE);

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);


        }

        @Override
        public void onHideCustomView() {
            //System.out.println("customview hideeeeeeeeeeeeeeeeeeeeeeeeeee");
            if (mCustomView == null)
                return;        

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();

            HTML5WebView.this.setVisibility(View.VISIBLE);
            HTML5WebView.this.goBack();
            //Log.i(LOGTAG, "set it to webVew");
        }


        @Override
        public View getVideoLoadingProgressView() {
            //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                mVideoProgressView = inflater.inflate(R.layout.video_loading_progress, null);
            }
            return mVideoProgressView; 
        }

         @Override
         public void onReceivedTitle(WebView view, String title) {
            ((Activity) mContext).setTitle(title);
         }


         @Override
         public void onProgressChanged(WebView view, int newProgress) {
            ((Activity) mContext).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
           /*  // Functionality for Progress Dialog
             progressDialog.show();
             progressDialog.setProgress(0);
             a.setProgress(newProgress * 1000);

             progressDialog.incrementProgressBy(newProgress);

             if(newProgress == 100 && progressDialog.isShowing()){
                 progressDialog.dismiss();
            } */
         }

         @Override
         public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
             callback.invoke(origin, true, false);
         }
    }


    static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
        new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
}