package com.example.sae.database;

import android.content.Context;
import com.example.sae.Formation;
import java.util.List;

public class FormationRepository {

    private FormationDao formationDao;

    public FormationRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.formationDao = db.formationDao();
    }

    // --- Les Interfaces de Callback pour renvoyer les données ---
    public interface OnFormationsLoaded { void onDataLoaded(List<Formation> formations); }
    public interface OnFormationLoaded { void onDataLoaded(Formation formation); }
    public interface OnCountLoaded { void onDataLoaded(int count); }
    public interface OnTaskCompleted { void onComplete(); }

    // --- Les méthodes listées dans le guide ---

    public void getAll(OnFormationsLoaded callback) {
        new Thread(() -> {
            List<Formation> liste = formationDao.getAllFormations();
            callback.onDataLoaded(liste);
        }).start();
    }

    public void getByThematique(String thematique, OnFormationsLoaded callback) {
        new Thread(() -> {
            List<Formation> liste = formationDao.getFormationsByThematique(thematique);
            callback.onDataLoaded(liste);
        }).start();
    }

    public void getById(int id, OnFormationLoaded callback) {
        new Thread(() -> {
            Formation f = formationDao.getFormationById(id);
            callback.onDataLoaded(f);
        }).start();
    }

    public void insert(Formation formation, OnTaskCompleted callback) {
        new Thread(() -> {
            formationDao.insertFormation(formation);
            if(callback != null) callback.onComplete();
        }).start();
    }

    public void countAll(OnCountLoaded callback) {
        new Thread(() -> {
            int count = formationDao.countAll();
            callback.onDataLoaded(count);
        }).start();
    }
}