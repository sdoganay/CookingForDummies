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

    private CategoryListAdapter adapter;
    private List<SearchItem> data;
    private DatabaseHandler dbHandler;
    private Category category;

    public enum Category {
        FAVORITE , SEARCH
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ListView list = (ListView)findViewById(R.id.listView);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        switch (category){
            case FAVORITE:
                setTitle("Breakfast");
                //TODO From database
               // data = dbHandler.fetchAllItemsIn(0);
                break;
            case SEARCH:
                setTitle("Lunch");
                RecipeSearcher searcher = new RecipeSearcher(this);
                searcher.execute();
                //data = dbHandler.fetchAllItemsIn(1);
                break;
        }

        // list.addHeaderView(
                 //       LayoutInflater.from(this).inflate(R.layout.header, list, false),null, false);
                //totalCal = (TextView)findViewById(R.id.totalCal);
//TODO datayı adapter'a veriyor.

        adapter = new CategoryListAdapter(data, this);
        list.setEmptyView(findViewById(R.id.empty));
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    public void setData(List<SearchItem> data){
        this.data=data;
    }


    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) { //TODO Click display'i açacak.
        if(category == Category.FAVORITE)
            return;
        SearchItem itemClicked = data.get(position);
        FoodItem consumed = new FoodItem(itemClicked.getItemName(), itemClicked.getCalories(), 5);
        dbHandler.createFoodItems(consumed);

        if(consumed.getId() == -1)
            Toast.makeText(this, "Item is not added!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        //TODO longClick recipe'yi fav'a atsın.

        if(category == Category.SEARCH)
            position--;
        SearchItem itemClicked = data.get(position);

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
