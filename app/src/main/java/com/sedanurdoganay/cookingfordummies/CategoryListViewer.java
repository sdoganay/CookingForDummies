package com.sedanurdoganay.cookingfordummies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CategoryListViewer extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener {
    public static final String KEY_CATEGORY = "com.sedanurdoganay.categorylistviewer.category";
    private CategoryListAdapter adapter;
    private DatabaseHandler dbHandler;
    private Category category;
    private TextView totalCal;
    private Spinner spinner;
    private EditText searchText;
    private Button searchButton;
    ListView list;

    static ArrayList<String> RECIPE_TYPES;


    public enum Category {
        FAVORITE, SEARCH, CALORIE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        category = getContainer();

       list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        list.setEmptyView(findViewById(R.id.textView));

        switch (category) {
            case FAVORITE:
                setTitle("FAVORITE");
                //TODO From database
                // data = dbHandler.fetchAllItemsIn(0);
                break;
            case SEARCH:
                setTitle("RECIPE SEARCH");
                /*item1.setName("Seda");
                item1.setCal(253);
                item1.setDescription("cok guzel bi kiz");
                data.add(item1);*/
                //Log.v("data adı: " ,data.get(0).getName().toString());
                /*if(data.size()==0){
                    SearchItem item1 = new SearchItem();
                    item1.setName("null");
                    item1.setCal(0);
                    item1.setDescription("null");
                    data.add(item1);

                }*/
                list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_search, list, false), null, false);

                searchText = (EditText) findViewById(R.id.keywordView);
                searchButton = (Button) findViewById( R.id.button);

                final Spinner spinner = (Spinner) findViewById(R.id.spinner);
                // Spinner click listener
                spinner.setOnItemSelectedListener(this);
                List<String> categories = RECIPE_TYPES;
                categories.add(0,"All");

                // Creating adapter for spinner
                ArrayAdapter<String> recipeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

                // Drop down layout style - list view with radio button
                recipeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(recipeTypeAdapter);
                //RecipeSearcher searcher = new RecipeSearcher(this);
                //searcher.execute();
                //data = dbHandler.fetchAllItemsIn(1);
                searchButton.setOnClickListener(new View.OnClickListener() {
                    String text;
                    @Override
                    public void onClick(View v) {
                        text = searchText.getText().toString();
                        String type = (String) spinner.getSelectedItem();
                        Log.v("aranacak kelimeler: ", text);
                        Log.v("type: ",type);
                        if(type.equals("All")){
                            new RecipeSearcher(list, getApplicationContext()).execute(text);
                        }else{
                            new RecipeSearcherByMealType(list, getApplicationContext()).execute(text,type);
                        }
                    }
                });
                break;
            case CALORIE:
                setTitle("CALORIE INTAKE");

                list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_total, list, false), null, false);
                totalCal = (TextView)findViewById(R.id.totalCal);
                //TODO datayı database'den alacaksın.
                //data = dbHandler.fetchAllItemsIn(1);
                break;
        }
/*
        adapter = new CategoryListAdapter(data, this);
        list.setEmptyView(findViewById(R.id.textView));
        list.setAdapter(adapter);
        */
        registerForContextMenu(list); //bunu yazmadım searcher'larda.

    }


    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) { //TODO Click display'i açacak.
        /*if(category == Category.FAVORITE)
            return;
        SearchItem itemClicked = data.get(position);
        FoodItem consumed = new FoodItem(itemClicked.getItemName(), itemClicked.getCalories(), 5);
        dbHandler.createFoodItems(consumed);

        if(consumed.getId() == -1)
            Toast.makeText(this, "Item is not added!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

       /* //TODO longClick recipe'yi fav'a atsın.

        if(category == Category.SEARCH)
            position--;
        SearchItem itemClicked = data.get(position);

        if(dbHandler.deleteFoodItem(itemClicked.getId())) {
            Toast.makeText(this, "deleted successfully!", Toast.LENGTH_SHORT).show();
            adapter.removeItem(position);
            //updateTotal();
            return true;
        } else {
            Toast.makeText(this, "Item is not deleted!", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    private Category getContainer() {
        Category category = (Category) getIntent().getExtras().get(KEY_CATEGORY);
        /*if (category == null) {
            category = Category.BREAKFAST;
        }*/
        return category;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
