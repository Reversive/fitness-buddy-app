package ar.edu.itba.fitness.buddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.helper.RoutineCard;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.RoutineCardViewHolder> {

    ArrayList<RoutineCard> routineCards;

    public RoutineCardAdapter(ArrayList<RoutineCard> routineCards) {
        this.routineCards = routineCards;
    }

    @NonNull
    @Override
    public RoutineCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card_item, parent, false);
        return new RoutineCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineCardAdapter.RoutineCardViewHolder holder, int position) {
        RoutineCard routineCard = routineCards.get(position);

        holder.title.setText(routineCard.getTitle());
        holder.category.setText(routineCard.getCategory());
        holder.difficulty.setText(routineCard.getDifficulty());
        holder.rating.setRating(routineCard.getRating());
    }

    @Override
    public int getItemCount() {
        return routineCards.size();
    }

    public static class RoutineCardViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title, category, difficulty;
        RatingBar rating;

        public RoutineCardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.default_card_title);
            category = itemView.findViewById(R.id.default_card_weight_field);
            difficulty = itemView.findViewById(R.id.default_card_difficulty_field);
            rating = itemView.findViewById(R.id.default_card_rating_bar);
        }
    }
}
