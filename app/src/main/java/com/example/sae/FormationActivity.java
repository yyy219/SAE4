package com.example.sae;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.sae.fragment.FormationFragment;
import com.google.android.material.navigation.NavigationView;

public class FormationActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        TextView btnMenu = findViewById(R.id.btn_menu);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // 1. Ouvrir le menu au clic
        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // 2. Gérer les clics dans le menu
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_formations) {
                // On est déjà dessus, on ne fait rien
            } else if (id == R.id.nav_profil) {
                Toast.makeText(this, "Écran Profil à coder plus tard !", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_progression) {
                Toast.makeText(this, "Écran Progression à coder plus tard !", Toast.LENGTH_SHORT).show();
            }
            // On referme le menu après le clic
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // 3. Charger le fragment par défaut
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FormationFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_formations);
        }
    }
}