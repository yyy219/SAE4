package com.example.sae;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

// On indique à Room que cette classe correspond à la table "formation"
@Entity(tableName = "formation")
public class Formation implements Serializable {

    // On précise que l'ID est la clé primaire générée automatiquement
    @PrimaryKey(autoGenerate = true)
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