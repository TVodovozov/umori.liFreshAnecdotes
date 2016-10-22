package com.vodovozov.umorilifreshanecdotes.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vodovozov.umorilifreshanecdotes.R;
import com.vodovozov.umorilifreshanecdotes.dataBase.entityes.AllJokesEntity;

import java.util.ArrayList;
import java.util.List;

public class AdapterAllJokes extends RecyclerView.Adapter<AdapterAllJokes.AllJokesHolder> {

    private Context context;
    private List<AllJokesEntity> allJokesList;

    public AdapterAllJokes(Context context) {
        this.context = context;
        allJokesList = new ArrayList<>();
    }

    public void setAllJokesList(List<AllJokesEntity> allJokesList) {
        this.allJokesList = allJokesList;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return allJokesList.size();
    }

    @Override
    public AllJokesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.all_jokes_item, parent, false);

        return new AllJokesHolder(itemView);
    }

    public AllJokesEntity getItemById(int itemId) {
        return allJokesList.get(itemId);
    }

    @Override
    public void onBindViewHolder(final AllJokesHolder holder, final int position) {
        final AllJokesEntity allJokes = allJokesList.get(position);
        holder
                .text
                .setText(Html.fromHtml(allJokes.getText()));
        if (allJokes.isFavorites()) {
            Glide
                    .with(context)
                    .load(R.drawable.ic_star_check_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.favorite);
            holder
                    .favorite
                    .setImageResource(R.drawable.ic_star_uncheck_24dp);
        } else {
            Glide
                    .with(context)
                    .load(R.drawable.ic_star_chek)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.favorite);
        }
        holder
                .favorite
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllJokesEntity allJokesEntity = allJokesList.get(position);

                        if (allJokesEntity.isFavorites()) {
                            allJokesEntity.setFavorites(false);
                            Glide
                                    .with(context)
                                    .load(R.drawable.ic_star_uncheck_24dp)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(holder.favorite);

                        } else {
                            allJokesEntity.setFavorites(true);
                            Glide
                                    .with(context)
                                    .load(R.drawable.ic_star_check_24dp)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(holder.favorite);
                        }
                        allJokesEntity.save();
                    }
                });
    }

    public class AllJokesHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView favorite;

        public AllJokesHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.all_jokes_item_jokes_text);
            favorite = (ImageView) itemView.findViewById(R.id.favorite_image);
        }
    }
}

