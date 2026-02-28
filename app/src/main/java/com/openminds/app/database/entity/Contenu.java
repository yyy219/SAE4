package com.openminds.app.database.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "contenu",
        indices = {
                @Index("formationId")
        },
        foreignKeys = @ForeignKey(
                entity = Formation.class,
                parentColumns = "id",
                childColumns = "formationId",
                onDelete = ForeignKey.CASCADE
        )
)

public class Contenu {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int formationId;

    // "texte", "video", "quiz"
    public String type;

    public String titre;
    public String contenuTexte;
    public String urlMedia;

    // ordre d'affichage dans la formation (1er, 2ème module...)
    public int ordre;
}
