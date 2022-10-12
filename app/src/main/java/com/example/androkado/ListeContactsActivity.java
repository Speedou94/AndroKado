package com.example.androkado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.androkado.adapter.ContactsAdapter;
import com.example.androkado.bo.Article;
import com.example.androkado.bo.Contact;

import java.util.ArrayList;
import java.util.List;

public class ListeContactsActivity extends AppCompatActivity implements ContactsAdapter.OnClicSurUnItem<Contact> {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Article article;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_article);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_contacts);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},14540);

        Intent intent = getIntent();
        article = intent.getParcelableExtra("article");
    }

    @Override
    public void onInteraction(Contact contact) {
        this.contact = contact;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},14550);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 14540: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    bindContactsList();
                }
            }
            break;

            case 14550: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    sendSms(contact.getTelephone(), article.getNom());
                }
            }
            break;
        }
    }

    private void sendSms(String phoneNumber, String articleName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+ phoneNumber));
        intent.putExtra("sms_body", "Voici un cadeau qui me ferait plaisir : "+ articleName);
        startActivity(intent);
    }

    private void bindContactsList() {
        List<Contact> contactsList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor contacts = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);

        while(contacts.moveToNext())
        {
            String id = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
            String name = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Contact friend = new Contact();
            friend.setId(Integer.parseInt(id));
            friend.setNom(name);

            if ("1".equals(hasPhone)){
                Cursor phones = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[] {id},
                        null);
                if (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    friend.setTelephone(phoneNumber);
                }
                phones.close();
            }
            contactsList.add(friend);
        }
        contacts.close();
        mAdapter = new ContactsAdapter(contactsList, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}