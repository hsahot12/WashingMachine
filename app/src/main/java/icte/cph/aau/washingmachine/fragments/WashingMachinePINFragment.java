package icte.cph.aau.washingmachine.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import icte.cph.aau.washingmachine.R;
import icte.cph.aau.washingmachine.WashingMachineActivity;
import icte.cph.aau.washingmachine.utils.Constants;
import me.philio.pinentry.PinEntryView;


public class WashingMachinePINFragment extends Fragment {
    private static final String TAG = WashingMachineIDFragment.class.getSimpleName();
    private PinEntryView setup_wm_pin_pin_entry;

    public WashingMachinePINFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_washing_machine_pin, container, false);
        setup_wm_pin_pin_entry = (PinEntryView) view.findViewById(R.id.setup_wm_pin_pin_entry);
        Button setup_wm_pin_button = (Button) view.findViewById(R.id.setup_wm_pin_button);
        setup_wm_pin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = setup_wm_pin_pin_entry.getText().toString();
                String pin = getArguments().getString(Constants.BUNDLE_PIN);

                //Checking if user has entered anything in the edittext
                if (!TextUtils.isEmpty(enteredPin)) {

                    //Checking if the entered PIN contains 4 digits. Else show appropiate error.
                    if (enteredPin.length() == 4) {
                        //Check if entered pin is equal to the PIN from database.

                        if (enteredPin.equals(pin)) {
                            //Get the WID associated with the PIN
                            String WID = getArguments().getString(Constants.BUNDLE_WID);

                            //Launching WashingMachineActivity.
                            Intent intent = new Intent(getActivity(), WashingMachineActivity.class);
                            intent.putExtra(WID, Constants.INTENT_WID);
                            startActivity(intent);

                        } else
                            Toast.makeText(getActivity(), "Incorrect PIN code", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "PIN code must contain 4 digits", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Please enter the 4 digit long PIN code", Toast.LENGTH_SHORT).show();


            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String pin = getArguments().getString(Constants.BUNDLE_PIN);
        Toast.makeText(getActivity(), pin, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onActivityCreated: pin: " + pin);

    }
}
