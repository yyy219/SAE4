package com.example.sae.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

public class FormationFragment extends Fragment {

    private FormationAdapter adapter;
    private List<Formation> formationList;      // Liste affichée à l'écran
    private List<Formation> formationListFull;  // Sauvegarde de la liste complète pour la recherche

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

        chargerFormationsLocales();

        // ======= LA MAGIE DE LA RECHERCHE =======
        etRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrerFormations(s.toString()); // On filtre à chaque lettre tapée
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

    // Méthode pour filtrer la liste
    private void filtrerFormations(String texteRecherche) {
        List<Formation> listeFiltree = new ArrayList<>();

        for (Formation f : formationListFull) {
            // On cherche dans le titre OU dans la thématique (sans se soucier des majuscules)
            if (f.getTitre().toLowerCase().contains(texteRecherche.toLowerCase()) ||
                    f.getThematique().toLowerCase().contains(texteRecherche.toLowerCase())) {
                listeFiltree.add(f);
            }
        }

        // On met à jour l'affichage
        formationList.clear();
        formationList.addAll(listeFiltree);
        adapter.notifyDataSetChanged();
    }

    private void chargerFormationsLocales() {
        formationListFull.clear(); // On remplit la liste de sauvegarde

        formationListFull.add(new Formation(1, "Bases de l'Écologie Associative", "Environnement", "Apprenez les gestes de tri, le recyclage et la réduction des déchets lors de nos événements locaux."));
        formationListFull.add(new Formation(2, "Organiser un événement Zéro Déchet", "Environnement", "Les étapes clés pour planifier une manifestation publique en minimisant l'impact carbone."));
        formationListFull.add(new Formation(3, "Accueil Inclusif et Bienveillant", "Inclusion", "Formation sur l'accueil des publics fragiles ou en situation de handicap (moteur et cognitif)."));
        formationListFull.add(new Formation(4, "Lutter contre les discriminations", "Tolérance", "Identifier et réagir face aux comportements discriminatoires (sexisme, racisme, etc.) sur le terrain."));
        formationListFull.add(new Formation(5, "Droits et Devoirs du Bénévole", "Citoyenneté", "Comprendre vos responsabilités légales et éthiques en tant que représentant de l'association."));
        formationListFull.add(new Formation(6, "Laïcité et Neutralité", "Citoyenneté", "Comment appliquer le principe de laïcité dans nos actions quotidiennes auprès du public."));
        formationListFull.add(new Formation(7, "Promouvoir l'Égalité Homme-Femme", "Égalité", "Outils et postures pour garantir une parité réelle dans les instances dirigeantes et sur le terrain."));

        // On copie tout dans la liste affichée
        formationList.clear();
        formationList.addAll(formationListFull);
        adapter.notifyDataSetChanged();
    }
}