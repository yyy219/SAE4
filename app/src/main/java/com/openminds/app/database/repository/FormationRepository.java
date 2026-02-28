package com.openminds.app.database.repository;

import android.content.Context;
import com.openminds.app.database.AppDatabase;
import com.openminds.app.database.entity.Formation;
import java.util.List;
import java.util.function.Consumer;


public class FormationRepository {

    private final AppDatabase db;

    public FormationRepository(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public void insert(Formation formation, Runnable onSuccess) {
        new Thread(() -> {
            db.formationDao().insert(formation);
            if (onSuccess != null) onSuccess.run();
        }).start();
    }

    public void getAll(Consumer<List<Formation>> callback) {
        new Thread(() -> {
            List<Formation> formations = db.formationDao().getAll();
            callback.accept(formations);
        }).start();
    }

    public void getByThematique(String thematique, Consumer<List<Formation>> callback) {
        new Thread(() -> {
            List<Formation> formations = db.formationDao().getByThematique(thematique);
            callback.accept(formations);
        }).start();
    }

    public void getById(int id, Consumer<Formation> callback) {
        new Thread(() -> {
            Formation formation = db.formationDao().getById(id);
            callback.accept(formation);
        }).start();
    }

    public void countAll(Consumer<Integer> callback) {
        new Thread(() -> {
            int count = db.formationDao().countAll();
            callback.accept(count);
        }).start();
    }
}
