package icte.cph.aau.washingmachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.auth0.core.Token;
import com.auth0.core.UserProfile;
import com.auth0.lock.Lock;
import com.auth0.lock.LockActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.SharedPreferencesHolder;
import icte.cph.aau.washingmachine.utils.VolleyJsonRequest;
import icte.cph.aau.washingmachine.utils.VolleySingleTon;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = AuthenticationActivity.class.getSimpleName();
    private LocalBroadcastManager broadcastManager;

    private BroadcastReceiver authenticationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Retrieve user information from the ID provider via Auth0
            UserProfile profile = intent.getParcelableExtra(Lock.AUTHENTICATION_ACTION_PROFILE_PARAMETER);
            String userID = profile.getId();
            String email = profile.getEmail();
            String name = profile.getName();
            String profilePic = profile.getPictureURL();

            //Get a token object, which returns an Access-token, ID-token etc.
            Token token = intent.getParcelableExtra(Lock.AUTHENTICATION_ACTION_TOKEN_PARAMETER);
            String idToken = token.getIdToken();
            String accessToken = token.getAccessToken();

            //Concatenate all user information into one String
            String userInfo =
                    "UID: " + userID + "\n"
                            + "Email: " + email + "\n"
                            + "Name: " + name + "\n"
                            + "Picture: " + profilePic + "\n";

            Log.d(TAG, "onReceive: profile: " + userInfo);
            Log.d(TAG, "onReceive: Token: "
                    + "Access token: "
                    + accessToken + "\n"
                    + "ID token: " + idToken);

            //Divide the user's name into their first name and last name.
            int i = name.indexOf(' ');
            String fname = name.substring(0, i);
            String lname = name.substring(i);

            //Insert retrieved user information into WM's database
            insertUser(userID, fname, lname, email);

            //Save the UID in cache locally on the phone.
            SharedPreferencesHolder sp = new SharedPreferencesHolder(AuthenticationActivity.this);
            sp.saveString(Constants.SP_UID, userID);

            //Start next Activity
            startActivity(new Intent(AuthenticationActivity.this,   WashingMachineActivity.class));
        }
    };

    private void insertUser(
            String userID,
            String fname,
            String lname,
            String email
    ) {

        Map<String, String> map = new HashMap<>();
        map.put(Constants.TAG_UID, userID);
        map.put(Constants.TAG_FNAME, fname);
        map.put(Constants.TAG_LNAME, lname);
        map.put(Constants.TAG_EMAIL, email);

        RequestQueue requestQueue = VolleySingleTon.getInstance().getRequestQueue();
        VolleyJsonRequest jsObjRequest = new VolleyJsonRequest(
                Request.Method.POST,
                Constants.URL_INSERT_USER_ID,
                map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt(Constants.TAG_SUCCESS);
                            String message = response.optString(Constants.TAG_MESSAGE);

                            if (success == 1) {
                                startActivity(new Intent(AuthenticationActivity.this, RegisterWashingMachineActivity.class));
                                Toast.makeText(AuthenticationActivity.this, "User successfully added", Toast.LENGTH_SHORT).show();
                            } else if (success == 2) {
                                Log.d(TAG, "insertUser: " + message);
                            } else
                                Toast.makeText(getApplicationContext(), "Error, could not login", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse: message: " + message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);

                    }
                }
        );

        requestQueue.add(jsObjRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticationActivity.this, LockActivity.class));
            }
        });


        broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(authenticationReceiver, new IntentFilter(Lock.AUTHENTICATION_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(authenticationReceiver);
    }
}
