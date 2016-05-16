package icte.cph.aau.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import icte.cph.aau.washingmachine.RealmModel.RealmMyWashingMachine;
import icte.cph.aau.washingmachine.adapter.MyWMAdapter;
import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.ItemClickSupport;
import icte.cph.aau.washingmachine.utils.SharedPreferencesHolder;
import icte.cph.aau.washingmachine.utils.VolleyJsonRequest;
import icte.cph.aau.washingmachine.utils.VolleySingleTon;
import io.realm.Realm;
import io.realm.RealmResults;

public class WashingMachineActivity extends AppCompatActivity {
    RealmResults<RealmMyWashingMachine> resultArray;
    private MyWMAdapter adapter;
    private RecyclerView my_washing_machine_recyclerview;
    private ProgressBar my_washing_machine_progressbar;

    private static final String TAG = WashingMachineActivity.class.getSimpleName();

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing_machine);

        realm = Realm.getDefaultInstance();

        //Initiating progressBar
        my_washing_machine_progressbar = (ProgressBar) findViewById(R.id.my_washing_machine_progressbar);

        //Initiating RecyclerView
        my_washing_machine_recyclerview = (RecyclerView) findViewById(R.id.my_washing_machine_recyclerview);
        my_washing_machine_recyclerview.setLayoutManager(new LinearLayoutManager(WashingMachineActivity.this));
        my_washing_machine_recyclerview.setHasFixedSize(true); //For performance improvements

        //Initiating RecyclerView clickListener

        RealmResults<RealmMyWashingMachine> myWashingMachines = realm.where(RealmMyWashingMachine.class).findAll();
        if (!myWashingMachines.isEmpty()) {
            adapter = new MyWMAdapter(myWashingMachines, WashingMachineActivity.this);
            my_washing_machine_recyclerview.setAdapter(adapter);
            my_washing_machine_recyclerview.setVisibility(View.VISIBLE);
            my_washing_machine_progressbar.setVisibility(View.GONE);
            Log.d(TAG, "Empty: Not empty");
        }
        loadMyWashingMachineData();

        ItemClickSupport.addTo(my_washing_machine_recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                RealmMyWashingMachine wms = adapter.getWashingMachine(position);
                String id = wms.getWashningMachineID();
                String name = wms.getWashningMachineName();
                String brand = wms.getWashingMachineBrand();

                Intent intent = new Intent(WashingMachineActivity.this, InsertClothActivity.class);
                intent.putExtra(Constants.INTENT_WMS_ID, id);
                intent.putExtra(Constants.INTENT_WMS_NAME, name);
                intent.putExtra(Constants.INTENT_WMS_BRAND, brand);
                startActivity(intent);
            }
        });

    }

    private void loadMyWashingMachineData() {
        my_washing_machine_progressbar.setVisibility(View.VISIBLE);

        final SharedPreferencesHolder sp = new SharedPreferencesHolder(WashingMachineActivity.this);
        final String WID = sp.loadString(Constants.SP_WID);
        final String UID = sp.loadString(Constants.SP_UID);
        final String url = Constants.URL_MY_WM;

        Log.d(TAG, "loadMyWashingMachineData: URL: " + url);
        Log.d(TAG, "loadMyWashingMachineData: UID: " + UID);
        Log.d(TAG, "loadMyWashingMachineData: WID: " + WID);

        String UIDEncoded = null;
        try {
            UIDEncoded = URLEncoder.encode(UID, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "loadMyWashingMachineData: UIDEncoded: " + UIDEncoded);
        //Defining the POST parameters.
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TAG_UID, UIDEncoded);
        map.put(Constants.TAG_WID, WID);

        //Creating a Volley instance to do async. network operations.
        RequestQueue requestQueue = VolleySingleTon.getInstance().getRequestQueue();
        VolleyJsonRequest jsObjRequest = new VolleyJsonRequest(
                Request.Method.POST,
                url,
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

                                    final String wmbID = wObj.optString(Constants.TAG_WMB_ID);
                                    final String wmbName = wObj.optString(Constants.TAG_WMB_NAME);
                                    final String wmdBrand = wObj.optString(Constants.TAG_BRAND);

                                    //Inserting results from server into Realm Model.
                                    RealmMyWashingMachine wm = new RealmMyWashingMachine();
                                    wm.setWashningMachineID(wmbID);
                                    wm.setWashningMachineName(wmbName);
                                    wm.setWashingMachineBrand(wmdBrand);

                                    //Inserting the newly created Realm model into Realm database.
                                    realm.beginTransaction();
                                    realm.copyToRealmOrUpdate(wm);
                                    realm.commitTransaction();
                                }
                            } else
                                Toast.makeText(getApplicationContext(), "Error, could not find data", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse: message: " + message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RealmResults<RealmMyWashingMachine> myWashingMachines = realm.where(RealmMyWashingMachine.class).findAll();
                        adapter = new MyWMAdapter(myWashingMachines, WashingMachineActivity.this);
                        my_washing_machine_recyclerview.setAdapter(adapter);
                        my_washing_machine_recyclerview.setVisibility(View.VISIBLE);
                        my_washing_machine_progressbar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);

                        my_washing_machine_recyclerview.setVisibility(View.VISIBLE);
                        my_washing_machine_progressbar.setVisibility(View.GONE);

                    }
                }
        );

        //Starting network request
        requestQueue.add(jsObjRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_washing_machine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_item_register_wm:
                startActivity(new Intent(this, RegisterWashingMachineActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
