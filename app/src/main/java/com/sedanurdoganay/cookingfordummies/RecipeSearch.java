package com.sedanurdoganay.cookingfordummies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fatsecret.platform.FatSecretAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by sedanurdoganay on 10/05/16.
 */
public class RecipeSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

    }

<<<<<<< Updated upstream
=======
    class RecipeSearcher extends AsyncTask<String, Void, JSONArray> {

>>>>>>> Stashed changes

    class RecipeSearcher extends AsyncTask<String, Void, SearchItem[]> {


        private static final String ConsumerKey = "a01009a644334ed4a59778ca8c6ae346";
        private static final String ConsumerSecret = "9c7caefe189441f387c0213c25a6a0d7";

        @Override
        protected SearchItem[] doInBackground(String... strings) {
            FatSecretAPI api = new FatSecretAPI(ConsumerKey, ConsumerSecret);

<<<<<<< Updated upstream
=======
            JSONArray recipes = null;

>>>>>>> Stashed changes
            SearchItem[] recipeResults = null;

            try {
                //lets search
                JSONObject search = api.getRecipes(strings[0]);
                JSONObject searchResults = search.getJSONObject("result").getJSONObject("recipes");
                JSONArray recipes = searchResults.getJSONArray("recipe");
                recipeResults = new SearchItem[recipes.length()];
                for(int i=0;i< recipes.length();i++){
                    JSONObject recipe = (JSONObject) recipes.get(i);
                    SearchItem item = new SearchItem();
                    item.setDescription(recipe.getString("recipe_description"));
                    item.setId(recipe.getLong("recipe_id"));
                    item.setImageURL(new URL(recipe.getString("recipe_image")));
                    item.setName(recipe.getString("recipe_name"));
                    recipeResults[i] = item;
                }

               /*
                JSONObject recipe = recipes.getJSONObject(1);
                Log.d("recipe: ", recipe.toString());
                long id = recipe.getLong("recipe_id");
                Log.d("id: ", String.valueOf(id));
                */

                /*
                //get the full recipe
                JSONObject fullrecipe = api.getRecipe(88339);
                Log.d("fullrecipe: ", fullrecipe.toString());
                JSONObject result = fullrecipe.getJSONObject("result");
                JSONObject recipe = result.getJSONObject("recipe");
                JSONObject directions = recipe.getJSONObject("directions");
                JSONArray directionlar = directions.getJSONArray("direction");
                Log.d("directions: ", directions.toString());
                JSONObject direction = directionlar.getJSONObject(directionlar.length() - 1);//son direction'ı ver
                Log.d("direction: ", direction.toString());
                String directionDescript = direction.getString("direction_description");
                Log.d("directionDescript: "+ (directionlar.length()) +" ->", directionDescript);
                //"direction_description":"Let simmer for 30 minutes.","direction_number":"4"
                */

            } catch (Exception e) {
                e.printStackTrace();
            }
            return recipeResults;
        }


        class FullRecipeGetter extends AsyncTask<Long, Void, RecipeItem> {

            private static final String ConsumerKey = "a01009a644334ed4a59778ca8c6ae346";
            private static final String ConsumerSecret = "9c7caefe189441f387c0213c25a6a0d7";

            @Override
            protected RecipeItem doInBackground(Long... IDs) {
                FatSecretAPI api = new FatSecretAPI(ConsumerKey, ConsumerSecret);

                RecipeItem item = null;
                try {
                    JSONObject result = api.getRecipe(IDs[0]).getJSONObject("result");
                    JSONObject recipe = result.getJSONObject("recipe");

                    item = JSON2RecipeItem(recipe);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return item;
            }

            private RecipeItem JSON2RecipeItem(JSONObject recipe) {
                RecipeItem item = new RecipeItem();
                try {
                    item.setId(recipe.getLong("recipe_id"));
                    item.setDescription(recipe.getString("recipe_description"));
                    item.setName(recipe.getString("recipe_name"));
                    item.setRecipeImageURL(new URL(recipe.getJSONObject("recipe_images").getString("recipe_image")));
                    item.setRecipeURL(new URL(recipe.getString("recipe_url")));
                    item.setCal(recipe.getJSONObject("serving_sizes").getJSONObject("serving").getInt("calories"));
                    item.setDirections(getDirectionsFromJSON(recipe));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return item;
            }


            private String[] getDirectionsFromJSON(JSONObject recipe) {
                String[] directionsArray = null;
                try {
                    JSONArray directions = recipe.getJSONObject("directions").getJSONArray("direction");
                    directionsArray = new String[directions.length()];
                    for (int i = 0; i < directions.length(); i++) {
                        JSONObject direction = (JSONObject) directions.get(i);
                        directionsArray[direction.getInt("direction_number") - 1] = direction.getString("direction_description");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return directionsArray;
            }

        }


    }
}
