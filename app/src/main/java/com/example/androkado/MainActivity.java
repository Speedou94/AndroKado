package com.example.androkado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.androkado.bo.Article;
import com.example.androkado.dal.ArticleDao;

public class MainActivity extends AppCompatActivity {
    private Article article;
    TextView tvName;
    TextView tvPrice;
    TextView tvDescription;
    RatingBar rb;
    ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        article = intent.getParcelableExtra("article");
        //show article in view
        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvDescription = findViewById(R.id.tv_description);
        rb = findViewById(R.id.rb_note);
        tb = findViewById(R.id.tb_status);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArticleDao dao = new ArticleDao(this);
        article = dao.get(article.getId());

        tvName.setText(article.getNom());
        String prixAsString = article.getPrix() + " €";
        tvPrice.setText(prixAsString);
        tvDescription.setText(article.getDescription());
        rb.setRating(article.getNote());
        tb.setChecked(article.getEtat());
    }

    public void onSearchClick(View view) {
        //show a toast with the url of the article on button click
        Toast toast = Toast.makeText(this, article.getUrl(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

        //navigate to InfoUrlActivity
        Intent intent = new Intent(this, InfoUrlActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }

    public void onToggle(View view) {
        //set the article state depending on the value of the toggle button
        boolean state = tb.isChecked();
        article.setEtat(state);
        //Log.v("ArticleState", article.getEtat().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_edit:
                //Toast.makeText(this, "Modifier", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, FormulaireActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
                return true;

            case R.id.action_send:
                //Toast.makeText(this, "Envoyer", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListeContactsActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}