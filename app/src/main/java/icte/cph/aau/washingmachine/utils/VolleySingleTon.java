package icte.cph.aau.washingmachine.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import icte.cph.aau.washingmachine.WashingMachineApplication;

public class VolleySingleTon {
    private static VolleySingleTon newInstance = null;
    private RequestQueue mRequestQueue;


    private VolleySingleTon() {
        mRequestQueue = Volley.newRequestQueue(WashingMachineApplication.getContext());

    }

    public static VolleySingleTon getInstance() {
        if (newInstance == null) {
            newInstance = new VolleySingleTon();
        }

        return newInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
