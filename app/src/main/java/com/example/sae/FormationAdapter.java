package com.example.sae;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FormationAdapter extends ArrayAdapter<Formation> {
    private Activity context;
    private int resource;
    private List<Formation> objects;

    public FormationAdapter(Activity context, int resource, List<Formation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = convertView;
        if (layout == null) {
            layout = context.getLayoutInflater().inflate(resource, parent, false);
        }

        Formation formation = objects.get(position);

        // Récupération des éléments visuels du XML
        TextView titreTV = layout.findViewById(R.id.tv_formation_titre);
        TextView themeTV = layout.findViewById(R.id.tv_formation_theme);
        TextView descTV = layout.findViewById(R.id.tv_formation_desc);

        // Remplissage avec les données de la Formation
        titreTV.setText(formation.getTitre());
        themeTV.setText(formation.getThematique());
        descTV.setText(formation.getDescription());

        return layout;
    }
}