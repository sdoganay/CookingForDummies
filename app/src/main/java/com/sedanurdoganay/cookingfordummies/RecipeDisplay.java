package com.sedanurdoganay.cookingfordummies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RecipeDisplay extends AppCompatActivity  implements TextToSpeech.OnInitListener, View.OnClickListener {

    Button playButton;
    Button backButton;
    Button nextButton;
    Button favButton;
    Button eatenButton;

    //şu an yok
    //Button speakButton;
    ListView list;
    String text;
    Speech textToSpeech;
    Context con;
    TextToSpeech tts;
    int status;
    Text textspeechinput;
    RecipeItem ri;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    int current=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        list= (ListView) findViewById(R.id.listView);
        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] dirs = ri.getDirections();
                current--;
                if(current<dirs.length & current>-1) {
                    text = dirs[current];
                }else{
                    text="Enjoy your meal!";
                }
                speakOut();
                //promptSpeechInput();
            }
        });
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] dirs = ri.getDirections();
                current++;
                if(current<dirs.length & current>-1) {
                    text = dirs[current];
                }else{
                    text="Enjoy your meal!";
                }
                speakOut();
                //promptSpeechInput();
            }
        });
        favButton = (Button) findViewById(R.id.fav_button);

        receiveBundle();
        setTitle(ri.getName());
        //Database halledilmesi lazım
        /*favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Burası gelecek
                RecipeItem fav = new RecipeItem(ri.getId(),ri.getIdInApi(), ri.getName(), ri.getDescription(), ri.getMealType());
                //buraya database olayları gelecek
                //dbHandler.createRecipeItem(fav,"FAV");
            }
        });*/
        eatenButton = (Button) findViewById(R.id.eaten_button);

        //Database halledilmesi lazım
       /* eatenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeItem eaten = new RecipeItem(ri.getId(),ri.getIdInApi(), ri.getName(), ri.getDescription(), ri.getMealType());
                //Buraya database olayları gelecek
                //dbHandler.createRecipeItem(eaten,"EATEN");
            }
        });*/

        WebView webView = (WebView) findViewById(R.id.webView);
        //artık web sayfası koymayacağız
        //webView.setWebViewClient(new MyWebViewClient());
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl("http://www.fatsecret.com/recipes/baked-lemon-snapper/Default.aspx");

        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] dirs = ri.getDirections();
                if(current<dirs.length & current>-1) {
                    text = dirs[current];
                }else{
                    text="Enjoy your meal!";
                }
                speakOut();
                //promptSpeechInput();
            }
        });
        //şu an yok
        //speakButton = (Button) findViewById(R.id.speak_button);
        /*speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });*/
        tts = new TextToSpeech(this,this);
        //deneme için
        //text ="hello everyone, what is going on?";

        RecipeListAdapter adapter = new RecipeListAdapter(Arrays.asList(ri.getDirections()), this);
        list.setAdapter(adapter);

    }

    private void receiveBundle() {
        Bundle mBundle = getIntent().getExtras();
        String imageURL = mBundle.getString("imageURL");
        String name = mBundle.getString("name");
        ArrayList<String> directions = mBundle.getStringArrayList("directions");
        long id = mBundle.getLong("id_in_api");

        Log.d("itemName",name);
        Log.d("imageURL",imageURL);
        Log.d("id_in_api",String.valueOf(id));

        RecipeItem item = new RecipeItem();
        item.setIdInApi(id);
        item.setName(name);
        item.setRecipeImageURL(imageURL);
        item.setDirections(directions.toArray(new String[directions.size()]));
        ri=item;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                playButton.setEnabled(true);
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
   public void speakOut(){
        String text1 = text.toString();

        tts.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onClick(View v) {
        if (v == playButton) {

            speakOut();
        }
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textspeechinput.toString();
                }
                break;
            }

        }
    }

}
class RecipeListAdapter extends BaseAdapter {
    private List<String> data;
    private LayoutInflater inflater;

    public RecipeListAdapter(List<String> data, Context conteext){
        this.data = data;
        inflater = LayoutInflater.from(conteext);
    }
    @Override
    public int getCount(){ return data.size(); }

    protected List<String> getData(){
        return data;
    }

    @Override
    public Object getItem(int position){ return data.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        ViewHolder viewHolder;
        View localView = converView;
        if(localView == null){
            localView = inflater.inflate(R.layout.activity_recipe_display_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.directionView = (TextView) localView.findViewById(R.id.recipe_description_view);
            localView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)localView.getTag();
        }
        viewHolder.directionView.setText(position+". "+data.get(position));

        return localView;
    }


    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView directionView;

    }
}



