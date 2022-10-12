package com.example.androkado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConfigurationActivity extends AppCompatActivity {
    public static final String CONF_FILE = "conf";
    public static final String CLE_PRIX = "prix";
    public static final String CLE_TRI = "tri";

    private SwitchCompat swPrice;
    private EditText etPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        swPrice = findViewById(R.id.sw_price);
        etPrice = findViewById(R.id.et_price);

        SharedPreferences sp = getSharedPreferences(CONF_FILE,MODE_PRIVATE);
        etPrice.setText(sp.getString(CLE_PRIX, ""));
        swPrice.setChecked(sp.getBoolean(CLE_TRI, false));
    }

    public void applyFilters(View view) {
        SharedPreferences sp = getSharedPreferences(CONF_FILE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(CLE_TRI, swPrice.isChecked());
        editor.putString(CLE_PRIX, etPrice.getText().toString());
        editor.apply();
    }
}