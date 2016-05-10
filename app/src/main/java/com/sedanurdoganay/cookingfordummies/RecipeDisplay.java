package com.sedanurdoganay.cookingfordummies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        WebView view = (WebView) findViewById(R.id.webView);
        view.setWebChromeClient(new WebChromeClient());
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl("http://www.fatsecret.com/recipes/baked-lemon-snapper/Default.aspx");

    }

}
