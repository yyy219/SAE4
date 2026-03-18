package com.example.sae.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.sae.Formation;
import java.util.List;

@Dao
public interface FormationDao {

    @Query("SELECT * FROM formation")
    List<Formation> getAllFormations();

    @Query("SELECT * FROM formation WHERE thematique = :thematique")
    List<Formation> getFormationsByThematique(String thematique);

    @Query("SELECT * FROM formation WHERE id = :id LIMIT 1")
    Formation getFormationById(int id);

    @Insert
    void insertFormation(Formation formation);

    @Query("SELECT COUNT(*) FROM formation")
    int countAll();
}