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
import java.util.ArrayList;

/**
 * Created by sedanurdoganay on 10/05/16.
 */
public class RecipeSearch extends AppCompatActivity {//TODO Search sistemi yazılacak.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

    }
}
