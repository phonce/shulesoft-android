package apps.inets.com.shulesoft.activities;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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


import java.util.HashMap;

import apps.inets.com.shulesoft.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String FINAL_URL = ".shulesoft.com/signin/index";

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //Set up webview and load url of selected school
        Intent intent = getIntent();
        String schoolName = intent.getStringExtra("School");
        HashMap<String, String> schoolMaps = (HashMap<String, String>) intent.getSerializableExtra("SchoolMaps");

        String url = "https://" + schoolMaps.get(schoolName) + FINAL_URL;

        TextView noInternet = findViewById(R.id.noInternet_text_view);
        webView = findViewById(R.id.webview);
        if(!isNetworkAvailable()){
            noInternet.setVisibility(View.VISIBLE);
        }else{
            noInternet.setVisibility(View.GONE);
            startWebView(url);

        }

    }

    /**
     * Configures the webview
     * @param url to be viewed
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
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }
                return true;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(WebViewActivity.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.setIndeterminate(true);
                }
                if(!progressDialog.isShowing()) {
                    progressDialog.show();
                }

            }

            public void onPageFinished(WebView view, String url) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        });
        //webView.loadUrl(url);

        //Loads the URL if network is available
        TextView noInternet = findViewById(R.id.noInternet_text_view);
        if (isNetworkAvailable()) {
            webView.loadUrl(url);
            noInternet.setVisibility(View.GONE);
        } else{
            webView.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Opens previous opened link in Webview when back button pressed or goes back to previous activity
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
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}