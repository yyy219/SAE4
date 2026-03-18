package com.example.sae;

import java.io.Serializable;

public class Formation implements Serializable {

    private int id;
    private String titre;
    private String thematique;
    private String description;

    public Formation(int id, String titre, String thematique, String description) {
        this.id = id;
        this.titre = titre;
        this.thematique = thematique;
        this.description = description;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getThematique() { return thematique; }
    public String getDescription() { return description; }
}