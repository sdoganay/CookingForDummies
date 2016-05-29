package tj.ic.caloriecalculator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sedanurdoganay.cookingfordummies.DatabaseHandler;

import java.util.Random;

/**
 * Created by sedanurdoganay on 29/05/16.
 */
public class TodaysRecipeNotification {


    public class ItemReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String itemName = bundle.getString("itemName");
            Log.d("itemName",itemName);
            byte[] imageBytes = bundle.getByteArray("image");
            Log.d("imageBytes",imageBytes.toString());
            int cal = bundle.getInt("calories");
            Log.d("calories",String.valueOf(cal));
            FoodItem item = new FoodItem(itemName,cal, 4,imageBytes);
            Log.d("item created:", item.toString());
            DatabaseHandler.createFoodItems(item);
        }


    }

    public class AlarmReceiver extends BroadcastReceiver {
        public static final String RANDOM_FOOD_INTENT = "com.sedanurdoganay.datagenerator.BROADCAST";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("AlarmReceiver", "OnReceive started.");
            Intent i = new Intent(RANDOM_FOOD_INTENT);
            //FoodItem item = DatabaseHandler.getFoodByID(getRandomNumber());
            Bundle mBundle = new Bundle();
            mBundle.putByteArray("image", item.getImage());
            mBundle.putString("itemName", item.getItemName());
            mBundle.putInt("calories", item.getCalories());
            i.putExtras(mBundle);
            context.sendBroadcast(i);
            Toast.makeText(context, item.toString(), Toast.LENGTH_LONG).show();
            Log.d("AlarmReceiver", "OnReceive finished.");
        }

        public int getRandomNumber( ) {
            Random rgen = new Random();
            return rgen.nextInt(4);
        }

    }




}
