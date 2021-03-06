package com.users.qwikhomeservices.activities.home.serviceTypes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.adapters.ServiceUsersAdapter;
import com.users.qwikhomeservices.databinding.ActivityAllServicesBinding;
import com.users.qwikhomeservices.models.Users;
import com.users.qwikhomeservices.utils.MyConstants;

import java.util.ArrayList;
import java.util.Objects;

public class AllServicesActivity extends AppCompatActivity {
    private ActivityAllServicesBinding allServicesBinding;
    private ServiceUsersAdapter adapter;
    private String accountType;
    private ArrayList<Users> arrayList = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private Parcelable mState;
    private Bundle mBundleState;
    private RecyclerView recyclerView;
    private Query query;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allServicesBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_services);

       /* AllArtisansAccountFragment allArtisansAccountFragment = new AllArtisansAccountFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,
                        R.anim.exit_to_right,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right)
                .add(R.id.frameItem, allArtisansAccountFragment)
                .commit();*/


        Intent getIntent = getIntent();

        if (getIntent != null) {

            switch (Objects.requireNonNull(getIntent.getStringExtra(MyConstants.ACCOUNT_TYPE))) {
                case MyConstants.BARBERS:
                    accountType = MyConstants.BARBERS;

                    break;
                case MyConstants.WOMEN_HAIR_STYLIST:
                    accountType = MyConstants.WOMEN_HAIR_STYLIST;

                    break;
                case MyConstants.INTERIOR_DERCORATOR:
                    accountType = MyConstants.INTERIOR_DERCORATOR;

                    break;

                case MyConstants.CARPENTERS:
                    accountType = MyConstants.CARPENTERS;

                    break;
                case MyConstants.MECHANICS:
                    accountType = MyConstants.MECHANICS;

                    break;
                case MyConstants.PEST_CONTROLS:
                    accountType = MyConstants.PEST_CONTROLS;

                    break;
                case MyConstants.PLUMBERS:
                    accountType = MyConstants.PLUMBERS;

                    break;
                case MyConstants.TILERS:
                    accountType = MyConstants.TILERS;

                    break;
                case MyConstants.TV_INSTALLERS:
                    accountType = MyConstants.TV_INSTALLERS;

                    break;
                case MyConstants.WELDERS:
                    accountType = MyConstants.WELDERS;

                    break;
                case MyConstants.ROLLERS:
                    accountType = MyConstants.ROLLERS;

                    break;
                case MyConstants.GARDENERS:
                    accountType = MyConstants.GARDENERS;

                    break;
                case MyConstants.PAINTERS:
                    accountType = MyConstants.PAINTERS;

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + getIntent.getStringExtra(MyConstants.ACCOUNT_TYPE));

            }

            setTitle(accountType);


        }


        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child(MyConstants.SERVICES).child(MyConstants.SERVICE_TYPE);
        databaseReference.keepSynced(true);
        query = databaseReference.orderByChild("accountType").equalTo(accountType);

        runOnUiThread(this::initRecyclerView);


    }

    private void initRecyclerView() {
        recyclerView = allServicesBinding.recyclerView;
        recyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {

            layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);

        } else {

            layoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(layoutManager);

        }

        adapter = new ServiceUsersAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);

        loadData();



    }


    private void loadData() {
        //querying the database BY artisan type
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {

                    arrayList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Users artisans = ds.getValue(Users.class);
                        arrayList.add(artisans);
                    }

                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleState = new Bundle();
        mState = layoutManager.onSaveInstanceState();
        mBundleState.putParcelable(MyConstants.KEY, mState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundleState != null) {

            new Handler().postDelayed(() -> {

                mState = mBundleState.getParcelable(MyConstants.KEY);
                layoutManager.onRestoreInstanceState(mState);
            }, 50);
        }

        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
