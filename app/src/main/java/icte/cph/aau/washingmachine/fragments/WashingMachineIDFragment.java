package icte.cph.aau.washingmachine.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import icte.cph.aau.washingmachine.R;
import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.HideKeyboard;
import icte.cph.aau.washingmachine.utils.VolleyJsonRequest;
import icte.cph.aau.washingmachine.utils.VolleySingleTon;

/**
 * A simple {@link Fragment} subclass.
 */
public class WashingMachineIDFragment extends Fragment {
    private static final String TAG = WashingMachineIDFragment.class.getSimpleName();

    private LinearLayout setup_wm_holder;
    private ProgressBar setup_wm_progressBar;
    private EditText setup_wm_id_edittext;
    private Button setup_wm_next_button;

    IDPassListener idPassListener;

    public WashingMachineIDFragment() {
    }

    public interface IDPassListener {
        void onPinReceived(String pin, String WID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_washing_machine_id, container, false);
        setup_wm_holder = (LinearLayout) view.findViewById(R.id.setup_wm_holder);
        setup_wm_next_button = (Button) view.findViewById(R.id.setup_wm_next_button);
        setup_wm_id_edittext = (EditText) view.findViewById(R.id.setup_wm_id_edittext);
        setup_wm_progressBar = (ProgressBar) view.findViewById(R.id.setup_wm_progressBar);
        return view;
    }


    private void sendWMID() {
        new HideKeyboard(getActivity()).hide(setup_wm_holder);
        setup_wm_progressBar.setVisibility(View.VISIBLE);
        setup_wm_holder.setVisibility(View.GONE);

        //Inserting POST parameters in a Map object.
        final String wid = setup_wm_id_edittext.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TAG_WID, wid);

        //Initiating the Requuest queue with Volley
        RequestQueue requestQueue = VolleySingleTon.getInstance().getRequestQueue();
        VolleyJsonRequest jsObjRequest = new VolleyJsonRequest(
                Request.Method.POST,
                Constants.URL_WM_PIN,
                map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setup_wm_progressBar.setVisibility(View.GONE);
                        setup_wm_holder.setVisibility(View.VISIBLE);

                        try {
                            int success = response.getInt(Constants.TAG_SUCCESS);
                            String message = response.optString(Constants.TAG_MESSAGE);

                            if (success == 1) {
                                JSONObject resultObj = response.getJSONObject(Constants.TAG_RESULT);
                                String pin = resultObj.optString(Constants.TAG_PIN);

                                idPassListener.onPinReceived(pin, wid);
                                Log.d(TAG, "onResponse: pin code: " + pin + " WID: " + wid);

                            } else
                                Toast.makeText(getActivity(), "Couldn't not find Washing Machine", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "onResponse: message: " + message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setup_wm_progressBar.setVisibility(View.GONE);
                        setup_wm_holder.setVisibility(View.VISIBLE);

                        Log.d(TAG, "onErrorResponse: " + error);

                    }
                }
        );

        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setup_wm_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWMID();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            idPassListener = (IDPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }


    }
}
