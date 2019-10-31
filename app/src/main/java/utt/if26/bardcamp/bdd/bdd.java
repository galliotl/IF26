package utt.if26.bardcamp.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class bdd extends SQLiteOpenHelper
{

    public SQLiteDatabase dbtest ;

    public static String TABLE_NAMEtest;

    public static String TABLE_CREATE ;

    public static  String TABLE_DROP;


    /**
     * Constructeur des base de données
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public bdd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("STATE", "### DAtabaseHandler #####################################################################");
    }



    /**
     * action faites automatiquement quand on crée la base de données
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {
        Log.d("STATE", "### onCreate ############################################################################################################################");
        db.execSQL(getCreate());
    }

    /**
     * action faites automatiquement quand on modifie la base de données
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("STATE", "### onUpgrade ############################################################################################################################");
        db.execSQL(getDrop());
        onCreate(db);
    }

    /**
     * action faites automatiquement quand on ouvre la base de données
     * @param db
     */
    public void onOpen(SQLiteDatabase db){
        db.execSQL(getDrop());
        onCreate(db);
        Log.d("STATE", "onOpen ###############################################################################################################################");
    }

    /**
     * essaye d'ouvrire la base de donnée en modifiable et sinon l'ouvre en lisable
     */
    public void open() {
        Log.d("STATE", "### open ############################################################################################################################");
        try {
            setDatabase(getWritableDatabase());
        } catch (SQLiteException ex) {
            setDatabase(getReadableDatabase());
        }
    }


    /**
     * ferme la base de donnée et la sauvegarde
     */
    public void close(){
        getDb().close();
    }

    /**
     * insérer un tuple dans la table
     * @param value
     */
    public void insert(ContentValues value) {
        getDb().insert(getTableName(), null, value);
    }

    /**
     * retirer un tuple de la table
     * @param intitule
     * @param value
     */
    public void delete(String intitule, Object value) {
        getDb().delete(getTableName(), intitule + " = ?", new String[]{String.valueOf(value)});
    }

    /**
     * changer un attribut de la table
     * @param intitule
     * @param value
     * @param where
     * @param whereClause
     */
    public void update(String intitule, String value, String where, Object whereClause){
        Log.d("STATE", " ### Update #########################################");
        ContentValues con= new ContentValues();
        con.put(intitule, value);

        Log.d("STATE", " ### "+ con.toString() +"\t"+ where +"\t"+ whereClause.toString() +"\t#########################################");
        getDb().update(getTableName(), con , where, new String[] {String.valueOf(whereClause)} );
    }

    /**
     * renvoie les tuples corespondants à la recherche
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param order
     * @return
     */
    public List select(String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String order) {

        Cursor cursor = getDb().query(
                getTableName(),   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                   // don't filter by row groups
                order             // The sort order
        );

        List itemIds = new ArrayList<>();
        ContentValues cursive;

        while (cursor.moveToNext()) {
            cursive = new ContentValues();
            for ( String proj : projection) {
                cursive.put(proj, cursor.getString(cursor.getColumnIndexOrThrow(proj)));
            }
            itemIds.add(cursive);
        }
        cursor.close();
        return itemIds;
    }

    /**
     * version simplifié de précédent
     * @param projection
     * @param selection
     * @param selectionArgs
     * @return
     */
    public List select(String[] projection, String selection, String[] selectionArgs) {
        return select(projection, selection, selectionArgs, null, null, (String)(getKey()));
    }


    /**
     * permet le SELECT entre les bdd
     * à lancer depuis la laison entre deuc bases
     * @param fromDb1   liste venant de la base initiale
     * @param attribut1 attribut de la base 1 à comparer
     * @param bd        base à comparer
     * @param attribut2 attribut de la base 2 à comparer
     * @return
     */
    public List crossSelect(List fromDb1, String attribut1, bdd bd,  String attribut2){
        Iterator iterator = fromDb1.iterator();
        List out = null;
        String var;
        List result= new ArrayList(), res;
        while(iterator.hasNext()) {
            var = ((ContentValues) iterator.next()).getAsString(attribut1);
            Log.d("STATE", "### itemId2 " + var + " ################################################################");
            res = bd.select(bd.getProjection(), attribut2 + "=?", new String[]{String.valueOf(var)});
            Iterator iterator2 = res.iterator();
            while (iterator2.hasNext()) {
                result.add(iterator2.next());
            }
        }
        return result;
    }


    abstract protected void setDatabase(SQLiteDatabase db);
    abstract protected String getTableName();
    abstract protected SQLiteDatabase getDb();
    abstract protected Object getKey();
    abstract protected String getCreate();
    abstract protected String getDrop();
    abstract protected String[] getProjection();

}












