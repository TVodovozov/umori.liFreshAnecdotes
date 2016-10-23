package com.vodovozov.umorilifreshanecdotes.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vodovozov.umorilifreshanecdotes.R;
import com.vodovozov.umorilifreshanecdotes.dataBase.entityes.AllJokesEntity;

import java.util.List;

public class AdapterFavorites extends RecyclerView.Adapter<AdapterFavorites.FavoriteHolder> {


    private List<AllJokesEntity> favoriteList;


    public AdapterFavorites(List<AllJokesEntity> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.favorites_item, parent, false);
        return new FavoriteHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final FavoriteHolder holder, final int position) {
        final AllJokesEntity favorite = favoriteList.get(position);
        holder.favoritesText.setText(Html.fromHtml(favorite.getText()));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {

        TextView favoritesText;

        public FavoriteHolder(View itemView) {
            super(itemView);
            favoritesText = (TextView) itemView.findViewById(R.id.favorite_item_text);
        }
    }
}

