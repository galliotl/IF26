package utt.if26.bardcamp.bdd;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class bddEcoute extends bdd {



    private SQLiteDatabase dbmusic;

    public static final String MUSIC_ID = "idm";
    public static final String USER_ID = "idu";

    public static final String TABLE_NAME = "Ecoute";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    USER_ID +  " INTEGER , "+
                    MUSIC_ID + " INTEGER );";

//

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    /**
     * constructeur du gestionnaire de la base de donnée
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public bddEcoute(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * crée une valeur  contenant les données à intégrer
     * @param idm
     * @param idu
     */
    public void insert(int idm, int idu) {
        insert(getValue( idm, idu));
    }
    /**
     * crée une valeur  contenant les données à intégrer
     * @param idm
     * @param idu
     * @return
     */
    public ContentValues getValue(int idm, int idu) {
        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, idm);
        values.put(USER_ID, idu);
        return values;
    }

    /**
     * crée un ContentValue en fonction des paramètre de la table
     * @param idm
     * @param idu
     * @return
     */
    public ContentValues getValue(String idm, String idu) {
        ContentValues values = new ContentValues();
        values.put(MUSIC_ID, idm);
        values.put(USER_ID, idu);
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
        return USER_ID;
    }

    /**
     * retourne une liste des différents nom des colones de la base
     *
     * @return
     */
    protected String[] getProjection() {
        String[] result = {USER_ID,
                MUSIC_ID};
        return result;
    }

    protected String getCreate() {
        return TABLE_CREATE;
    }
    protected String getDrop() {
        return TABLE_DROP;
    }

}

