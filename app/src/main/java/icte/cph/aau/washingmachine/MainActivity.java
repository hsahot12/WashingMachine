package icte.cph.aau.washingmachine;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import icte.cph.aau.washingmachine.adapter.SetupWMViewPagerAdapter;
import icte.cph.aau.washingmachine.fragments.WashingMachineIDFragment;
import icte.cph.aau.washingmachine.fragments.WashingMachinePINFragment;
import icte.cph.aau.washingmachine.utils.Constants;
import icte.cph.aau.washingmachine.utils.SharedPreferencesHolder;

public class MainActivity extends AppCompatActivity implements WashingMachineIDFragment.IDPassListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout fragment_container;
    public ViewPager setup_wm_viewpager;
    private SetupWMViewPagerAdapter setupWMViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fragment_container = (LinearLayout) findViewById(R.id.fragment_container);

        //Inserting a Fragment programmatically.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WashingMachineIDFragment washingMachineIDFragment = new WashingMachineIDFragment();
        fragmentTransaction.add(R.id.fragment_container, washingMachineIDFragment, Constants.FRAGMENT_TAG_WASHINGMACHINE_ID);
        fragmentTransaction.commit();

        /*
        setupWMViewPagerAdapter = new SetupWMViewPagerAdapter(getSupportFragmentManager());
        setup_wm_viewpager = (ViewPager) findViewById(R.id.setup_wm_viewpager);
        setup_wm_viewpager.setAdapter(setupWMViewPagerAdapter);
        */
    }


    @Override
    public void onPinReceived(String pin, String WID) {
        SharedPreferencesHolder sp = new SharedPreferencesHolder(MainActivity.this);
        sp.saveString(Constants.SP_WID, WID);

        WashingMachinePINFragment washingMachinePINFragment = new WashingMachinePINFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_PIN, pin);
        bundle.putString(Constants.BUNDLE_WID, WID);
        washingMachinePINFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fragment_container, washingMachinePINFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}

