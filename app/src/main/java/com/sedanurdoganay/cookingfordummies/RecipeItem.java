package com.sedanurdoganay.cookingfordummies;

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
public class RecipeItem {

    private int id;
    private String name;
    private URL recipeURL;
    private String description;
    private URL recipeImageURL;
    private String type; //Either of Appetizers, Soups or Main Dishes.
    private int numberofServings, prepTime, cookingTime;

}
