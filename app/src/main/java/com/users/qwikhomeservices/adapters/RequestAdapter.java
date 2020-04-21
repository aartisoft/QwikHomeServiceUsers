package com.users.qwikhomeservices.adapters;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.databinding.LayoutUserRequestSentBinding;
import com.users.qwikhomeservices.models.RequestModel;
import com.users.qwikhomeservices.utils.DisplayViewUI;

public class RequestAdapter extends FirebaseRecyclerAdapter<RequestModel, RequestAdapter.RequestViewHolder> {


    public RequestAdapter(@NonNull FirebaseRecyclerOptions<RequestModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RequestViewHolder requestViewHolder, int i, @NonNull RequestModel requestModel) {

        requestViewHolder.layoutUserRequestSentBinding.setRequestItems(requestModel);
        requestViewHolder.showResponse(requestModel.getResponse());
        requestViewHolder.showRating(requestModel.getRating());

        //confirm work done status and rate user
        requestViewHolder.btnRateServicePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DisplayViewUI.displayAlertDialog(requestViewHolder.layoutUserRequestSentBinding.getRoot().getContext(),
                        "Confirm work ...",
                        "Please confirm that your job requested has been done",
                        "Job is done",
                        "Not done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == -1) {

                                } else if (which == -2) {
                                    dialog.dismiss();

                                }
                            }
                        });
            }
        });

        requestViewHolder.btnView.setOnClickListener(v -> {
            if (requestModel.getRating() == 0.0) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(v.getResources().getDrawable(R.drawable.applogo))
                        .setTitle("Your request to " + requestModel.getServicePersonName())
                        .setMessage(requestModel.getReason())
                        .setPositiveButton("ok", (dialog, which) -> dialog.dismiss()).create().show();

            } else if (requestModel.getRating() > 0) {

                new AlertDialog.Builder(v.getContext())
                        .setIcon(v.getResources().getDrawable(R.drawable.applogo))
                        .setTitle("Your request to " + requestModel.getServicePersonName())
                        .setMessage(requestModel.getReason() + "\n\n\n" +
                                "You rated " + requestModel.getServicePersonName() + " "
                                + requestModel.getRating() + " stars on the work done")
                        .setPositiveButton("ok", (dialog, which) -> dialog.dismiss()).create().show();
            }

        });


    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutUserRequestSentBinding layoutUserRequestSentBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_user_request_sent, viewGroup, false);

        return new RequestViewHolder(layoutUserRequestSentBinding);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        LayoutUserRequestSentBinding layoutUserRequestSentBinding;
        RatingBar ratingBar;
        private ImageButton btnView, btnChat, btnRateServicePerson;
        private TextView txtResponse, txtPaymentStatus;
        private LinearLayoutCompat linearLayoutCompat;
        private Button btnConfirmPayment;

        RequestViewHolder(@NonNull LayoutUserRequestSentBinding layoutUserRequestSentBinding) {
            super(layoutUserRequestSentBinding.getRoot());
            this.layoutUserRequestSentBinding = layoutUserRequestSentBinding;
            ratingBar = layoutUserRequestSentBinding.ratedResults;
            btnRateServicePerson = layoutUserRequestSentBinding.btnRateServicePerson;
            txtResponse = layoutUserRequestSentBinding.txtResponse;
            btnView = layoutUserRequestSentBinding.btnView;
            btnChat = layoutUserRequestSentBinding.btnChat;
            txtPaymentStatus = layoutUserRequestSentBinding.txtPaymentStatus;
            linearLayoutCompat = layoutUserRequestSentBinding.linearLayout;
            btnConfirmPayment = layoutUserRequestSentBinding.btnConfirmPayment;
        }

        //display the rating
        void showRating(float rating) {
            if (!String.valueOf(rating).isEmpty() && rating > 0) {
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating(rating);
                btnRateServicePerson.setEnabled(false);

            } else if (rating == 0) {
                ratingBar.setVisibility(View.INVISIBLE);
            }

        }

        //display the response details
        void showResponse(String response) {

            //customer can only chat , rate and view the route only when their request are accepted
            if (response.equals("Request Accepted")) {
                btnRateServicePerson.setVisibility(View.VISIBLE);
                btnChat.setVisibility(View.VISIBLE);
                // btnShowRoute.setVisibility(View.VISIBLE);
                txtResponse.setTextColor(layoutUserRequestSentBinding.getRoot().getResources().getColor(R.color.colorGreen));
                linearLayoutCompat.setVisibility(View.VISIBLE);


            } else if (response.equals("Request Rejected")) {
                btnChat.setVisibility(View.GONE);
                btnRateServicePerson.setVisibility(View.GONE);
                linearLayoutCompat.setVisibility(View.GONE);
                //btnShowRoute.setVisibility(View.INVISIBLE);
                txtResponse.setTextColor(layoutUserRequestSentBinding.getRoot().getResources().getColor(R.color.colorRed));
            }


            txtResponse.setText(response);
        }
    }
}
