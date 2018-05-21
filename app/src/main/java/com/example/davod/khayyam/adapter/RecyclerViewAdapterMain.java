package com.example.davod.khayyam.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.activity.BiographyActivity;
import com.example.davod.khayyam.activity.PoemMenuActivity;
import com.example.davod.khayyam.activity.SearchActivity;
import com.example.davod.khayyam.dataModel.DataModelMain;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by DaVoD on 1/8/2018.
 */

public class RecyclerViewAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_DEFAULT_ITEM = 1;
    private Context context;
    private List<DataModelMain> models;
    private FirebaseAnalytics  mFirebaseAnalytics;

    public RecyclerViewAdapterMain(Context context, List<DataModelMain> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_HEADER:
                View banner = LayoutInflater.from(context).inflate(R.layout.banner_main,parent,false);
                return new BannerViewHolder(banner);
            case VIEW_TYPE_DEFAULT_ITEM:
                View view = LayoutInflater.from(context).inflate(R.layout.option_card_view,parent,false);
                return new OptionsViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OptionsViewHolder){
            OptionsViewHolder viewHolder = (OptionsViewHolder) holder;
            DataModelMain model = models.get(position-1);
            viewHolder.title.setText(model.getTitle());
            viewHolder.title.setBackground(null);
            Picasso.with(context).load(model.getImage_dir()).into(viewHolder.imageView);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position-1){
                        case 0:
                            context.startActivity(new Intent(context,PoemMenuActivity.class));
                            break;
                        case 1:
                            context.startActivity(new Intent(context,BiographyActivity.class));
                            break;
                        case 2:
                            Intent intent = new Intent(context,BiographyActivity.class);
                            intent.putExtra("position",position);
                            context.startActivity(intent);
                            break;
                        case 3:
                            context.startActivity(new Intent(context,SearchActivity.class));
                            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "main_search");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return VIEW_TYPE_HEADER;
        }else {
            return VIEW_TYPE_DEFAULT_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return models.size()+1;
    }

    public class OptionsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;

        public OptionsViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_main_recycler);
            title = itemView.findViewById(R.id.txt_title_main_recycler);
        }
    }
    public static class BannerViewHolder extends RecyclerView.ViewHolder{

        private BannerSlider bannerSlider;

        public BannerViewHolder(View itemView) {
            super(itemView);
            bannerSlider = itemView.findViewById(R.id.banner_slider_main);
            List<Banner> banners = new ArrayList<>();
            banners.add(new DrawableBanner(R.drawable.banner1));
            banners.add(new DrawableBanner(R.drawable.banner2));
            banners.add(new DrawableBanner(R.drawable.banner3));
            bannerSlider.setBanners(banners);


        }
    }

}
