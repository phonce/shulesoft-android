package apps.inets.com.shulesoft.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.extras.SchoolListQuery;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    SchoolListQuery schoolListQuery;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private String mSchoolName;

    /**
     * Email or phone number entered
     */
    private String mEmail;

    /**
     * Password entered
     */
    private String mPassword;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;

    private ArrayList<String> mSchools;
    private HashMap<String, String> schoolMaps;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private RequestQueue mRequestQueue;
    private ProgressBar login_progressBar;
    private Spinner schoolSelect_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        schoolListQuery = new SchoolListQuery();
        login_progressBar = findViewById(R.id.login_progress);
        schoolSelect_spinner = findViewById(R.id.spinner_select_school);

        mRequestQueue = Volley.newRequestQueue(this);

        //Set up the email view,password view and the login button
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        checkInternetConnection();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //Gets and stores the clicked school name from searchActivity
        /*Intent intent = getIntent();
        Bundle intentData = intent.getExtras();
        if (intentData != null) {
            mSchoolName = (String) intentData.get("School");
        } */

        //Start Async Task for Background process
        new SchoolParser(LoginActivity.this, schoolSelect_spinner).execute();

    }

    /**
     * Saves username and password entered
     */
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = mEmailView.getText().toString();
        PasswordValue = mPasswordView.getText().toString();
        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_PASSWORD, PasswordValue);
        editor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        this.finishAffinity();
        this.finish();

    }

    /**
     * Loads the saved password and username and fills the respective fields
     */
    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        mEmailView.setText(UnameValue);
        mPasswordView.setText(PasswordValue);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();

    }

    /**
     * Makes a network request to login a user by sending a post request to the server
     * and interpreting the response
     */
    public void makeHttpRequest(final String schoolName, String username, String password) {
        String getSchoolsUrl = "http://158.69.112.216:8081/api/login";

        JSONObject params = new JSONObject();

        try {
            params = params.put("School", schoolName);
            params = params.put("username", username);
            params = params.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getSchoolsUrl, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {
                        try {
                            String fromServer = responseObject.getString("message");
                            if (fromServer.equals("success")) {
                                Intent intent = new Intent
                                        (LoginActivity.this, HomeScreenActivity.class);
                                intent.putExtra("School", schoolName);
                                startActivity(intent);
                            } else {

                                wrongDetails();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v("Response", "There is a response");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("HAHAHAH", error.toString());
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    /**
     * Attempts to sign in.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(mPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;

        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            makeHttpRequest("beta_testing", mEmail, mPassword);
        }
    }


    public String getEmail() {
        if (isEmailValid(mEmail)) {
            return mEmail;
        }
        return null;
    }

    public String getPassword() {

        if (isPasswordValid(mPassword)) {
            return mPassword;
        }
        return null;
    }

    private boolean isEmailValid(String email) {

        return email.contains("@") || email.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Displays a toast when user has entered wrong details and restarts the activity
     */
    public void wrongDetails() {
        Toast.makeText(this, getResources().getString(R.string.wrong_details), Toast.LENGTH_LONG).show();
        recreate();
    }

    /**
     * Opens terms and privacy webpage in the user's browser
     */
    public void openTermsAndPrivacy(View view) {
        String url = "https://www.shulesoft.com/shulesoft-school-management-system-privacy-policy/";
        Intent privacyIntent = new Intent(Intent.ACTION_VIEW);
        privacyIntent.setData(Uri.parse(url));
        startActivity(privacyIntent);
    }


    /*@Override
    public void onBackPressed() {
        startActivity(new Intent(this, SchoolSearchActivity.class));
    }
*/

    //Background Process To get School names into Spinner drop down List
    public class SchoolParser extends AsyncTask<Void, Void, Boolean> {


        Context context;
        Spinner spinner;

        public SchoolParser(Context context, Spinner spinner) {
            this.context = context;
            this.spinner = spinner;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login_progressBar.setVisibility(View.VISIBLE);
            login_progressBar.setProgress(2000, true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return getSchool();
        }


        @Override
        protected void onPostExecute(Boolean isParsed) {
            super.onPostExecute(isParsed);
            login_progressBar.setVisibility(View.GONE);
            if (isParsed) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, mSchools);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // mEmailView.setEnabled(true);
                        //  mPasswordView.setEnabled(true);
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            } else {
                Toast.makeText(LoginActivity.this, "Unable To parse", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public Boolean getSchool() {

        Intent intentGetSchools = getIntent();
        schoolMaps = (HashMap<String, String>) intentGetSchools.getSerializableExtra("Schools");

        mSchools = new ArrayList<>();
        Iterator it = schoolMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            mSchools.add((String) pair.getKey());
        }

        return true;

    }

    public void checkInternetConnection() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.initializing_text_view);
                Toast.makeText(LoginActivity.this, R.string.slow_interent, Toast.LENGTH_SHORT).show();
            }
        }, 4000L);

    }

}



