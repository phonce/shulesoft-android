package apps.inets.com.shulesoft.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import android.net.http.*;

import apps.inets.com.shulesoft.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String FINAL_URL = ".shulesoft.com/signin/index";

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //Set up webview and load url of selected school
//        WebView webView = findViewById(R.id.webview);
        Intent intent = getIntent();
        String schoolName = intent.getStringExtra("School");
        String url = "https://" + schoolName + FINAL_URL;


        webView = findViewById(R.id.webview);
        startWebView(url);

    }

    /**
     * Configures the webview
     * @param url
     */

    private void startWebView(final String url) {


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("Android WebView");

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(WebViewActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                }
            }

            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
//                try{
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }else{
//                        Log.v("Dialog", "error2");
//                    }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                    Log.v("Dialog", "error");
//                }
            }
        });


        //Loads the URL if network is available
        TextView noInternet = findViewById(R.id.noInternet_text_view);
        if (isNetworkAvailable()) {
            webView.loadUrl(url);
            noInternet.setVisibility(View.GONE);
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Open previous opened link from history on webview when back button pressed
     */
    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }

    }
    /**
     * Checks if network connection is available
     */

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}