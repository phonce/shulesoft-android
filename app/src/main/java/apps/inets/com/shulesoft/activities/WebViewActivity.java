package apps.inets.com.shulesoft.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        String url = "https://" + schoolName + FINAL_URL;

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());

        ProgressDialog progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);



        Log.v("URL",""+a+"haha"+b);

       webView.getSettings().setJavaScriptEnabled(true);
       webView.getSettings().setLoadsImagesAutomatically(true);
       //webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("Android WebView");


        Log.v("URL",url);



    }

    private class MyWebViewClient extends WebViewClient {
        boolean timeout;

        public MyWebViewClient() {
            timeout = true;
        }

        @Override
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
        }
    }

}
