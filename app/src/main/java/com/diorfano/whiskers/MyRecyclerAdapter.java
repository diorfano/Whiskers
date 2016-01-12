package com.diorfano.whiskers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diorfano.models.Cat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<Cat> mCats;
    private int mRes;
    private Activity mCtx;

    public MyRecyclerAdapter(Activity ctx, List<Cat> Cats, int res) {
        this.mCats = Cats;
        this.mRes = res;
        this.mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mRes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Cat catItem = mCats.get(position);

        holder.iv_image.setImageBitmap(null);
        Picasso.with(mCtx).cancelRequest(holder.iv_image);

        int id = mCtx.getResources().getIdentifier(catItem.getImage() + "", "drawable", mCtx.getPackageName());
        if (id != 0) {
            Picasso.with(mCtx).load(id).resize(convertDpToPx(120), convertDpToPx(120)).centerCrop().into(holder.iv_image);
        } else {
            Picasso.with(mCtx).load("http://www.treffpunkt-regional.com/wp-content/themes/white/assets/images/placeholder.jpg").resize(convertDpToPx(120), convertDpToPx(120)).into(holder.iv_image);
        }

        holder.tv_breed.setText("breed - " + catItem.getBreed());
        holder.tv_legs.setText("legs - " + catItem.getLegs());
        holder.tv_prefered_food.setText(catItem.getPrefered_food().getName());

        if (catItem.getPrefered_food().getPackage1() == null) {
            holder.tv_prefered_food.setText("prefered food - " + catItem.getPrefered_food().getName());
        } else {
            holder.tv_prefered_food.setText(
                    "prefered food - " + catItem.getPrefered_food().getName() +
                    " in a " + catItem.getPrefered_food().getPackage1() + " package"
            );
        }

        try{
            holder.tv_colour.setText("colour - " + catItem.getColour());
            holder.tv_colour.setTextColor(Color.parseColor(catItem.getColour()));
        }catch (Exception e){
            holder.tv_colour.setText("colour - " + catItem.getColour() + " wrong hex color code");
        }

        holder.tv_size.setText("size - " + catItem.getSize());
        holder.tv_whiskers.setText("whiskers " + catItem.getWhiskers());

        holder.mCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mCtx, ScrollingItemActivity.class);
                intent.putExtra("cat", catItem);
                mCtx.startActivity(intent);

            }
        });

        holder.itemView.setTag(catItem);
    }

    @Override
    public int getItemCount() {
        return mCats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image;
        public TextView tv_breed;
        public TextView tv_legs;
        public TextView tv_prefered_food;
        public TextView tv_colour;
        public TextView tv_size;
        public TextView tv_whiskers;
        public CardView mCv;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_breed = (TextView) itemView.findViewById(R.id.tv_breed);
            tv_legs = (TextView) itemView.findViewById(R.id.tv_legs);
            tv_prefered_food = (TextView) itemView.findViewById(R.id.tv_prefered_food);
            tv_colour = (TextView) itemView.findViewById(R.id.tv_colour);
            tv_size = (TextView) itemView.findViewById(R.id.tv_size);
            tv_whiskers = (TextView) itemView.findViewById(R.id.tv_whiskers);
            mCv = (CardView) itemView.findViewById(R.id.mCv);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private int convertDpToPx(int dp) {
        return Math.round(dp * (mCtx.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }
}