package utt.if26.bardcamp.bdd;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class bddUser extends bdd {



    private SQLiteDatabase dbuser;

    public static final String USER_ID = "idu";
    public static final String USER_FNAME = "prenom";
    public static final String USER_LNAME = "nom";
    public static final String USER_PSEUDO = "pseudo";
    public static final String USER_NAISSANCE = "date_de_naissance";
    public static final String USER_DISCO = "discographie";

    public static final String TABLE_NAME = "User";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    USER_LNAME + " TEXT, " +
                    USER_FNAME + " TEXT, " +
                    USER_PSEUDO + " TEXT," +
                    USER_NAISSANCE + " DATE, " +
                    USER_DISCO + " INTEGER);";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    /**
     * constructeur du gestionnaire de la base de donnée
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public bddUser(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * crée une valeur  contenant les données à intégrer
     *
     * @param idu
     * @param prenom
     * @param nom
     * @param pseudo
     * @param date_de_naissance
     * @param discographie
     * @return
     */
    public void insert(int idu, String prenom, String nom, String pseudo, String date_de_naissance, int discographie) {
        insert(getValue(idu, prenom, nom, pseudo, date_de_naissance, discographie));
    }

    /**
     * get a ContentValue en fonction des paramètre de la table
     * @param idu
     * @param prenom
     * @param nom
     * @param pseudo
     * @param date_de_naissance
     * @param discographie
     * @return
     */
    public ContentValues getValue(int idu, String prenom, String nom, String pseudo, String date_de_naissance, int discographie) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, idu);
        values.put(USER_FNAME, prenom);
        values.put(USER_LNAME, nom);
        values.put(USER_PSEUDO, pseudo);
        values.put(USER_NAISSANCE, date_de_naissance);
        values.put(USER_DISCO, discographie);

        //Log.d("STATE", "### getValues\t" + values.toString() + "\t ############################################################################################################################");

        return values;
    }

    //même que précédent mais avec string
    public ContentValues getValue(String idu, String prenom, String nom, String pseudo, String date_de_naissance, String discographie) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, idu);
        values.put(USER_FNAME, prenom);
        values.put(USER_LNAME, nom);
        values.put(USER_PSEUDO, pseudo);
        values.put(USER_NAISSANCE, date_de_naissance);
        values.put(USER_DISCO, discographie);

        //Log.d("STATE", "### getValues\t" + values.toString() + "\t ############################################################################################################################");

        return values;
    }

    //getter de la classe

    public void setDatabase(SQLiteDatabase db){
        dbuser = db;
    }

    public String getTableName() {
        return TABLE_NAME;
    }
    public SQLiteDatabase getDb() {
        return dbuser;
    }
    public Object getKey() {
        return USER_ID;
    }

    /**
     * retourne une liste des différents nom des colones de la base
     *
     * @return
     */
    public String[] getProjection() {
        String[] result = {USER_ID,
                USER_FNAME,
                USER_LNAME,
                USER_PSEUDO,
                USER_NAISSANCE,
                USER_DISCO};
        return result;
    }

    public String getCreate() {
        return TABLE_CREATE;
    }
    public String getDrop() {
        return TABLE_DROP;
    }

}
