package apps.inets.com.shulesoft.extras;

//package services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.activities.MainActivity;

/**
 * This background service checks for Internet connection
 */
public class ConnectionService extends Service {

    // Constant
    public static String TAG_INTERVAL = "interval";
    public static String TAG_URL_PING = "url_ping";
    public static String TAG_ACTIVITY_NAME = "activity_name";

    private int interval;
    private String url_ping;
    private String activity_name;

    private Timer mTimer = null;

    ConnectionServiceCallback mConnectionServiceCallback;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public interface ConnectionServiceCallback {
        void hasInternetConnection();

        void hasNoInternetConnection();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        interval = intent.getIntExtra(TAG_INTERVAL, 10);
        url_ping = intent.getStringExtra(TAG_URL_PING);
        activity_name = intent.getStringExtra(TAG_ACTIVITY_NAME);


        try {
            mConnectionServiceCallback = (ConnectionServiceCallback) Class.forName(activity_name).newInstance();
            Log.v("CALLBACK","YEAH");
            Log.v("DYBE",""+activity_name);

        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.v("ISTANTIATIONEXIONCAUGHT","YEAH");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.v("ISTANTIATIONEXIONCAUGHT","YEAH");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.v("ISTANTIATIONEXIONCAUGHT","YEAH");
        }

        Log.v("STARTED_SERVICE", "YES");
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new CheckForConnection(), 0, interval * 1000);

        return super.onStartCommand(intent, flags, startId);
    }

    class CheckForConnection extends TimerTask {
        @Override
        public void run() {
            isNetworkAvailable();
        }
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    private boolean isNetworkAvailable() {
        HttpGet httpGet = new HttpGet(url_ping);
        HttpParams httpParameters = new BasicHttpParams();


        //Toast.makeText(this, getResources().getString(R.string.wrong_details), Toast.LENGTH_LONG).show();

     int timeoutConnection = 5000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        int timeoutSocket = 7000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        try {
            httpClient.execute(httpGet);
            mConnectionServiceCallback.hasInternetConnection();
            return true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mConnectionServiceCallback.hasNoInternetConnection();
        return false;

    }


}







































