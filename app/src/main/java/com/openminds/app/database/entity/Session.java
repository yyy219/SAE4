package com.openminds.app.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "session",
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
public class Session {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int formationId;  // lien vers Formation

    // "presentielle" ou "en_ligne"
    public String type;

    public String dateDebut;
    public String dateFin;
    public String lieu;
    public String lienOnline;
    public int placesMax;
}
