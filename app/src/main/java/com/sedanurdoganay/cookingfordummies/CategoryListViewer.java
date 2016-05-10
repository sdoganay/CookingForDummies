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
    public static final String KEY_CATEGORY = "tj.ic.categorylistviewer.category";
    private Category category;
    private TextView totalCal;
    private DatabaseHandler dbHandler;
    private CategoryListAdapter adapter;
    private List<FoodItem> data;

    public enum Category {
        BREAKFAST, LUNCH, DINNER, SNACKS, RECEIVED, TOTAL_CONSUMED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        category = getContainer();
        dbHandler = new DatabaseHandler(this);


        ListView list = (ListView)findViewById(R.id.list);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        switch (category){
            case BREAKFAST:
                setTitle("Breakfast");
                data = dbHandler.fetchAllItemsIn(0);
                break;
            case LUNCH:
                setTitle("Lunch");
                data = dbHandler.fetchAllItemsIn(1);
                break;
            case DINNER:
                setTitle("Dinner");
                data = dbHandler.fetchAllItemsIn(2);
                break;
            case SNACKS:
                setTitle("Snacks");
                data = dbHandler.fetchAllItemsIn(3);
                break;
            case RECEIVED:
                setTitle("Received");
                data = dbHandler.fetchAllItemsIn(4);
                break;
            case TOTAL_CONSUMED:
                setTitle("Total Calories");
                data = dbHandler.fetchAllItemsIn(5);
                list.addHeaderView(
                        LayoutInflater.from(this).inflate(R.layout.header, list, false),
                        null, false);
                totalCal = (TextView)findViewById(R.id.totalCal);
                updateTotal();
                break;
        }

        adapter = new CategoryListAdapter(data, this);
        list.setEmptyView(findViewById(R.id.empty));
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }


    private Category getContainer() {
        Category category = (Category) getIntent().getExtras().get(KEY_CATEGORY);
        if (category == null) {
            category = Category.BREAKFAST;
        }
        return category;
    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
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
    public boolean onItemLongClick(AdapterView<?> parent, View view,
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

    void updateTotal(){
        if(category != Category.TOTAL_CONSUMED)
            return;
        int total = 0;
        for(FoodItem item : data)
            total += item.getCalories();

        totalCal.setText(total+"");
    }
}
