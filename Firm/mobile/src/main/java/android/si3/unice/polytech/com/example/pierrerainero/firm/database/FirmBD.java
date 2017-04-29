package android.si3.unice.polytech.com.example.pierrerainero.firm.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by PierreRainero on 29/04/2017.
 */

public class FirmBD extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/android.si3.unice.polytech.com.example.pierrerainero.firm/databases/";
    private static String DB_NAME = "firm_database";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public FirmBD(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e){
            //database doesn't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Firm getFirm(){
        Firm firm = new Firm("","","","","");
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM store ORDER BY id ASC", null);

        for(int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String city = cursor.getString(3);
            int cityNumber = cursor.getInt(4);
            String mallName = cursor.getString(5);
            String description = cursor.getString(6);
            String image = cursor.getString(7);
            String region = cursor.getString(8);
            String department = cursor.getString(9);

            firm.addStore(new Store(name, address, city, cityNumber, mallName, description, image, region, department));
        }

        return firm;
    }

    /*public List<Article> getAllArticles(){
        List<Article> returnArticles = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM news ORDER BY date DESC", null);

        for(int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(7);
            String titre = cursor.getString(0);
            String auteur = cursor.getString(2);
            Date date = null;
            try {
                date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).parse(cursor.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Categorie cat = null;
            if(cursor.getInt(4)==1)
                cat = Categorie.POLITIQUE;
            else if(cursor.getInt(4)==2)
                cat = Categorie.SOCIETE;

            Media media = null;
            if(cursor.getInt(5)==0)
                media = Media.IMAGE;
            else if(cursor.getInt(5)==1)
                media = Media.VIDEO;

            String mediaUrl = cursor.getString(6);

            returnArticles.add(new Article(id,titre, auteur, date, cat, media, mediaUrl));
        }

        cursor.close();
        return returnArticles;
    }*/
}