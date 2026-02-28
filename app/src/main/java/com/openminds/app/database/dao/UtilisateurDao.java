
package com.openminds.app.database.dao;

import androidx.room.*;
import com.openminds.app.database.entity.Utilisateur;

@Dao
public interface UtilisateurDao {

    @Insert
    long insert(Utilisateur utilisateur);

    @Query("SELECT * FROM utilisateur WHERE email = :email AND motDePasse = :mdp LIMIT 1")
    Utilisateur login(String email, String mdp);

    @Query("SELECT * FROM utilisateur WHERE id = :id")
    Utilisateur getById(int id);

    @Query("SELECT COUNT(*) FROM utilisateur WHERE email = :email")
    int emailExiste(String email);
}