package com.devandreid.dev.challengevenues.search;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devandreid.dev.challengevenues.R;
import com.devandreid.dev.challengevenues.models.Venue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ListItemViewHolder> {

    private List<Venue> venueList;
    private Context ctx;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public MyListAdapter(List<Venue> venueList, Context ctx, @NonNull RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.venueList = venueList;
        this.ctx = ctx;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.venue_item,
                viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
        Glide.with(ctx).load(venueList.get(i).getCategories().get(0).getIcon()
                .getPrefix() + "bg_32" + venueList.get(i).getCategories().get(0).getIcon()
                .getSuffix()).thumbnail(0.5f).crossFade()
                .error(ctx.getDrawable(R.drawable.ic_cloud_off_gray_24dp))
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(listItemViewHolder.itemIcon);

        listItemViewHolder.itemName.setText(venueList.get(i).getName());
        listItemViewHolder.itemCategory.setText(venueList.get(i).getCategories().get(0).getShortName());

        Location loc1 = new Location("dummyprovider");
        loc1.setLatitude(47.6062);
        loc1.setLongitude(-122.4079);
        Location loc2 = new Location("dummyprovider");
        loc2.setLatitude(venueList.get(i).getLocation().getLat());
        loc2.setLongitude(venueList.get(i).getLocation().getLng());

        listItemViewHolder.itemDistance.setText((loc1.distanceTo(loc2)/1000)+" Km");
    }

    @Override
    public int getItemCount() {
        return this.venueList.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_icon_venue)
        public ImageView itemIcon;
        @BindView(R.id.tv_name_venue)
        public TextView itemName;
        @BindView(R.id.tv_category_venue)
        public TextView itemCategory;
        @BindView(R.id.tv_distance_venue)
        public TextView itemDistance;
        @BindView(R.id.iv_favorite_venue)
        public ImageView itemFavorite;

        View itemview;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemview = itemView;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }

    }
}
