package apps.inets.com.shulesoft.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });


        //webView.loadUrl(yourUrl);


        //ProgressDialog progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        //progDailog.setCancelable(false);


        //Log.v("URL",""+a+"haha"+b);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("Android WebView");

TextView noInternet = findViewById(R.id.noInternet_text_view);
        if(isNetworkAvailable()){
            webView.loadUrl(url);
            noInternet.setVisibility(View.GONE);
        }else{
            noInternet.setVisibility(View.VISIBLE);
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

    private class MyWebViewClient extends WebViewClient {
        boolean timeout;

        public MyWebViewClient() {
            timeout = true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /*@Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.v("Loading time", "This is a big exception");
                        //e.printStackTrace();
                    }
                    if(timeout) {
                        // do what you want
                        Log.v("Loading time", "Time is up");
                    }
                }
            }).start();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            timeout = false;
        }*/
    }

}
