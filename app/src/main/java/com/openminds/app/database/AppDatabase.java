package com.openminds.app.database;

import android.content.Context;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.openminds.app.database.dao.*;
import com.openminds.app.database.entity.*;

@Database(
        entities = {
                Utilisateur.class,
                Formation.class,
                Session.class,
                Inscription.class,
                Contenu.class,
                Telechargement.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UtilisateurDao utilisateurDao();
    public abstract FormationDao formationDao();
    public abstract SessionDao sessionDao();
    public abstract InscriptionDao inscriptionDao();
    public abstract ContenuDao contenuDao();
    public abstract TelechargementDao telechargementDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "openminds.db"
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new Thread(() -> {
                                try {
                                    AppDatabase database = getInstance(context);

                                    Utilisateur admin = new Utilisateur();
                                    admin.nom = "Admin";
                                    admin.prenom = "OpenMinds";
                                    admin.email = "admin@openminds.fr";
                                    admin.motDePasse = "admin123";
                                    admin.role = "admin";
                                    admin.dateInscription = "2026-01-01";
                                    database.utilisateurDao().insert(admin);

                                    Utilisateur benevole = new Utilisateur();
                                    benevole.nom = "Dupont";
                                    benevole.prenom = "Marie";
                                    benevole.email = "marie@openminds.fr";
                                    benevole.motDePasse = "marie123";
                                    benevole.role = "benevole";
                                    benevole.dateInscription = "2026-01-01";
                                    database.utilisateurDao().insert(benevole);

                                    Formation f1 = new Formation();
                                    f1.titre = "Introduction a l'inclusion";
                                    f1.description = "Comprendre les enjeux de l'inclusion sociale";
                                    f1.thematique = "inclusion";
                                    f1.dureeMinutes = 45;
                                    f1.dateCreation = "2026-01-01";
                                    database.formationDao().insert(f1);

                                    Formation f2 = new Formation();
                                    f2.titre = "Environnement et citoyennete";
                                    f2.description = "Agir pour la planete au quotidien";
                                    f2.thematique = "environnement";
                                    f2.dureeMinutes = 60;
                                    f2.dateCreation = "2026-01-01";
                                    database.formationDao().insert(f2);

                                    Formation f3 = new Formation();
                                    f3.titre = "Egalite et diversite";
                                    f3.description = "Promouvoir l'egalite dans nos actions";
                                    f3.thematique = "egalite";
                                    f3.dureeMinutes = 30;
                                    f3.dateCreation = "2026-01-01";
                                    database.formationDao().insert(f3);

                                    Session s1 = new Session();
                                    s1.formationId = 1;
                                    s1.type = "en_ligne";
                                    s1.dateDebut = "2026-03-15 09:00";
                                    s1.dateFin = "2026-03-15 10:00";
                                    s1.lienOnline = "https://meet.openminds.fr/inclusion";
                                    s1.placesMax = 20;
                                    database.sessionDao().insert(s1);

                                    Session s2 = new Session();
                                    s2.formationId = 2;
                                    s2.type = "presentielle";
                                    s2.dateDebut = "2026-03-20 14:00";
                                    s2.dateFin = "2026-03-20 16:00";
                                    s2.lieu = "Salle A - Paris 11e";
                                    s2.placesMax = 15;
                                    database.sessionDao().insert(s2);

                                    android.util.Log.d("OpenMinds_DB", "Base prepeulee avec succes");

                                } catch (Exception e) {
                                    android.util.Log.e("OpenMinds_DB", "Erreur lors du prepeulement : " + e.getMessage());
                                }
                            }).start();
                        }
                    })
                    .build();
        }
        return instance;
    }
}