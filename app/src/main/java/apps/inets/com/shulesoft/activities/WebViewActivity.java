package apps.inets.com.shulesoft.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Date;

import apps.inets.com.shulesoft.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String FINAL_URL = ".shulesoft.com/signin/index";

    private WebView webView;
    private long a;

    private long b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //Set up webview and load url of selected school
//        WebView webView = findViewById(R.id.webview);
        Intent intent = getIntent();
        String schoolName = intent.getStringExtra("School");
        String url = "https://" + schoolName + FINAL_URL;;


        webView = findViewById(R.id.webview);
        startWebView(url);
//        webView.setWebViewClient(new WebViewClient());

//        ProgressDialog progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
//        progDailog.setCancelable(false);


//        Log.v("URL", "" + a + "haha" + b);
//
//       //webView.getSettings().setJavaScriptEnabled(true);
//       webView.getSettings().setLoadsImagesAutomatically(true);
//       webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setDisplayZoomControls(true);
//        webView.loadUrl("https://makongo.shulesoft.com");
//        webView.getSettings().setUserAgentString("Android WebView");
//
//
//        Log.v("URL",url);

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
            handler.proceed();
            }

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
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

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);


        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.loadUrl(url);
    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

}
