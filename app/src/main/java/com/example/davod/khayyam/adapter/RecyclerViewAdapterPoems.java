package com.example.davod.khayyam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.activity.PoemMenuActivity;
import com.example.davod.khayyam.activity.SinglePoemActivity;
import com.example.davod.khayyam.dataModel.DataModelPoems;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DaVoD on 1/10/2018.
 */

public class RecyclerViewAdapterPoems extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String MODEL = "model";
    public static final String RECYCLER_POSITION = "recycler_position";
    private static final String TAG = "tag" ;
    private DataModelPoems model;
    private int lastPosition = -1;
    private Context context;
    private List<DataModelPoems> models;
    private int id;

    public RecyclerViewAdapterPoems(Context context, List<DataModelPoems> models,int id ) {
        this.context = context;
        this.models = models;
        this.id = id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (id>=0)
                return new PoemMenuViewHolder(LayoutInflater.from(context).inflate(R.layout.poems_card_view,parent,false));
        else
            return new PoemMenuViewHolder(LayoutInflater.from(context).inflate(R.layout.search_model_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof PoemMenuViewHolder){
                PoemMenuViewHolder viewHolder = (PoemMenuViewHolder) holder;
                 model = models.get(position);
                if (id>=0) {
                    viewHolder.textView_title.setText(model.getTitle_cover());
                    viewHolder.textView_under_title.setText(model.getUnder_title_cover());
                    viewHolder.textView_under_title.setBackground(null);
                    viewHolder.textView_title.setBackground(null);
                    Picasso.with(context).load(model.getImage_dir()).into(viewHolder.imageView);


                }else {
                    viewHolder.txt_poem.setText(model.getText());
                    viewHolder.txt_poem_number.setText(model.getTitle_cover());
                }

                showAnimation(holder.itemView,position);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SinglePoemActivity.class);
                        intent.putExtra(PoemMenuActivity.ID,id);
                        intent.putExtra(RECYCLER_POSITION,position);
                        intent.putExtra(MODEL,model);
                        context.startActivity(intent);
                    }
                });


            }
    }
    public void showAnimation(View view,int position){
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.startAnimation(animation);
            lastPosition = position;
        }else {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            view.startAnimation(animation);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
       super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class PoemMenuViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageView;
            private TextView textView_title;
            private TextView textView_under_title;
            private TextView txt_poem;
            private TextView txt_poem_number;
        public PoemMenuViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_poems_recycler);
            textView_title = itemView.findViewById(R.id.txt_poems_title_recycler);
            textView_under_title = itemView.findViewById(R.id.txt_poems_under_title_recycler);
            txt_poem = itemView.findViewById(R.id.txt_search_recyclerView);
            txt_poem_number = itemView.findViewById(R.id.txt_poem_number);
        }
    }

}
