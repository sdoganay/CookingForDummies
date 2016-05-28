package com.sedanurdoganay.cookingfordummies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
       // CategoryListViewer.Category cat = null;
        Log.v("cat pressed: ", categoryId +"");
        switch (categoryId){
            case 0: // SEARCH
                startActivity(new Intent (MainActivity.this, RecipeSearch.class));
                break;
            case 1: // TODAYS RECİPE
                Intent intent2 = new Intent (MainActivity.this, RecipeDisplay.class);
                startActivity(intent2);
                break;
            case 2: // FAVORITE RECIPE
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
