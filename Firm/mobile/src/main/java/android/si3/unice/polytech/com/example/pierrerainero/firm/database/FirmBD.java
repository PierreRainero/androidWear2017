package android.si3.unice.polytech.com.example.pierrerainero.firm.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.si3.unice.polytech.com.example.pierrerainero.firm.R;
import android.si3.unice.polytech.com.example.pierrerainero.firm.exception.ProductException;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Firm;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Product;
import android.si3.unice.polytech.com.example.pierrerainero.firm.model.Store;
import android.util.Log;

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
        Firm firm = new Firm(myContext.getString(R.string.app_name), myContext.getString(R.string.description));
        generateProducts(firm);

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM store ORDER BY id ASC", null);

        for(int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);

            Store store = new Store(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getDouble(10),
                    cursor.getDouble(11),
                    cursor.getInt(12));
            generateProductProfit(firm, cursor.getInt(0), store);

            firm.addStore(store);
        }

        firm.rankStoreByProfit();
        return firm;
    }

    private void generateProducts(Firm firm){
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM product ORDER BY reference ASC", null);

        for(int i=0; i<cursor.getCount();i++) {
            cursor.moveToPosition(i);

            String reference = cursor.getString(0);
            boolean promoted = (cursor.getInt(4)==0)? false : true;
            boolean flagship = (cursor.getInt(5)==0)? false : true;

            Product product = null;
            try {
                product = new Product(cursor.getString(1), cursor.getString(6), reference, cursor.getString(2), cursor.getDouble(3), promoted, flagship);
            } catch (ProductException e) {
                e.printStackTrace();
            }
            firm.addProduct(reference, product);
        }
    }

    private void generateProductProfit(Firm firm, int id, Store store){
        Cursor cursor = myDataBase.rawQuery("SELECT referenceProduct, gain, cost FROM products_profit WHERE idStore="+id, null);

        for(int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);

            store.addProduct(firm.getProduct(cursor.getString(0)), cursor.getDouble(1), cursor.getDouble(2));
        }
    }
}