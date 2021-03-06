package com.users.qwikhomeservices.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class RequestModel extends BaseObservable {
    private float rating;
    private String senderId;
    private String receiverId;
    private String name;
    private String reason;
    private String price;
    private String itemName;
    private String response;
    private String mobileNumber;
    private String itemImage;
    private String distanceBetween;
    private String senderPhoto;
    private String servicePersonName;
    private String servicePersonPhoto;
    private String dateRequested;
    private String paymentStatus;
    private String paymentAmount;
    private String isWorkDone;
    private String firstName, lastName;

    public RequestModel() {
    }

    public RequestModel(float rating,
                        String senderId,
                        String receiverId,
                        String reason,
                        String price,
                        String itemName,
                        String response,
                        String mobileNumber,
                        String itemImage,
                        String senderPhoto,
                        String servicePersonName,
                        String servicePersonPhoto,
                        String dateRequested,
                        String paymentStatus,
                        String isWorkDone,
                        String firstName, String lastName) {
        this.rating = rating;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.reason = reason;
        this.price = price;
        this.itemName = itemName;
        this.response = response;
        this.mobileNumber = mobileNumber;
        this.itemImage = itemImage;
        this.senderPhoto = senderPhoto;
        this.servicePersonName = servicePersonName;
        this.servicePersonPhoto = servicePersonPhoto;
        this.dateRequested = dateRequested;
        this.paymentStatus = paymentStatus;
        this.isWorkDone = isWorkDone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @BindingAdapter("imageItemUrl")
    public static void loadImages(AppCompatImageView imageView, String imageUrl) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {


                        return false;
                    }
                })
                .into(imageView);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsWorkDone() {
        return isWorkDone;
    }

    public void setIsWorkDone(String isWorkDone) {
        this.isWorkDone = isWorkDone;
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Bindable
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Bindable
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    @Bindable
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Bindable
    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(String distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    @Bindable
    public String getSenderName() {
        return firstName.concat(" ").concat(lastName);
    }


    @Bindable
    public String getServicePersonName() {
        return servicePersonName;
    }

    public void setServicePersonName(String servicePersonName) {
        this.servicePersonName = servicePersonName;
    }

    public String getServicePersonPhoto() {
        return servicePersonPhoto;
    }

    public void setServicePersonPhoto(String servicePersonPhoto) {
        this.servicePersonPhoto = servicePersonPhoto;
    }

    @Bindable
    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    @Bindable
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Bindable
    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

}
