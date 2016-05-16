package icte.cph.aau.washingmachine.adapter;/*
Default RecyclerView with a ViewHolder
*/

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import icte.cph.aau.washingmachine.R;
import icte.cph.aau.washingmachine.utils.Constants;

public class InsertClothesAdapter extends RecyclerView.Adapter<InsertClothesAdapter.MyViewHolder> {
    private static final String TAG = InsertClothesAdapter.class.getSimpleName();
    private Context context;
    public ArrayList<HashMap<String, String>> resultArray = new ArrayList<>();

    ArrayList<String> clothesList = new ArrayList<>();
    ArrayList<String> clothesShadeList = new ArrayList<>();
    ArrayList<String> removeClothesList = new ArrayList<>();

    public InsertClothesAdapter(Context context, ArrayList<HashMap<String, String>> resultArray) {
        this.context = context;
        this.resultArray = resultArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item_insert_clothes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        TextView brandTv = vh.single_item_insert_clothes_brand;
        TextView colorTv = vh.single_item_insert_clothes_color;
        TextView shadeTv = vh.single_item_insert_clothes_shade;
        TextView temperatureTv = vh.single_item_insert_clothes_temperature;
        TextView materialTv = vh.single_item_insert_clothes_material;

        HashMap<String, String> map = resultArray.get(position);
        String brand = map.get(Constants.TAG_BRAND);
        String color = map.get(Constants.TAG_COLOR);
        String shade = map.get(Constants.TAG_SHADE);
        String temperature = map.get(Constants.TAG_TEMPERATURE);
        String material = map.get(Constants.TAG_MATERIAL);

        brandTv.setText(brand);
        colorTv.setText(color);
        shadeTv.setText(shade);
        temperatureTv.setText(temperature);
        materialTv.setText(material);


    }



    public ArrayList<String> getShadeClothes() {

        for (int i = 0; i < resultArray.size(); i++) {

            //Get the shade for each piece of clothing
            HashMap<String, String> map = resultArray.get(i);
            String shade = map.get(Constants.TAG_SHADE);
            String brand = map.get(Constants.TAG_BRAND);

            clothesShadeList.add(shade);
            clothesList.add(brand);


            if (!clothesShadeList.isEmpty() && clothesShadeList.size() > 0) {
                if (clothesShadeList.contains(Constants.SHADE_WHITE) && !shade.equals(Constants.SHADE_WHITE)) {
                    removeClothesList.add(brand);

                } else if (clothesShadeList.contains(Constants.SHADE_LIGHT) && shade.equals(Constants.SHADE_DARK)) {
                    removeClothesList.add(brand);

                }
            }
        }


        return removeClothesList;
    }

    @Override
    public int getItemCount() {
        return resultArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView
                single_item_insert_clothes_brand,
                single_item_insert_clothes_temperature,
                single_item_insert_clothes_color,
                single_item_insert_clothes_shade,
                single_item_insert_clothes_material;

        public MyViewHolder(View v) {
            super(v);
            single_item_insert_clothes_brand = (TextView) v.findViewById(R.id.single_item_insert_clothes_brand);
            single_item_insert_clothes_temperature = (TextView) v.findViewById(R.id.single_item_insert_clothes_temperature);
            single_item_insert_clothes_color = (TextView) v.findViewById(R.id.single_item_insert_clothes_color);
            single_item_insert_clothes_shade = (TextView) v.findViewById(R.id.single_item_insert_clothes_shade);
            single_item_insert_clothes_material = (TextView) v.findViewById(R.id.single_item_insert_clothes_material);
        }
    }
}
