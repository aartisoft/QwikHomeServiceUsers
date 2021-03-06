package com.users.qwikhomeservices.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.databinding.LayoutActivityItemsBinding;
import com.users.qwikhomeservices.models.StylesItemModel;
import com.users.qwikhomeservices.utils.DisplayViewUI;
import com.users.qwikhomeservices.utils.GetTimeAgo;

public class ActivityItemAdapter extends FirebaseRecyclerAdapter<StylesItemModel, ActivityItemAdapter.ActivityItemAdapterViewHolder> {


    public ActivityItemAdapter(@NonNull FirebaseRecyclerOptions<StylesItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ActivityItemAdapterViewHolder activityItemAdapterViewHolder,
                                    int i, @NonNull StylesItemModel itemModel) {

        activityItemAdapterViewHolder.activityItemsBinding.setItems(itemModel);
        activityItemAdapterViewHolder.activityItemsBinding.txtTime.setText(GetTimeAgo.getTimeAgo(itemModel.getTimeStamp()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(DisplayViewUI.getRandomDrawableColor());
        requestOptions.error(DisplayViewUI.getRandomDrawableColor());
        requestOptions.centerCrop();

        Glide.with(activityItemAdapterViewHolder.activityItemsBinding.getRoot().getContext())
                .load(itemModel.itemImage)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        if (isFirstResource) {
                            activityItemAdapterViewHolder.activityItemsBinding.progressBar.setVisibility(View.INVISIBLE);

                        }
                        activityItemAdapterViewHolder.activityItemsBinding.progressBar.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        activityItemAdapterViewHolder.activityItemsBinding.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(activityItemAdapterViewHolder.activityItemsBinding.imgItemPhoto);


    }

    @NonNull
    @Override
    public ActivityItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutActivityItemsBinding layoutActivityItemsBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_activity_items, viewGroup, false);

        return new ActivityItemAdapterViewHolder(layoutActivityItemsBinding);

    }

    static class ActivityItemAdapterViewHolder extends RecyclerView.ViewHolder {

        LayoutActivityItemsBinding activityItemsBinding;

        ActivityItemAdapterViewHolder(@NonNull LayoutActivityItemsBinding activityItemsBinding) {
            super(activityItemsBinding.getRoot());
            this.activityItemsBinding = activityItemsBinding;
        }
    }
}
