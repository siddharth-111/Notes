package com.example.sidharth.artoonotes;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 30;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String CREATION_TIME = "creation_time";
    private static final String KEY_IMAGE = "image";
    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT ," + CREATION_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +KEY_IMAGE +" BLOB "+
                ");";
        db.execSQL(query);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
    //Add a new row to the database i.e note
    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname());
        values.put(KEY_IMAGE, product._image);
// values.put(CREATION_TIME,product.getCreationTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    //Delete a product from the database
    public void deleteProduct(int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,id);
        String whereClause = " "+COLUMN_ID + " = " + id + " ";
        db.delete(TABLE_PRODUCTS,whereClause,null);
        db.close();
    }
    //Gets the Recently Added note
    public Product databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE _id = (SELECT MAX(_id) FROM " + TABLE_PRODUCTS + ");";
        Product nm = new Product();
//Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
//Move to the first row in your results
        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                nm.set_id(c.getInt(0));
                nm.set_productname(c.getString(c.getColumnIndexOrThrow("productname")));
                nm.setCreationTime(c.getString(c.getColumnIndexOrThrow("creation_time")));
                nm.setImage(c.getBlob(3));
            } while (c.moveToNext());
        }
        db.close();
        return nm;
    }
    //Gets all the notes
    public List<Product> getAllNotes() {
        List<Product> notelist = new ArrayList<Product>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " ORDER BY _id DESC;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                Product nm = new Product();
                nm.set_id(c.getInt(0));
                nm.set_productname(c.getString(c.getColumnIndexOrThrow("productname")));
                nm.setCreationTime(c.getString(c.getColumnIndexOrThrow("creation_time")));
                nm.setImage(c.getBlob(3));
                notelist.add(nm);
            } while (c.moveToNext());
        }
        return notelist;
    }
    //Returns the total number of Notes
    public int getCount() {
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT COUNT(_id) FROM " +TABLE_PRODUCTS + ";" ;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        return c.getInt(0);
    }
    //Delete entries from Database
    public void deleteTable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS);
    }
    public Product returnProduct(int id){
        SQLiteDatabase db = getWritableDatabase();
        Product nm = new Product();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE _id = " +id+ ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                nm.set_id(c.getInt(0));
                nm.set_productname(c.getString(c.getColumnIndexOrThrow("productname")));
                nm.setCreationTime(c.getString(c.getColumnIndexOrThrow("creation_time")));
                nm.setImage(c.getBlob(3));
            } while (c.moveToNext());
        }
        db.close();
        return nm;

    }
    public void updateNote(int id,String note)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,id);
        values.put(COLUMN_PRODUCTNAME,note);
        String whereClause = " "+COLUMN_ID + " = " + id + " ";
        db.update(TABLE_PRODUCTS,values,whereClause,null);
        db.close();
    }
}