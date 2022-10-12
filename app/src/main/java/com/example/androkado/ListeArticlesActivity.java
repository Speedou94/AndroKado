package com.example.androkado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androkado.adapter.ArticlesAdapter;
import com.example.androkado.bo.Article;
import com.example.androkado.dal.ArticleDao;

public class ListeArticlesActivity extends AppCompatActivity implements ArticlesAdapter.OnClicSurUnItem<Article> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_articles);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_articles);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        Article painChoc = new Article("Pain au chocolat", 2.15, "Une viennoiserie au beurre salé et au chocolat", 3.5f, "http://painauchocolat.fr", true);
//        Article croissant = new Article("Croissant", 1.45, "Une viennoiserie au beurre salé", 4.0f, "http://croissant.fr", false);
//        Article pommier = new Article("Gateau aux pommes", 3.10, "Un gateaux au beurre salé et pommes caramelisées", 4.5f, "http://pommier.fr", true);
//        Article fraisier = new Article("Gateau aux fraises", 2.50, "Un gateau fruité au fraises sucrées et crème vanille", 4.5f, "http://fraisier.fr", true);
//        Article framboisier = new Article("Gateau aux framboises", 2.30, "Une tarte au framboises délicieuses et crème vanille", 5.0f, "http://framboisier.fr", false);

        //initialize action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArticleDao dao = new ArticleDao(this);

        //retrieve the config from filters
        SharedPreferences sp = getSharedPreferences(ConfigurationActivity.CONF_FILE, MODE_PRIVATE);
        boolean isTrie = sp.getBoolean(ConfigurationActivity.CLE_TRI, false);

        //connect the article with the recycler view
        mAdapter = new ArticlesAdapter(dao.get(isTrie), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onInteraction(Article article) {
        Toast.makeText(this, article.getNom(), Toast.LENGTH_SHORT).show();
        //navigate to InfoUrlActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Toast.makeText(this, "Configuration", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ConfigurationActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_add:
                //Toast.makeText(this, "Ajouter article", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, FormulaireActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}