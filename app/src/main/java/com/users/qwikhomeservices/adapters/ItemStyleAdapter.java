package com.users.qwikhomeservices.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

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
import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.databinding.LayoutStylesListItemBinding;
import com.users.qwikhomeservices.models.StylesItemModel;
import com.users.qwikhomeservices.utils.DisplayViewUI;

import java.util.List;

public class ItemStyleAdapter extends RecyclerView.Adapter<ItemStyleAdapter.ItemStyleAdapterViewHolder> {
    public static onItemClickListener onItemClickListener;
    private Context context;
    private List<StylesItemModel> stylesItemModelList;
    private long mLastClickTime = 0;

    public ItemStyleAdapter(Context context, List<StylesItemModel> stylesItemModels) {
        this.context = context;
        this.stylesItemModelList = stylesItemModels;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        ItemStyleAdapter.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public ItemStyleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutStylesListItemBinding layoutStylesListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_styles_list_item, viewGroup, false);

        return new ItemStyleAdapterViewHolder(layoutStylesListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStyleAdapterViewHolder itemStyleAdapterViewHolder, int position) {

        StylesItemModel stylesItemModel = stylesItemModelList.get(position);
        itemStyleAdapterViewHolder.layoutStylesListItemBinding.setItem(stylesItemModel);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(DisplayViewUI.getRandomDrawableColor());
        requestOptions.error(DisplayViewUI.getRandomDrawableColor());
        requestOptions.centerCrop();

        Glide.with(itemStyleAdapterViewHolder.layoutStylesListItemBinding.getRoot().getContext())
                .load(stylesItemModel.itemImage)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        itemStyleAdapterViewHolder.layoutStylesListItemBinding.progressBar.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        itemStyleAdapterViewHolder.layoutStylesListItemBinding.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemStyleAdapterViewHolder.layoutStylesListItemBinding.imgStylePhoto);

        itemStyleAdapterViewHolder.layoutStylesListItemBinding.mCardViewItem.startAnimation(AnimationUtils.loadAnimation(itemStyleAdapterViewHolder.layoutStylesListItemBinding.getRoot().getContext()
                , R.anim.fade_scale_animation));


        itemStyleAdapterViewHolder.
                layoutStylesListItemBinding.
                mCardViewItem.
                setOnClickListener(v -> onItemClickListener.onClick(v, stylesItemModel));

        //on item click
        /*
        itemStyleAdapterViewHolder.layoutStylesListItemBinding.mCardViewItem.setOnClickListener(v -> {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }

            mLastClickTime = SystemClock.elapsedRealtime();

            int adapterPosition = itemStyleAdapterViewHolder.getAdapterPosition();
            String price = String.valueOf(stylesItemModel.getPrice());
            String itemStyleName = String.valueOf(stylesItemModel.getItemDescription());
            String itemImage = String.valueOf(stylesItemModel.getItemImage());


            Bundle bundle = new Bundle();
            bundle.putString(MyConstants.PRICE, price);
            bundle.putString(MyConstants.ITEM_DESCRIPTION, itemStyleName);
            bundle.putString(MyConstants.IMAGE_URL, itemImage);

            AppCompatActivity appCompatActivity = new AppCompatActivity();
            FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();


            SendRequestBottomSheet sendRequestBottomSheet = new SendRequestBottomSheet();
            sendRequestBottomSheet.setArguments(bundle);
            sendRequestBottomSheet.show(fragmentManager, "sendRequest");

        });
*/

    }

    @Override
    public int getItemCount() {
        return stylesItemModelList == null ? 0 : stylesItemModelList.size();
    }

    public interface onItemClickListener {
        void onClick(View view, StylesItemModel stylesItemModel);
    }

    static class ItemStyleAdapterViewHolder extends RecyclerView.ViewHolder {

        LayoutStylesListItemBinding layoutStylesListItemBinding;

        ItemStyleAdapterViewHolder(@NonNull LayoutStylesListItemBinding layoutStylesListItemBinding) {
            super(layoutStylesListItemBinding.getRoot());
            this.layoutStylesListItemBinding = layoutStylesListItemBinding;
            // layoutStylesListItemBinding.getRoot().setOnClickListener(this);
        }

       /* @Override
        public void onClick(View v) {

            StylesItemModel stylesItemModel = stylesItemModelList.get(getAdapterPosition());
            onItemClickListener.onClick(layoutStylesListItemBinding.getRoot(), stylesItemModel);

        }*/
    }

}
