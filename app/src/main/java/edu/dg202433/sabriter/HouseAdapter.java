package edu.dg202433.sabriter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.classes.House;

public class HouseAdapter extends BaseAdapter {

    private List<House> houses;
    private LayoutInflater mInflater;

    public HouseAdapter(List<House> houses, Context context) {
        this.houses = houses;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return houses.size();
    }

    public Object getItem(int position) {
        return houses.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;
        layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.activity_one_house, parent, false);

        TextView name = layoutItem.findViewById(R.id.name);
        ImageView picture = layoutItem.findViewById(R.id.picture);

        House house = houses.get(position);
        name.setText(house.getNom());
        picture.setImageResource(house.getFirstImage());

        return layoutItem;
    }







}
