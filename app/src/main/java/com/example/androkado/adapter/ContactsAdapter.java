package com.example.androkado.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androkado.R;
import com.example.androkado.bo.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<Contact> listeContacts;
    private ContactsAdapter.OnClicSurUnItem<Contact> action;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tvContact;
        public TextView tvPhone;

        ViewHolder(View v)
        {
            super(v);
            tvContact = v.findViewById(R.id.tv_contact);
            tvPhone = v.findViewById(R.id.tv_phone);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            action.onInteraction(listeContacts.get(ContactsAdapter.ViewHolder.this.getAdapterPosition()));
        }
    }

    public interface OnClicSurUnItem<T> {
        void onInteraction(T article);
    }

    public ContactsAdapter(List<Contact> myDataset, ContactsAdapter.OnClicSurUnItem<Contact> activite)
    {
        listeContacts = (ArrayList<Contact>)myDataset;
        action = activite;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        return new ContactsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
        holder.tvContact.setText(listeContacts.get(position).getNom());
        holder.tvPhone.setText(listeContacts.get(position).getTelephone());
    }

    @Override
    public int getItemCount() {
        return listeContacts.size();
    }
}
