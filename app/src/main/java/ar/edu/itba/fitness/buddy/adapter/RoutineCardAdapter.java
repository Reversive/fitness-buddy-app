package ar.edu.itba.fitness.buddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.RoutineCardViewHolder> implements Filterable {

    private ArrayList<RoutineCard> routineCards;
    private ArrayList<RoutineCard> routineCardsAll;
    private OnRoutineCardListener mOnRoutineCardListener;

    public RoutineCardAdapter(ArrayList<RoutineCard> routineCards, OnRoutineCardListener onRoutineCardListener) {
        this.routineCards = routineCards == null ? new ArrayList<>() : routineCards;
        this.routineCardsAll = new ArrayList<>(this.routineCards);
        this.mOnRoutineCardListener = onRoutineCardListener;
    }

    @NonNull
    @Override
    public RoutineCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card_item, parent, false);
        return new RoutineCardViewHolder(view, this.mOnRoutineCardListener);
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<RoutineCard> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()) {
                filteredList = new ArrayList<>(routineCardsAll);
            } else {
                for(RoutineCard routineCard : routineCardsAll) {
                    if(routineCard.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(routineCard);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            routineCards.clear();
            routineCards.addAll((Collection<? extends RoutineCard>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class RoutineCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView title, category, difficulty;
        RatingBar rating;
        OnRoutineCardListener onRoutineCardListener;

        public RoutineCardViewHolder(@NonNull View itemView, OnRoutineCardListener onRoutineCardListener) {
            super(itemView);
            title = itemView.findViewById(R.id.default_card_title);
            category = itemView.findViewById(R.id.default_card_weight_field);
            difficulty = itemView.findViewById(R.id.default_card_difficulty_field);
            rating = itemView.findViewById(R.id.default_card_rating_bar);
            this.onRoutineCardListener = onRoutineCardListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRoutineCardListener.onRoutineCardClick(getBindingAdapterPosition());
        }
    }

    public interface OnRoutineCardListener {
        void onRoutineCardClick(int position);
    }
}
