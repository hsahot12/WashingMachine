package icte.cph.aau.washingmachine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import icte.cph.aau.washingmachine.fragments.WashingMachineIDFragment;
import icte.cph.aau.washingmachine.fragments.WashingMachinePINFragment;


public class SetupWMViewPagerAdapter extends FragmentPagerAdapter {
    //Defining a list of Fragments, which later are added in the Constructor.
    private List<Fragment> fragments = new ArrayList<>();

    public SetupWMViewPagerAdapter(FragmentManager fm) {
        super(fm);

        //Adding Fragments to the list in order to display them in the ViewPager.
        fragments.add(new WashingMachineIDFragment());
        fragments.add(new WashingMachinePINFragment());
    }

    @Override
    public Fragment getItem(int position) {
        //Returns a Fragment according to their position in the ViewPager.
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        //Returning the size of the list - how many fragments are there in total.
        return fragments.size();
    }
}

