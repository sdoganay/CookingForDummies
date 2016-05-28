package com.sedanurdoganay.cookingfordummies;

import android.os.AsyncTask;

import com.fatsecret.platform.FatSecretAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by sedanurdoganay on 28/05/16.
 */



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

    @Override
    protected void onPostExecute(RecipeItem result) {
        //TODO
    }

}

