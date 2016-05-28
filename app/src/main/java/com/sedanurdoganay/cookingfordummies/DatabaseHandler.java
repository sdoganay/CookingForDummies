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
    private static final String DB_NAME = "deneme";
    private static final int DB_VERSION = 1;

    private static SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
        if (isDatabaseEmpty())
            initializeDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("YourTag", "table oldu");
        db.execSQL("CREATE TABLE " + RecipeItem.TABLE_NAME + " ("
                + RecipeItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RecipeItem.COLUMN_NAME_ITEM + " TEXT,"
                + RecipeItem.COLUMN_NAME_CAL + " INTEGER,"
                + RecipeItem.COLUMN_NAME_CATEGORY + " INTEGER,"
                + RecipeItem.ITEM_IMAGE + " BLOB "
                + RecipeItem.COLUMN_DIRECTIONS+ "String" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeItem.TABLE_NAME);
        onCreate(db);
    }

    public static void createFoodItems(RecipeItem recipeItem) {
        Log.d("createFoodItem: ", "creating icindeyim");
        //TODO: if exists ignore
        ContentValues values = new ContentValues();
        values.put(RecipeItem.COLUMN_NAME_ITEM, recipeItem.getName());
        values.put(RecipeItem.COLUMN_NAME_CAL, recipeItem.getCal());
        //category olacak mı??
        //values.put(RecipeItem.COLUMN_NAME_CATEGORY, foodItems.getCategory());
        //image ı sadece byte şeklinde depolayabiliriz o yüzden URL olmaması lazım database için
        //values.put(RecipeItem.ITEM_IMAGE,recipeItem.getRecipeImageURL());

        Log.d("created: ", recipeItem.getName().toString());
        recipeItem.setId(db.insert(recipeItem.TABLE_NAME, null, values));
        Log.d("created: ", "insert basarili");
    }

    public boolean deleteFoodItem(long itemId) {
        return db.delete(RecipeItem.TABLE_NAME, RecipeItem._ID + "=?",
                new String[]{String.valueOf(itemId)}) != 0;
    }

    public List<FoodItem> fetchAllFoodItems() {
        List<FoodItem> foodItems = new ArrayList<FoodItem>();

        Cursor cursor = db.query(FoodItem.TABLE_NAME, new String[]{
                FoodItem._ID, FoodItem.COLUMN_NAME_ITEM, FoodItem.COLUMN_NAME_CAL,
                FoodItem.COLUMN_NAME_CATEGORY, FoodItem.ITEM_IMAGE}, FoodItem.COLUMN_NAME_CATEGORY + " != 5 ", null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoodItem foodItem = new FoodItem(cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),cursor.getBlob(4));
                foodItem.setId(Integer.parseInt(cursor.getString(0)));
                // Adding contact to list
                foodItems.add(foodItem);
            } while (cursor.moveToNext());
        }

        // return contact list
        return foodItems;
    }

    public List<FoodItem> fetchAllItemsIn(int category) {
        List<FoodItem> foodItems = new ArrayList<FoodItem>();

        Cursor cursor = db.query(FoodItem.TABLE_NAME, new String[]{
                        FoodItem._ID, FoodItem.COLUMN_NAME_ITEM, FoodItem.COLUMN_NAME_CAL,
                        FoodItem.COLUMN_NAME_CATEGORY,FoodItem.ITEM_IMAGE}, FoodItem.COLUMN_NAME_CATEGORY + " == ? ", new String[] { String.valueOf(category) }, null, null,
                FoodItem._ID + " DESC");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoodItem foodItem = new FoodItem(cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),cursor.getBlob(4));
                foodItem.setId(Integer.parseInt(cursor.getString(0)));
                // Adding contact to list
                foodItems.add(foodItem);
            } while (cursor.moveToNext());
        }

        // return contact list
        return foodItems;
    }


    // In case you want to update entry you can make use of this function
    public int updateFoodItem(FoodItem foodItems) {
        ContentValues values = new ContentValues();
        values.put(FoodItem.COLUMN_NAME_ITEM, foodItems.getItemName());
        values.put(FoodItem.COLUMN_NAME_CAL, foodItems.getCalories());
        values.put(FoodItem.COLUMN_NAME_CATEGORY, foodItems.getCategory());
        values.put(FoodItem.ITEM_IMAGE, foodItems.getImage());

        return db.update(FoodItem.TABLE_NAME, values, FoodItem._ID + "=?",
                new String[]{String.valueOf(foodItems.getId())});
    }

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