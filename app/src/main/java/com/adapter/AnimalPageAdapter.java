package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.R;
import com.item.Animal;

import java.util.List;

public class AnimalPageAdapter extends PagerAdapter {

    private final Context context;
    private final List<Animal> items;

    public AnimalPageAdapter(Context context, List<Animal> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.row_card, collection, false);

        ImageView ivImage = itemView.findViewById(R.id.ivImage);
        TextView tvName = itemView.findViewById(R.id.tvName);

        Animal item = items.get(position);

        if (item != null) {
            ivImage.setImageResource(item.getImage());
            tvName.setText(item.getName());
        }

        collection.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
