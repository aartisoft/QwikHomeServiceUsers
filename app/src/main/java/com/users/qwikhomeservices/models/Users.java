package com.users.qwikhomeservices.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.users.qwikhomeservices.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Users extends BaseObservable {

    public String image;
    public String servicePersonId;
    public String firstName, lastName, fullName;
    public String mobileNumber;
    public String joinedDate;
    private float rating;
    private String userId;
    private String name;
    private String email;
    private String reason;
    private String styleItem;
    private double latitude;
    private double longitude;
    private String occupation;
    private String response;
    private String location;
    private String date;
    private String about;
    private String number;
    private String accountType;
    private String distanceBetween;
    private String senderPhoto;
    private String senderName;
    private String servicePersonName;
    private String servicePersonPhoto;
    private String dateRequested;


    public Users() {

    }

    public Users(String userId, String image, String firstName, String lastName, String fullName, String mobileNumber, String joinedDate) {
        this.userId = userId;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.joinedDate = joinedDate;
    }


    @BindingAdapter("imageUrl")
    public static void loadImages(CircleImageView imageView, String imageUrl) {
        Context context = imageView.getContext();
        if (imageUrl == null || imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.photoe))
                    .into(imageView);
        } else {
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

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Bindable
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Bindable
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Bindable
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Bindable
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Bindable
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Bindable
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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getServicePersonPhoto() {
        return servicePersonPhoto;
    }

    public void setServicePersonPhoto(String servicePersonPhoto) {
        this.servicePersonPhoto = servicePersonPhoto;
    }

    @Bindable
    public String getServicePersonName() {
        return servicePersonName;
    }

    public void setServicePersonName(String servicePersonName) {
        this.servicePersonName = servicePersonName;
    }

    @Bindable
    public String getStyleItem() {
        return styleItem;
    }

    public void setStyleItem(String styleItem) {
        this.styleItem = styleItem;
    }

    @Bindable
    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    @Bindable
    public String getServicePersonId() {
        return servicePersonId;
    }

    public void setServicePersonId(String servicePersonId) {
        this.servicePersonId = servicePersonId;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Bindable
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    @Bindable
    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }
}
