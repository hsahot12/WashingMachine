package icte.cph.aau.washingmachine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import icte.cph.aau.washingmachine.R;
import icte.cph.aau.washingmachine.RealmModel.RealmMyWashingMachine;
import io.realm.RealmResults;


public class MyWMAdapter extends RecyclerView.Adapter<MyWMAdapter.MyViewHolder> {
    private RealmResults<RealmMyWashingMachine> resultArray;
    private Context context;

    public MyWMAdapter(RealmResults<RealmMyWashingMachine> resultArray, Context context) {
        this.resultArray = resultArray;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.single_item_my_washing_machine, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        TextView brandTv = vh.single_item_wm_brand;
        TextView modelNameTv = vh.single_item_wm_model_name;

        RealmMyWashingMachine mReamls = resultArray.get(position);

        String modelName = mReamls.getWashningMachineName();
        String brand = mReamls.getWashingMachineBrand();

        brandTv.setText(brand);
        modelNameTv.setText(modelName);
    }

    public StringBuilder getWashingMachineData(int position) {
        StringBuilder builder = new StringBuilder();

        RealmMyWashingMachine realm = resultArray.get(position);
        String id = realm.getWashningMachineID();
        String name = realm.getWashningMachineName();
        String brand = realm.getWashingMachineBrand();

        builder.append(id);
        builder.append(name);
        builder.append(brand);

        return builder;

    }

    @Override
    public int getItemCount() {
        return resultArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView single_item_wm_brand, single_item_wm_model_name;

        public MyViewHolder(View v) {
            super(v);
            single_item_wm_brand = (TextView) v.findViewById(R.id.single_item_wm_brand);
            single_item_wm_model_name = (TextView) v.findViewById(R.id.single_item_wm_model_name);
        }
    }
}
