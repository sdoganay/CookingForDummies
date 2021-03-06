package com.sedanurdoganay.cookingfordummies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.fatsecret.platform.FatSecretAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sedanurdoganay on 28/05/16.
 */

class RecipeSearcher extends AsyncTask<String, Void, ArrayList<SearchItem>> {

    private ListView list;
    private Context context;

    public RecipeSearcher(ListView list, Context context){
        this.list = list;
        this.context = context;
    }


    private static final String ConsumerKey = "a01009a644334ed4a59778ca8c6ae346";
    private static final String ConsumerSecret = "9c7caefe189441f387c0213c25a6a0d7";


    @Override
    protected ArrayList<SearchItem> doInBackground(String... keywords) {
        FatSecretAPI api = new FatSecretAPI(ConsumerKey, ConsumerSecret);

        ArrayList<SearchItem> recipeResults = null;

        try {
            //lets search
            JSONObject search = api.getRecipes(keywords[0]);

            JSONObject searchResults = search.getJSONObject("result").getJSONObject("recipes");
            JSONArray recipes = searchResults.getJSONArray("recipe");
            recipeResults = new ArrayList<SearchItem>();
            for(int i=0;i< recipes.length();i++){
                JSONObject recipe = (JSONObject) recipes.get(i);
                SearchItem item = new SearchItem();
                item.setDescription(recipe.getString("recipe_description"));
                item.setId(recipe.getLong("recipe_id"));
                item.setImageURL(recipe.getString("recipe_image"));
                item.setName(recipe.getString("recipe_name"));
                recipeResults.add(item);
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

    @Override
    protected void onPostExecute(ArrayList<SearchItem> result) {
        SearchListAdapter adapter = new SearchListAdapter(result, context);
        list.setAdapter(adapter);

    }



}
//ends RecipeSearcher


