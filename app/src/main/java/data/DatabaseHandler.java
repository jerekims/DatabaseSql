package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import model.myWish;

/**
 * Created by jere on 7/9/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<myWish> wishlist=new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String CREATE_TABLE_WISHES="CREATE TABLE " + Constants.TABLE_NAME + "("
            + Constants.KEY_ID + " INTEGER PRIMARY KEY,"+ Constants.TITLE_NAME +
            " TEXT," + Constants.CONTENT_NAME + " TEXT," + Constants.DATE_NAME + " Long);";

        db.execSQL(CREATE_TABLE_WISHES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + Constants.TABLE_NAME);
//        cretate  a new table
        onCreate(db);
    }

//    method  for  adding  wishes to the database
    public void addwishes(myWish wish){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(Constants.TITLE_NAME,wish.getTitle());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());
        values.put(Constants.CONTENT_NAME, wish.getContent());

        //inserting  into database
        db.insert(Constants.TABLE_NAME, null, values);
        // Log.v("Test","Successful");
        db.close();
    }

    //getting all the wishes
    public ArrayList<myWish> getAllWishes (){

//        statement
        String selectWishes=" SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID, Constants.TITLE_NAME, Constants.CONTENT_NAME,
                Constants.DATE_NAME }, null, null, null, null, Constants.DATE_NAME + " DESC");

        if (cursor.moveToFirst()){

            do {
                myWish wish = new myWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));

                java.text.DateFormat dateformat= java.text.DateFormat.getDateInstance();
                String dateData=dateformat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                wish.setRecordate(dateData);

                wishlist.add(wish);

            } while(cursor.moveToNext());
        }

        return wishlist;
    }

}
