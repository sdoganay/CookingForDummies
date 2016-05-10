package com.sedanurdoganay.cookingfordummies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CategoryListAdapter extends BaseAdapter {
    private List<FoodItem> data;
    private LayoutInflater inflater;

    public CategoryListAdapter(List<FoodItem> data, Context conteext){
        this.data = data;
        inflater = LayoutInflater.from(conteext);
    }

    @Override
    public int getCount(){ return data.size(); }

    @Override
    public Object getItem(int position){ return data.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        ViewHolder viewHolder;
        View localView = converView;

        boolean hasNoImage = (data.get(position).getImage() == (null));

        if(localView == null){
            localView = setLayout(parent, hasNoImage);
            viewHolder = new ViewHolder();
            setViewItems(viewHolder, localView, hasNoImage);
            localView.setTag(viewHolder);
        } else {
          viewHolder = (ViewHolder)localView.getTag();
        }

        setViewItemsContent(position, viewHolder, hasNoImage);

        return localView;
    }

    private void setViewItemsContent(int position, ViewHolder viewHolder, boolean hasNoImage) {
        viewHolder.textViewItem.setText(data.get(position).getItemName());
        viewHolder.textViewCal.setText(data.get(position).getCalories()+" kcal");
        if(!hasNoImage) {
            Bitmap imageBitmap = getBitmap(data.get(position).getImage());
            viewHolder.imageViewItem.setImageBitmap(imageBitmap);
        }
    }

    private void setViewItems(ViewHolder viewHolder, View localView, boolean hasNoImage) {
        if(!hasNoImage) {
            viewHolder.imageViewItem = (ImageView) localView.findViewById(R.id.ReceivedItemImage);
            viewHolder.textViewItem = (TextView) localView.findViewById(R.id.ReceivedListText);
            viewHolder.textViewCal = (TextView) localView.findViewById(R.id.ReceivedListTextCal);
        }else{
            viewHolder.textViewItem = (TextView) localView.findViewById(R.id.listText);
            viewHolder.textViewCal = (TextView) localView.findViewById(R.id.listTextCal);
        }
    }

    private View setLayout(ViewGroup parent, boolean hasNoImage) {
        View localView;
        if(!hasNoImage)
            localView = inflater.inflate(R.layout.content_received, parent, false);
        else
            localView = inflater.inflate(R.layout.list_row, parent, false);
        return localView;
    }


    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView textViewItem;
        private TextView textViewCal;
        private ImageView imageViewItem;
    }
    public static Bitmap getBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
