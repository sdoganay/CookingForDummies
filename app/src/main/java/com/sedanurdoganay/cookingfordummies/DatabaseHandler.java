package com.sedanurdoganay.cookingfordummies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengisukose on 5/28/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "recipeDB_v1";
    private static final int DB_VERSION = 1;

    private static SQLiteDatabase db;


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
        //if (isDatabaseEmpty())
          //  initializeDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("YourTag", "table oldu");
        db.execSQL("CREATE TABLE " + RecipeItem.TABLE_NAME + " ("
                + RecipeItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RecipeItem.COLUMN_NAME_ID_FOR_API + " INTEGER,"
                + RecipeItem.COLUMN_NAME_ITEM + " TEXT,"
                + RecipeItem.COLUMN_NAME_CAL + " INTEGER,"
                + RecipeItem.COLUMN_NAME_CATEGORY + " STRING,"
                +RecipeItem.COLUMN_NAME_MEAL_TYPE + " STRING,"
                + RecipeItem.COLUMN_NAME_ITEM_IMAGE + " BLOB,"
                + RecipeItem.COLUMN_NAME_DIRECTIONS + "STRING" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeItem.TABLE_NAME);
        onCreate(db);
    }

    public static void createRecipeItem(RecipeItem recipeItem,String category) {
        Log.d("createRecipeItem: ", "creating icindeyim");
        ContentValues values = new ContentValues();
        values.put(RecipeItem.COLUMN_NAME_ID_FOR_API, recipeItem.getIdInApi());
        values.put(RecipeItem.COLUMN_NAME_ITEM, recipeItem.getName());
        values.put(RecipeItem.COLUMN_NAME_CAL, recipeItem.getCal());
        values.put(RecipeItem.COLUMN_NAME_CATEGORY, category); //FAV or EATEN
        values.put(RecipeItem.COLUMN_NAME_MEAL_TYPE, recipeItem.getMealType());

        //image ı sadece byte şeklinde depolayabiliriz o yüzden URL olmaması lazım database için
        //values.put(RecipeItem.COLUMN_NAME_ITEM_IMAGE,recipeItem.getRecipeImageURL());
        values.put(RecipeItem.COLUMN_NAME_ITEM_IMAGE,getBLOBFromURL(recipeItem.getRecipeImageURL()));

        values.put(RecipeItem.COLUMN_NAME_DIRECTIONS, getStringFromArray(recipeItem.getDirections()));

        Log.d("created: ", recipeItem.getName().toString());
        recipeItem.setId(db.insert(recipeItem.TABLE_NAME, null, values));
        Log.d("created: ", "insert basarili");
    }

    private static String getStringFromArray(String[] directions) {
        //TODO Bu method acil yazılmalı!!
        return null;
    }

    private static byte[] getBLOBFromURL(String recipeImageURL) {
        //TODO Bu method acil yazılmalı!!
        return null;
    }

    public boolean deleteRecipeItem(long itemId) {
        return db.delete(RecipeItem.TABLE_NAME, RecipeItem.COLUMN_NAME_ID_FOR_API + "=?",
                new String[]{String.valueOf(itemId)}) != 0;
    }

   /*public List<RecipeItem> fetchAllFavorites() {
        List<RecipeItem> foodItems = new ArrayList<RecipeItem>();

        Cursor cursor = db.query(RecipeItem.TABLE_NAME, new String[]{
                RecipeItem._ID, RecipeItem.COLUMN_NAME_ITEM, RecipeItem.COLUMN_NAME_CAL,
                RecipeItem.COLUMN_NAME_CATEGORY, RecipeItem.COLUMN_NAME_ITEM_IMAGE}, RecipeItem.COLUMN_NAME_CATEGORY + " != 5 ", null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RecipeItem recipeItem = new RecipeItem(cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),cursor.getBlob(4));
                recipeItem.setId(Integer.parseInt(cursor.getString(0)));
                // Adding contact to list
                foodItems.add(recipeItem);
            } while (cursor.moveToNext());
        }

        // return contact list
        return foodItems;
    }*/


   public List<RecipeItem> fetchAllItemsIn(String category) {
        List<RecipeItem> recipeItems = new ArrayList<RecipeItem>();

        Cursor cursor = db.query(RecipeItem.TABLE_NAME, new String[]{
                RecipeItem._ID, //We'll get these.
                RecipeItem.COLUMN_NAME_ID_FOR_API,
                RecipeItem.COLUMN_NAME_ITEM,
                RecipeItem.COLUMN_NAME_CAL,
                RecipeItem.COLUMN_NAME_MEAL_TYPE,
                RecipeItem.COLUMN_NAME_DESCRIPTION,
                RecipeItem.COLUMN_NAME_ITEM_IMAGE,
                RecipeItem.COLUMN_NAME_DIRECTIONS}, //We'll get these.
                RecipeItem.COLUMN_NAME_CATEGORY + " == ? ", new String[] {category}, null, null,
                RecipeItem._ID + " DESC");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RecipeItem recipeItem = new RecipeItem(cursor.getLong(0), cursor.getLong(1),cursor.getString(2),cursor.getInt(3), cursor.getString(4),
                        cursor.getString(5), cursor.getBlob(6),getArrayFromString(cursor.getString(7)));
                // Adding contact to list
                recipeItems.add(recipeItem);
            } while (cursor.moveToNext());
        }

        // return contact list
        return recipeItems;
    }

    private String[] getArrayFromString(String string) {
        //TODO Acil implement et!!
        return null;
    }

    // Update metodu şimdilik kullanmayacağız
    // In case you want to update entry you can make use of this function
   /* public int updateFoodItem(FoodItem foodItems) {
        ContentValues values = new ContentValues();
        values.put(FoodItem.COLUMN_NAME_ITEM, foodItems.getItemName());
        values.put(FoodItem.COLUMN_NAME_CAL, foodItems.getCalories());
        values.put(FoodItem.COLUMN_NAME_CATEGORY, foodItems.getCategory());
        values.put(FoodItem.COLUMN_NAME_ITEM_IMAGE, foodItems.getImage());

        return db.update(FoodItem.TABLE_NAME, values, FoodItem._ID + "=?",
                new String[]{String.valueOf(foodItems.getId())});
    }*/

    public boolean isDatabaseEmpty() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + RecipeItem.TABLE_NAME, null);
        return !cursor.moveToFirst();
    }

    public void initializeDatabase() {
        /*createFoodItems(new FoodItem("Sausage", 100, 0));
        createFoodItems(new FoodItem("Muffin", 375,0));
        createFoodItems(new FoodItem("Orange juice", 122,0));*/
    }

}

