package icte.cph.aau.washingmachine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import icte.cph.aau.washingmachine.fragments.WashingMachineIDFragment;
import icte.cph.aau.washingmachine.fragments.WashingMachinePINFragment;
import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.IDPassListener;
import icte.cph.aau.washingmachine.utils.SharedPreferencesHolder;

public class RegisterWashingMachineActivity extends AppCompatActivity implements IDPassListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Preparing FragmentManager so it is possible to insert a Fragment dynamically.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Inserting the WashingMachingIDFragment dynamically
        WashingMachineIDFragment washingMachineIDFragment = new WashingMachineIDFragment();
        fragmentTransaction
                .add(R.id.fragment_container,
                        washingMachineIDFragment,
                        Constants.FRAGMENT_TAG_WASHINGMACHINE_ID);
        fragmentTransaction.commit();
    }

    @Override
    public void onPinReceived(String pin, String WID) {
        //Retrieving the WID from SharedPreferences
        SharedPreferencesHolder sp = new SharedPreferencesHolder(RegisterWashingMachineActivity.this);
        sp.saveString(Constants.SP_WID, WID);

        //Inserting WashingMachingPINFragment dynamically and adding a bundle object containing the WID and PIN code.
        WashingMachinePINFragment washingMachinePINFragment = new WashingMachinePINFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_PIN, pin);
        bundle.putString(Constants.BUNDLE_WID, WID);
        washingMachinePINFragment.setArguments(bundle);

        //Displaying the WashingMachinePINFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fragment_container, washingMachinePINFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}

