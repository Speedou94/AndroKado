package com.example.androkado.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androkado.R;
import com.example.androkado.bo.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>
{
    private ArrayList<Article> listeArticles;
    private OnClicSurUnItem<Article> action;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView cardView;
        public RatingBar rbNote;

        ViewHolder(View v)
        {
            super(v);
            rbNote = v.findViewById(R.id.rb_note_cardview);
            cardView = v.findViewById(R.id.tv_article_cardview);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            action.onInteraction(listeArticles.get(ViewHolder.this.getAdapterPosition()));
        }
    }

    public interface OnClicSurUnItem<T> {
        void onInteraction(T article);
    }

    public ArticlesAdapter(List<Article> myDataset, OnClicSurUnItem<Article> activite)
    {
        listeArticles = (ArrayList<Article>)myDataset;
        action = activite;
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cards, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setText(listeArticles.get(position).getNom());
        holder.rbNote.setRating(listeArticles.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return listeArticles.size();
    }
}
