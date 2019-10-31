package utt.if26.bardcamp.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class bddMusique extends bdd {



    private SQLiteDatabase dbmusic;

    public static final String MUSIC_ID = "idm";
    public static final String MUSIC_NAME = "nom";
    public static final String MUSIC_ARTISTE = "idu_artiste";
    public static final String MUSIC_IMAGE = "image";
    public static final String MUSIC_DATE = "date_sortie";
    public static final String MUSIC_FILE = "fichier";

    public static final String TABLE_NAME = "Musique";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    MUSIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "+
                    MUSIC_NAME + " TEXT, " +
                    MUSIC_ARTISTE + " INTEGER, " +
                    MUSIC_IMAGE + " TEXT, " +
                    MUSIC_DATE + " TEXT, " +
                    MUSIC_FILE + " TEXT);";








    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    /**
     * constructeur du gestionnaire de la base de donnée
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public bddMusique(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * crée une valeur  contenant les données à intégrer
     * @param idm
     * @param nom
     * @param artiste
     * @param image
     * @param date_sortie
     * @param fichier
     */
    public void insert(int idm, String nom, int artiste, String image, String date_sortie, String fichier) {
        insert(getValue( idm,  nom,  artiste,  image,  date_sortie,  fichier));
    }

    public ContentValues getValue(int idm, String nom, int artiste, String image, String date_sortie, String fichier) {
        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, idm);
        values.put(MUSIC_NAME, nom);
        values.put(MUSIC_ARTISTE, artiste);
        values.put(MUSIC_IMAGE, image);
        values.put(MUSIC_DATE, date_sortie);
        values.put(MUSIC_FILE, fichier);

        //Log.d("STATE", "### getValues\t" + values.toString() + "\t ############################################################################################################################");

        return values;
    }

    /**
     * et a ContentValue en fonction des paramètre de la table
     * @param idm
     * @param nom
     * @param artiste
     * @param image
     * @param date_sortie
     * @param fichier
     * @return
     */
    public ContentValues getValue(String idm, String nom, String artiste, String image, String date_sortie, String fichier) {
        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, idm);
        values.put(MUSIC_NAME, nom);
        values.put(MUSIC_ARTISTE, artiste);
        values.put(MUSIC_IMAGE, image);
        values.put(MUSIC_DATE, date_sortie);
        values.put(MUSIC_FILE, fichier);
        return values;
    }

    //getter de la classe

    public void setDatabase(SQLiteDatabase db){
        dbmusic = db;
    }

    protected String getTableName() {
        return TABLE_NAME;
    }
    protected SQLiteDatabase getDb() {
        return dbmusic;
    }
    protected Object getKey() {
        return MUSIC_ARTISTE;
    }

    /**
     * retourne une liste des différents nom des colones de la base
     *
     * @return
     */
    protected String[] getProjection() {
        String[] result = {MUSIC_ID,
                MUSIC_NAME,
                MUSIC_ARTISTE,
                MUSIC_IMAGE,
                MUSIC_DATE,
                MUSIC_FILE};
        return result;
    }

    protected String getCreate() {
        return TABLE_CREATE;
    }
    protected String getDrop() {
        return TABLE_DROP;
    }

}

