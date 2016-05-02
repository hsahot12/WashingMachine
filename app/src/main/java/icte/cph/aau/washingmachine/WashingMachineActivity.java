package icte.cph.aau.washingmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import icte.cph.aau.washingmachine.adapter.MyWMAdapter;
import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.SharedPreferencesHolder;
import icte.cph.aau.washingmachine.utils.VolleyJsonRequest;
import icte.cph.aau.washingmachine.utils.VolleySingleTon;

public class WashingMachineActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> resultArray;
    private MyWMAdapter adapter;
    private RecyclerView my_washing_machine_recyclerview;
    private ProgressBar my_washing_machine_progressbar;

    private static final String TAG = WashingMachineActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing_machine);

        //Initiating progressBar
        my_washing_machine_progressbar = (ProgressBar) findViewById(R.id.my_washing_machine_progressbar);

        //Initiating RecyclerView
        my_washing_machine_recyclerview = (RecyclerView) findViewById(R.id.my_washing_machine_recyclerview);
        my_washing_machine_recyclerview.setLayoutManager(new LinearLayoutManager(WashingMachineActivity.this));
        my_washing_machine_recyclerview.setHasFixedSize(true); //For performance improvements

        loadMyWashingMachineData();
    }

    private void loadMyWashingMachineData() {
        my_washing_machine_progressbar.setVisibility(View.VISIBLE);

        resultArray = new ArrayList<>();
        SharedPreferencesHolder sp = new SharedPreferencesHolder(WashingMachineActivity.this);
        String WID = sp.loadString(Constants.SP_WID);
        String UID = sp.loadString(Constants.SP_UID);

        Log.d(TAG, "loadMyWashingMachineData: WID: " + WID);

        //Defining the POST parameters.
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TAG_UID, UID);
        map.put(Constants.TAG_WID, WID);

        //Creating a Volley instance to do async. network operations.
        RequestQueue requestQueue = VolleySingleTon.getInstance().getRequestQueue();
        VolleyJsonRequest jsObjRequest = new VolleyJsonRequest(
                Request.Method.POST,
                Constants.URL_MY_WM,
                map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt(Constants.TAG_SUCCESS);
                            String message = response.optString(Constants.TAG_MESSAGE);

                            if (success == 1) {
                                JSONArray resultArr = response.getJSONArray(Constants.TAG_RESULT);

                                for (int i = 0; i < resultArr.length(); i++) {
                                    JSONObject wObj = resultArr.getJSONObject(i);

                                    String wmbID = wObj.optString(Constants.TAG_WMB_ID);
                                    String wmbName = wObj.optString(Constants.TAG_WMB_NAME);
                                    String brand = wObj.optString(Constants.TAG_BRAND);

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put(Constants.TAG_WMB_ID, wmbID);
                                    hashMap.put(Constants.TAG_WMB_NAME, wmbName);
                                    hashMap.put(Constants.TAG_BRAND, brand);

                                    resultArray.add(hashMap);
                                }


                            } else
                                Toast.makeText(getApplicationContext(), "Error, could not find data", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse: message: " + message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new MyWMAdapter(resultArray, WashingMachineActivity.this);
                        my_washing_machine_recyclerview.setAdapter(adapter);
                        my_washing_machine_recyclerview.setVisibility(View.VISIBLE);
                        my_washing_machine_progressbar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);

                    }
                }
        );

        //Starting network request
        requestQueue.add(jsObjRequest);

    }
}
