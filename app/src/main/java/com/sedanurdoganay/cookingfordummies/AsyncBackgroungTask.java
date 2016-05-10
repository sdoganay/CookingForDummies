package com.sedanurdoganay.cookingfordummies;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.fatsecret.platform.FatSecretAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sedanurdoganay on 10/05/16.
 */


public class AsyncBackgroungTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("FATSECREEET", "HALOOO");
            FatSecretAPI api = new FatSecretAPI("a01009a644334ed4a59778ca8c6ae346", "9c7caefe189441f387c0213c25a6a0d7");
            Log.d("FATSECREEET", "timamdıırCANNIKAOO");
            Log.d("api: ", api.toString());
            try {
                //lets search
               /* api.generateOauthParams();
                JSONObject search = api.getRecipes("tomato");
                Log.d("search: ", search.toString());
                JSONObject recipes = search.getJSONObject("result").getJSONObject("recipes");
                Log.d("recipes: ", recipes.toString());
                JSONArray recipeler = recipes.getJSONArray("recipe");//ilk recipe'i ver.
                Log.d("recipeler: ", recipeler.toString());
                JSONObject recipe = recipeler.getJSONObject(1);
                Log.d("recipe: ", recipe.toString());
                long id = recipe.getLong("recipe_id");
                Log.d("id: ", String.valueOf(id));
                */

                //get the full recipe
                JSONObject fullrecipe = api.getRecipe(88339);
                Log.d("fullrecipe: ", fullrecipe.toString());
                JSONArray directions = fullrecipe.getJSONArray("directions");
                Log.d("directions: ", directions.toString());
                JSONObject direction = directions.getJSONObject(directions.length() - 1);//son direction'ı ver
                String directionDescript = direction.getString("direction_description");
                Log.d("Direction: ", directionDescript);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

}