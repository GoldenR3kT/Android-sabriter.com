package edu.dg202433.sabriter.classes;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.HouseActivity;

/**
 * La classe HouseAdapter est un adaptateur personnalisé utilisé pour afficher une liste de maisons dans une vue ListView ou GridView.
 * Elle gère l'affichage des éléments de la liste et les interactions utilisateur avec ces éléments.
 */
public class HouseAdapter extends BaseAdapter {

    private List<House> houses; // Liste des maisons à afficher
    private LayoutInflater mInflater; // Utilisé pour inflater le layout de chaque élément de la liste
    private Context mContext; // Contexte de l'application
    private List<House> modifiedHouses; // Liste des maisons modifiées par l'utilisateur

    /**
     * Constructeur de la classe HouseAdapter.
     *
     * @param houses Liste des maisons à afficher dans l'adaptateur.
     * @param context Contexte de l'application.
     * @param modifiedHouses Liste des maisons modifiées par l'utilisateur.
     */
    public HouseAdapter(List<House> houses, Context context, List<House> modifiedHouses) {
        this.houses = houses;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.modifiedHouses = modifiedHouses;
    }

    /**
     * Retourne le nombre d'éléments dans la liste.
     *
     * @return Nombre d'éléments dans la liste.
     */
    public int getCount() {
        return houses.size();
    }

    /**
     * Retourne l'élément à la position spécifiée dans la liste.
     *
     * @param position Position de l'élément dans la liste.
     * @return L'élément à la position spécifiée.
     */
    public Object getItem(int position) {
        return houses.get(position);
    }

    /**
     * Retourne l'identifiant de l'élément à la position spécifiée dans la liste.
     *
     * @param position Position de l'élément dans la liste.
     * @return L'identifiant de l'élément à la position spécifiée.
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Méthode appelée pour créer et afficher la vue d'un élément de la liste.
     *
     * @param position Position de l'élément dans la liste.
     * @param convertView La vue réutilisée pour afficher l'élément, si disponible.
     * @param parent Le parent de la vue.
     * @return La vue d'un élément de la liste.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;
        layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.activity_one_house, parent, false);

        TextView name = layoutItem.findViewById(R.id.name); // TextView pour afficher le nom de la maison
        ImageView picture = layoutItem.findViewById(R.id.picture); // ImageView pour afficher l'image de la maison
        RatingBar ratingBar = layoutItem.findViewById(R.id.rank); // RatingBar pour afficher la note moyenne de la maison
        TextView value = layoutItem.findViewById(R.id.valMaison); // TextView pour afficher la valeur de la maison

        House house = houses.get(position); // Récupération de la maison à cette position dans la liste

        // Affichage des données de la maison dans les éléments de la vue
        name.setText(house.getNom());
        ratingBar.setRating(house.getMoyenneNote());
        value.setText(String.valueOf(house.getMoyenneNote()));

        if (house.hasVoted()) {
            ratingBar.setRating(house.getMoyenneNote());
            ratingBar.setIsIndicator(true);
        } else {
            ratingBar.setRating(house.getMoyenneNote());
            ratingBar.setIsIndicator(false);
        }


        // Gestion de l'événement de changement de note de la maison
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (!house.hasVoted()) {
                    house.setNoteFromRatingBarChange(rating);
                    house.setHasVoted(true); // Marquer que l'utilisateur a voté pour cette maison
                    modifiedHouses.add(house); // Ajouter la maison modifiée à la liste
                    notifyDataSetChanged(); // Notifier l'adaptateur pour mettre à jour la vue
                    // Affichage d'un message de remerciement
                    Toast.makeText(mContext, "Merci pour votre note !", Toast.LENGTH_SHORT).show();
                } else {
                    // Affichage d'un message indiquant que l'utilisateur a déjà voté
                    Toast.makeText(mContext, "Vous avez déjà voté pour cette maison.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Chargement de l'image de la maison à partir de l'URL et affichage dans l'ImageView
        String imageUrl = house.getCompleteImageLinks()[0];
        Picasso.get().load(imageUrl).into(picture);

        // Gestion du clic sur un élément de la liste pour afficher les détails de la maison
        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HouseActivity.class);
                intent.putExtra("selectedHouse", house); // Passage de la maison sélectionnée à l'activité HouseActivity
                mContext.startActivity(intent); // Démarrage de l'activité HouseActivity
            }
        });

        return layoutItem; // Retour de la vue d'un élément de la liste
    }
}

