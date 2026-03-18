package com.example.sae.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.sae.Formation;
import com.example.sae.FormationAdapter;
import com.example.sae.R;
import com.example.sae.database.FormationRepository;

// 1. Importer AppDatabase et les entités (Ajuste les noms de packages selon ceux de ton ami)
// import com.example.sae.database.AppDatabase;
// import com.example.sae.database.FormationRepository;

import java.util.ArrayList;
import java.util.List;

public class FormationFragment extends Fragment {

    private FormationAdapter adapter;
    private List<Formation> formationList;
    private List<Formation> formationListFull;

    public FormationFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_formations, container, false);

        ListView lvFormations = rootView.findViewById(R.id.lv_habitats_frag);
        EditText etRecherche = rootView.findViewById(R.id.et_recherche);

        formationList = new ArrayList<>();
        formationListFull = new ArrayList<>();
        adapter = new FormationAdapter(getActivity(), R.layout.item_formation, formationList);
        lvFormations.setAdapter(adapter);

        // NOUVEAU : On appelle la base de données au lieu des données en dur
        chargerFormationsDepuisBDD();

        etRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrerFormations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        lvFormations.setOnItemClickListener((parent, view, position, id) -> {
            Formation selected = formationList.get(position);
            Toast.makeText(getContext(), "Ouverture : " + selected.getTitre(), Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }

    private void filtrerFormations(String texteRecherche) {
        List<Formation> listeFiltree = new ArrayList<>();
        for (Formation f : formationListFull) {
            if (f.getTitre().toLowerCase().contains(texteRecherche.toLowerCase()) ||
                    f.getThematique().toLowerCase().contains(texteRecherche.toLowerCase())) {
                listeFiltree.add(f);
            }
        }
        formationList.clear();
        formationList.addAll(listeFiltree);
        adapter.notifyDataSetChanged();
    }

    // ==========================================
    // INTEGRATION DE LA BASE DE DONNEES
    // ==========================================
    private void chargerFormationsDepuisBDD() {
        // Règle d'or : On fait tout le travail de base de données en arrière-plan
        new Thread(() -> {
            try {
                // 1. On se connecte directement à la base
                com.example.sae.database.AppDatabase db = com.example.sae.database.AppDatabase.getInstance(requireActivity().getApplicationContext());
                com.example.sae.database.FormationDao dao = db.formationDao();

                // 2. On vérifie s'il y a des données. Si c'est vide (0), on les crée !
                if (dao.countAll() == 0) {
                    dao.insertFormation(new Formation(0, "Bases de l'Écologie Associative", "Environnement", "Apprenez les gestes de tri, le recyclage et la réduction des déchets."));
                    dao.insertFormation(new Formation(0, "Accueil Inclusif et Bienveillant", "Inclusion", "Formation sur l'accueil des publics fragiles ou en situation de handicap."));
                    dao.insertFormation(new Formation(0, "Droits et Devoirs du Bénévole", "Citoyenneté", "Comprendre vos responsabilités légales et éthiques en association."));
                }

                // 3. Maintenant on est ABSOLUMENT SÛR que les données sont sauvegardées
                // On les récupère toutes
                List<Formation> formationsBDD = dao.getAllFormations();

                // 4. On renvoie les données vers l'écran (Interface Graphique)
                requireActivity().runOnUiThread(() -> {
                    formationListFull.clear();
                    formationListFull.addAll(formationsBDD); // On stocke la sauvegarde

                    formationList.clear();
                    formationList.addAll(formationListFull); // On met dans la liste affichée

                    adapter.notifyDataSetChanged(); // On dit à l'écran de se rafraîchir
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}