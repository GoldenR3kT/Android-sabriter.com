package edu.dg202433.sabriter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.classes.House;

public class HouseAdapter extends BaseAdapter {

    private List<House> houses;
    private LayoutInflater mInflater;

    private Context mContext;

    public HouseAdapter(List<House> houses, Context context) {
        this.houses = houses;
        mInflater = LayoutInflater.from(context);
        mContext = context;
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
        RatingBar ratingBar = layoutItem.findViewById(R.id.rank);
        TextView value = layoutItem.findViewById(R.id.valMaison);

        House house = houses.get(position);
        name.setText(house.getNom());
        picture.setImageResource(0);
        ratingBar.setRating(house.getMoyenneNote());

        value.setText(String.valueOf(house.getMoyenneNote()));

        ratingBar.setOnRatingBarChangeListener((ratingBar2, rating, fromUser) -> {
            house.setNoteFromRatingBarChange(rating);
            value.setText(String.valueOf(house.getMoyenneNote()));
        });



        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HouseActivity.class);
                intent.putExtra("id", house.getId());
                mContext.startActivity(intent);
            }
        });
        return layoutItem;
    }
}
