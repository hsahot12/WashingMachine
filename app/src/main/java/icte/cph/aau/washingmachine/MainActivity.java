package icte.cph.aau.washingmachine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import icte.cph.aau.washingmachine.adapter.SetupWMViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public ViewPager setup_wm_viewpager;
    private SetupWMViewPagerAdapter setupWMViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        setupWMViewPagerAdapter = new SetupWMViewPagerAdapter(getSupportFragmentManager());
        setup_wm_viewpager = (ViewPager) findViewById(R.id.setup_wm_viewpager);
        setup_wm_viewpager.setAdapter(setupWMViewPagerAdapter);
    }

}

