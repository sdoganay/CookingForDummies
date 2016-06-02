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


public class SearchListAdapter extends BaseAdapter {
    private List<SearchItem> data;
    private LayoutInflater inflater;

    public SearchListAdapter(List<SearchItem> data, Context conteext){
        this.data = data;
        inflater = LayoutInflater.from(conteext);
    }
    @Override
    public int getCount(){ return data.size(); }

    protected List<SearchItem> getData(){
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
            localView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (TextView) localView.findViewById(R.id.recipe_name_view);
            viewHolder.descriptionView = (TextView) localView.findViewById(R.id.recipe_description_view);
            viewHolder.imageView = (WebView) localView.findViewById(R.id.recipe_image_view);
            localView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)localView.getTag();
        }
        viewHolder.nameView.setText(data.get(position).getName());
        viewHolder.descriptionView.setText(data.get(position).getDescription());
        viewHolder.imageView.loadUrl(data.get(position).getImageURL());

        return localView;
    }


    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView nameView;
        private TextView descriptionView;
        private WebView imageView;
    }
}
