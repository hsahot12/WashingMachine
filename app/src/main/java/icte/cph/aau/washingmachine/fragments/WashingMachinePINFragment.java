package icte.cph.aau.washingmachine.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import icte.cph.aau.washingmachine.R;
import me.philio.pinentry.PinEntryView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WashingMachinePINFragment extends Fragment {
    private PinEntryView pinEntryView;


    public WashingMachinePINFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_washing_machine_pin, container, false);

        pinEntryView = (PinEntryView) view.findViewById(R.id.pin_entry_simple);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
