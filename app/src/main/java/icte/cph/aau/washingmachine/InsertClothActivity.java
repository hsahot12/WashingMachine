package icte.cph.aau.washingmachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class InsertClothActivity extends AppCompatActivity {
    private RecyclerView insert_clothes_recyclerview;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cloth);

        insert_clothes_recyclerview = (RecyclerView) findViewById(R.id.insert_clothes_recyclerview);
    }

    private void loadClothes() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        LocalBroadcastManager.getInstance(InsertClothActivity.this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(InsertClothActivity.this).unregisterReceiver(broadcastReceiver);
    }
}
