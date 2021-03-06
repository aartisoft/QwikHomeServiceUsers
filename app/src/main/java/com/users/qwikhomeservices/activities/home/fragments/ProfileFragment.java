package com.users.qwikhomeservices.activities.home.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.activities.home.MainActivity;
import com.users.qwikhomeservices.databinding.FragmentProfileBinding;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding fragmentProfileBinding;
    private EditProfileFragment editProfileFragment = new EditProfileFragment();

    private OnFragmentInteractionListener mListener;
    private CircleImageView profilePhoto;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Settings");
        // Inflate the layout for this fragment
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentProfileBinding.txtName.setText(MainActivity.name);
        profilePhoto = fragmentProfileBinding.imgPhoto;
        String imageUrl = MainActivity.imageUrl;
        if (imageUrl == null) {
            Glide.with(Objects.requireNonNull(getActivity())).load(getActivity().getResources().getDrawable(R.drawable.photoe)).into(profilePhoto);
        } else {
            Glide.with(Objects.requireNonNull(getActivity())).load(imageUrl).into(profilePhoto);
        }
        profilePhoto.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));

        fragmentProfileBinding.mConstrainProfile.setOnClickListener(this::onClick);

        //TODO : UPDATE SERVICE TYPE USER PHOTO ON PICTURE CHANGE

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void onClick(View v) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,
                        R.anim.exit_to_right,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right)
                .replace(R.id.containerSettings, editProfileFragment)
                .addToBackStack(null)
                .commit();

        getActivity().setTitle("Profile");


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
