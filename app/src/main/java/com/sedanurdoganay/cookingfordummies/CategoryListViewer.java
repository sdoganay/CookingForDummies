package com.sedanurdoganay.cookingfordummies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoryListViewer extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemLongClickListener{
    private RecipeSearch searcher;
    private CategoryListAdapter adapter;
    private List<SearchItem> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        searcher = new RecipeSearch();


        ListView list = (ListView)findViewById(R.id.listView);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);


        setTitle("Breakfast");

        RecipeSearcher task = new RecipeSearcher();
        task.execute(new String[] { "http://www.vogella.com" });


        // list.addHeaderView(
                 //       LayoutInflater.from(this).inflate(R.layout.header, list, false),null, false);
                //totalCal = (TextView)findViewById(R.id.totalCal);

        adapter = new CategoryListAdapter(data, this);
        list.setEmptyView(findViewById(R.id.empty));
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) { //TODO Click display'i açacak.
        if(category == Category.TOTAL_CONSUMED)
            return;
        FoodItem itemClicked = data.get(position);
        FoodItem consumed = new FoodItem(itemClicked.getItemName(), itemClicked.getCalories(), 5);
        dbHandler.createFoodItems(consumed);

        if(consumed.getId() == -1)
            Toast.makeText(this, "Item is not added!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, //TODO longClick recipe'yi fav'a atsın.
                                   int position, long id) {
        if(category == Category.TOTAL_CONSUMED)
            position--;
        FoodItem itemClicked = data.get(position);

        if(dbHandler.deleteFoodItem(itemClicked.getId())) {
            Toast.makeText(this, "deleted successfully!", Toast.LENGTH_SHORT).show();
            adapter.removeItem(position);
            updateTotal();
            return true;
        } else {
            Toast.makeText(this, "Item is not deleted!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
