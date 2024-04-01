package edu.dg202433.sabriter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    private List<House> modifiedHouses;

    public HouseAdapter(List<House> houses, Context context, List<House> modifiedHouses) {
        this.houses = houses;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.modifiedHouses = modifiedHouses;

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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                house.setNoteFromRatingBarChange(rating);
                modifiedHouses.add(house);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Merci pour votre note :)");

                builder.setTitle("Note attribuée");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        notifyDataSetChanged();                    }
                });

                AlertDialog alertDialog = builder.create();


                alertDialog.show();
                notifyDataSetChanged();
            }
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
