package com.sedanurdoganay.cookingfordummies;

import android.provider.BaseColumns;

import java.net.URL;

/**
 * Created by sedanurdoganay on 10/05/16.
 */

/*
*
recipe_id – the unique recipe identifier.
recipe_name – the name of the recipe.
recipe_url – URL of this recipe item on www.fatsecret.com.
recipe_description – A short description of the recipe.
recipe_image – URL of this recipe item's default image, only if this is available
 */
public class RecipeItem implements BaseColumns{

    public static final String TABLE_NAME = "cooking_for_dummies_data";
    public static final String ID_FOR_API = "id_for_api";
    public static final String COLUMN_NAME_ITEM = "item";
    public static final String COLUMN_NAME_CAL = "cal";
    public static final String COLUMN_NAME_CATEGORY = "category";
    public static final String ITEM_IMAGE = "image";
    public static final String COLUMN_DIRECTIONS = "directions";

    private long id;
    private long idInApi;
    private String name;
    private URL recipeURL;
    private String description;
    private URL recipeImageURL;
    private int cal;
    private String[] directions;


    public RecipeItem(){

    }

    public RecipeItem(long idInApi, String name, URL recipeURL, String description, URL recipeImageURL ){
        idInApi=this.idInApi;
        name = this.name;
        recipeURL=this.recipeURL;
        description=this.description;
        recipeImageURL=this.recipeImageURL;
    }

    protected long getIdInApi() {
        return idInApi;
    }

    protected long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected URL getRecipeURL() {
        return recipeURL;
    }

    protected void setRecipeURL(URL recipeURL) {
        this.recipeURL = recipeURL;
    }

    protected String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected URL getRecipeImageURL() {
        return recipeImageURL;
    }

    protected void setRecipeImageURL(URL recipeImageURL) {
        this.recipeImageURL = recipeImageURL;
    }

    protected void setCal(int cal){
        this.cal = cal;
    }

    protected int getCal(){
        return cal;
    }

    protected String[] getDirections(){
        return directions;
    }

    protected void setDirections(String[] directions){
        this.directions = directions;
    }
}
