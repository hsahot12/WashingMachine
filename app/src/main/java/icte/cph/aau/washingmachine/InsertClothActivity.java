package icte.cph.aau.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import icte.cph.aau.washingmachine.adapter.InsertClothesAdapter;
import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.VolleyJsonRequest;
import icte.cph.aau.washingmachine.utils.VolleySingleTon;

public class InsertClothActivity extends AppCompatActivity {
    private static final String TAG = InsertClothActivity.class.getSimpleName();
    private RecyclerView insert_clothes_recyclerview;
    private ListView insert_clothes_listview;
    private Button insert_clothes_button, insert_clothes_washing_program_button;
    private ProgressBar insert_clothes_progressBar;

    private TextView insert_clothes_clothes_brand, insert_clothes_clothes_name;

    private ArrayList<HashMap<String, String>> resultArrayList;

    private InsertClothesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cloth);

        insert_clothes_progressBar = (ProgressBar) findViewById(R.id.insert_clothes_progressBar);
        insert_clothes_button = (Button) findViewById(R.id.insert_clothes_button);
        insert_clothes_washing_program_button = (Button) findViewById(R.id.insert_clothes_washing_program_button);

        insert_clothes_listview = (ListView) findViewById(R.id.insert_clothes_listview);

        insert_clothes_recyclerview = (RecyclerView) findViewById(R.id.insert_clothes_recyclerview);
        insert_clothes_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        insert_clothes_recyclerview.setHasFixedSize(true);

        Intent intent = getIntent();
        final String WID = intent.getStringExtra(Constants.INTENT_WMS_ID);
        final String wmsName = intent.getStringExtra(Constants.INTENT_WMS_NAME);
        final String wmsBrand = intent.getStringExtra(Constants.INTENT_WMS_BRAND);

        insert_clothes_clothes_name = (TextView) findViewById(R.id.insert_clothes_clothes_name);
        insert_clothes_clothes_brand = (TextView) findViewById(R.id.insert_clothes_clothes_brand);

        insert_clothes_clothes_name.setText(wmsName);
        insert_clothes_clothes_brand.setText(wmsBrand);


        insert_clothes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_clothes_listview.setVisibility(View.GONE);
                insert_clothes_recyclerview.setVisibility(View.VISIBLE);

                loadClothes(WID);

            }
        });

        insert_clothes_washing_program_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkColor();
            }
        });
    }

    private void loadClothes(final String WID) {
        Log.d(TAG, "loadClothes: WID: " + WID);
        String url = Constants.URL_GET_CLOTHES;
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TAG_WID, WID);

        resultArrayList = new ArrayList<>();

        insert_clothes_recyclerview.setVisibility(View.GONE);

        RequestQueue requestQueue = VolleySingleTon.getInstance().getRequestQueue();
        VolleyJsonRequest json = new VolleyJsonRequest(
                Request.Method.POST,
                url,
                map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int success = response.optInt(Constants.TAG_SUCCESS);
                        String message = response.optString(Constants.TAG_MESSAGE);
                        Log.d(TAG, "loadClothes: " + message);

                        if (success == 1) {
                            try {
                                JSONArray resultArray = response.getJSONArray(Constants.TAG_CLOTHES);

                                for (int i = 0; i < resultArray.length(); i++) {
                                    String color = resultArray.getJSONObject(i).optString(Constants.TAG_COLOR);
                                    String shade = resultArray.getJSONObject(i).optString(Constants.TAG_SHADE);
                                    String material = resultArray.getJSONObject(i).optString(Constants.TAG_MATERIAL);
                                    String temperature = resultArray.getJSONObject(i).optString(Constants.TAG_TEMPERATURE);
                                    String brand = resultArray.getJSONObject(i).optString(Constants.TAG_BRAND);

                                    HashMap<String, String> map = new HashMap<>();
                                    map.put(Constants.TAG_COLOR, color);
                                    map.put(Constants.TAG_SHADE, shade);
                                    map.put(Constants.TAG_MATERIAL, material);
                                    map.put(Constants.TAG_TEMPERATURE, temperature);
                                    map.put(Constants.TAG_BRAND, brand);

                                    resultArrayList.add(map);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter = new InsertClothesAdapter(InsertClothActivity.this, resultArrayList);
                        insert_clothes_recyclerview.setAdapter(adapter);
                        insert_clothes_recyclerview.setVisibility(View.VISIBLE);
                        insert_clothes_progressBar.setVisibility(View.GONE);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        insert_clothes_recyclerview.setVisibility(View.VISIBLE);
                        insert_clothes_progressBar.setVisibility(View.GONE);

                    }
                }
        );
        requestQueue.add(json);

    }

    private void checkColor() {
        if (adapter != null) {
            ArrayList<String> errorList = adapter.getShadeClothes();

            ListAdapter adapter = new ArrayAdapter<>(InsertClothActivity.this, android.R.layout.simple_list_item_1, errorList);
            insert_clothes_listview.setAdapter(adapter);
            insert_clothes_listview.setVisibility(View.VISIBLE);
            insert_clothes_recyclerview.setVisibility(View.GONE);

            if (errorList.size() > 0) {
                for (int i = 0; i < errorList.size(); i++) {
                    Log.d(TAG, "checkColor: errorList: " + errorList.get(i));
                }
            }


        } else
            Log.d(TAG, "checkColor: adapter null :(");
    }

}
