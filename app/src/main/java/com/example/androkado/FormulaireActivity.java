package com.example.androkado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ToggleButton;

import com.example.androkado.bo.Article;
import com.example.androkado.dal.ArticleDao;

public class FormulaireActivity extends AppCompatActivity {

    private EditText etNom;
    private EditText etPrix;
    private EditText etDescription;
    private EditText etUrl;
    private RatingBar rbNote;
    private ToggleButton tbEtat;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_create);
        setSupportActionBar(myToolbar);

        etNom = findViewById(R.id.et_nom);
        etPrix = findViewById(R.id.et_price);
        etDescription = findViewById(R.id.et_description);
        etUrl = findViewById(R.id.et_url);
        rbNote = findViewById(R.id.rb_note);
        tbEtat = findViewById(R.id.tb_etat);

        Intent intent = getIntent();
        article = intent.getParcelableExtra("article");
        if (article != null){
            getSupportActionBar().setTitle("Modifier article");
            etNom.setText(article.getNom());
            etPrix.setText(String.valueOf(article.getPrix()));
            etDescription.setText(article.getDescription());
            etUrl.setText(article.getUrl());
            rbNote.setRating(article.getNote());
            tbEtat.setChecked(article.getEtat());
        }else{
            getSupportActionBar().setTitle("Créer un article");
            article = new Article();
            //configurer le prix par défaut à partir des filtres
            SharedPreferences sp = getSharedPreferences(ConfigurationActivity.CONF_FILE, MODE_PRIVATE);
            String prix = sp.getString(ConfigurationActivity.CLE_PRIX, "");
            etPrix.setText(prix);
        }

    }

    public void onSaveClick(View view) {
        article.setNom(etNom.getText().toString());
        article.setPrix(Double.parseDouble(etPrix.getText().toString()));
        article.setDescription(etDescription.getText().toString());
        article.setNote(rbNote.getRating());
        article.setUrl(etUrl.getText().toString());
        article.setEtat(tbEtat.isChecked());

        ArticleDao dao = new ArticleDao(this);
        if (article.getId() == 0){
            getSupportActionBar().setTitle("Créer un article");
            dao.insert(article);
        }else{
            getSupportActionBar().setTitle("Modifier article");
            dao.update(article);
        }
        finish();
    }


}