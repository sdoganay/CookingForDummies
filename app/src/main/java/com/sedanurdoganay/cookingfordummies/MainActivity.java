package com.sedanurdoganay.cookingfordummies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.fatsecret.platform.FatSecretAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipeTypeGetter task = new RecipeTypeGetter();
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //public void onNewItemClick(View v) {
     //   startActivity(new Intent(this, NewItemAdder.class));
    //}

    public void onClickTo(View view) {
        int categoryId = Integer.parseInt(view.getTag().toString());
        CategoryListViewer.Category cat;
        Intent intent;

        Log.v("cat pressed: ", categoryId +"");
        switch (categoryId){
            case 0: // SEARCH
                cat = CategoryListViewer.Category.SEARCH;
                Log.v("cat name: ", cat.toString());
                intent=new Intent (MainActivity.this, CategoryListViewer.class);
                intent.putExtra(CategoryListViewer.KEY_CATEGORY, cat);
                startActivity(intent);
                break;
            case 1: // TODAYS RECİPE

                /*Intent intent2 = new Intent (MainActivity.this, RecipeDisplay.class);
                startActivity(intent2);*/
                break;
            case 2: // FAVORITE RECIPE
                cat = CategoryListViewer.Category.FAVORITE;
                intent=new Intent (MainActivity.this, CategoryListViewer.class);
                intent.putExtra(CategoryListViewer.KEY_CATEGORY, cat);
                startActivity(intent);
                break;
            case 3: // CALORIE INTAKE
                cat = CategoryListViewer.Category.CALORIE;
                Log.v("cat name: ", cat.toString());
                intent=new Intent (MainActivity.this, CategoryListViewer.class);
                Log.v("key catagory" , CategoryListViewer.KEY_CATEGORY.toString());
                intent.putExtra(CategoryListViewer.KEY_CATEGORY, cat);
                startActivity(intent);
                break;

        }
    }
     //Buna gerek kalmadı
    /*public void onRecipeSearchClick(View view){
        startActivity(new Intent (MainActivity.this, RecipeSearch.class));
    }*/

    //Bu metoda Gerek kalmadı
   /* public void onTodaysRecipeClick(View view){
        startActivity(new Intent (MainActivity.this, RecipeDisplay.class));
    }*/
}


/**
 * Created by sedanurdoganay on 28/05/16.
 */

class RecipeTypeGetter extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String ConsumerKey = "a01009a644334ed4a59778ca8c6ae346";
    private static final String ConsumerSecret = "9c7caefe189441f387c0213c25a6a0d7";


    @Override
    protected ArrayList<String> doInBackground(String... keywords) {
        FatSecretAPI api = new FatSecretAPI(ConsumerKey, ConsumerSecret);

        ArrayList<String> typeResults = null;

        try {
            //lets search
            JSONObject search = api.getRecipeTypes();
            Log.d("SearchTypes:",search.toString());
            JSONObject searchResults = search.getJSONObject("result").getJSONObject("recipe_types");
            JSONArray types = searchResults.getJSONArray("recipe_type");
            typeResults = new ArrayList<String>();
            for(int i=0;i< types.length();i++){
                String type = (String) types.get(i);
                typeResults.add(type);
            }
            /*
            JSONObject searchResults = search.getJSONObject("result").getJSONObject("recipes");
            JSONArray recipes = searchResults.getJSONArray("recipe");
            recipeResults = new ArrayList<SearchItem>();
            for(int i=0;i< recipes.length();i++){
                JSONObject recipe = (JSONObject) recipes.get(i);
                SearchItem item = new SearchItem();
                item.setDescription(recipe.getString("recipe_description"));
                item.setId(recipe.getLong("recipe_id"));
                item.setImageURL(new URL(recipe.getString("recipe_image")));
                item.setName(recipe.getString("recipe_name"));
                recipeResults.add(item);
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeResults;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        CategoryListViewer.RECIPE_TYPES = result;
        Log.d("postExec","CategoryListViewer.RECIPE_TYPES ="+CategoryListViewer.RECIPE_TYPES );
    }

}
//ends RecipeSearcher


