package com.sedanurdoganay.cookingfordummies;

import java.net.URL;

/**
 * Created by sedanurdoganay on 11/05/16.
 */
public class SearchItem extends RecipeItem{

    private String imageURL;
    private String name;
    private long id;
    private String description;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
