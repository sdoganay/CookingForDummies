package com.sedanurdoganay.cookingfordummies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CategoryListAdapter extends BaseAdapter {
    private List<RecipeItem> data;
    private LayoutInflater inflater;

    public CategoryListAdapter(List<RecipeItem> data, Context conteext){
        this.data = data;
        inflater = LayoutInflater.from(conteext);
    }
    @Override
    public int getCount(){ return data.size(); }

    protected List<RecipeItem> getData(){
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
            localView = inflater.inflate(R.layout.categorylist_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (TextView) localView.findViewById(R.id.recipe_name_view);
            viewHolder.descriptionView = (TextView) localView.findViewById(R.id.recipe_description_view);
            viewHolder.imageView = (ImageView) localView.findViewById(R.id.imageView);
            localView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)localView.getTag();
        }
        viewHolder.nameView.setText(data.get(position).getName());
        viewHolder.descriptionView.setText(data.get(position).getDescription());
        Bitmap imageBitmap = ViewHolder.getBitmap(data.get(position).getImage());
        viewHolder.imageView.setImageBitmap(imageBitmap);

        return localView;
    }


    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView nameView;
        private TextView descriptionView;
        private ImageView imageView;

        public static Bitmap getBitmap(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }
}
