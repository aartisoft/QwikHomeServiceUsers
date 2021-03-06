package com.users.qwikhomeservices.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.adapters.MessageAdapter;
import com.users.qwikhomeservices.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class ChatActivity extends AppCompatActivity {

    EmojiconEditText emojiconEditText;
    ImageView emojiButton;
    EmojIconActions emojIconActions;
    private CircleImageView imgServicePersonPhoto;
    private TextView txtName, txtContent;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    // private ChatAdapter adapter;
    private MessageAdapter adapter;
    private List<Message> messageList;
    private DatabaseReference chatsDbRef;
    private String receiverId, servicePersonName, servicePersonPhotoUrl, senderID,
            senderName, senderPhoto, reason, getAdapterPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.chatToolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton = findViewById(R.id.btnReplyBack);
        floatingActionButton.setOnClickListener(v -> addChat());


        initViews();


    }

    private void initViews() {
        Intent getDataIntent = getIntent();
        if (getDataIntent != null) {
            //get data from the view holder
            servicePersonPhotoUrl = getIntent().getStringExtra("servicePersonPhoto");//itemImage
            getAdapterPosition = getIntent().getStringExtra("adapterPosition");//adapter position of the item
            servicePersonName = getIntent().getStringExtra("servicePersonName");//name of handyMan
            reason = getIntent().getStringExtra("senderReason");//content of the report
            senderName = getIntent().getStringExtra("senderName");//name of sender
            senderID = getIntent().getStringExtra("senderID");//name of sender
            senderPhoto = getIntent().getStringExtra("senderPhoto");//sender photo
            receiverId = getIntent().getStringExtra("receiverID");//sender id


            imgServicePersonPhoto = findViewById(R.id.imgHandyManPhoto);
            txtName = findViewById(R.id.txtHandyManName);
            //  txtContent = findViewById(R.id.txtShowReason);

            txtName.setText(servicePersonName);
            // txtContent.setText(reason);
            Glide.with(this).load(servicePersonPhotoUrl).into(imgServicePersonPhoto);


        }

        chatsDbRef = FirebaseDatabase.getInstance().getReference().child("Requests").child(getAdapterPosition);
        DatabaseReference postChatsDbRef = chatsDbRef.child("Chats");
        chatsDbRef.keepSynced(true);
        Query query = postChatsDbRef.orderByChild("messageDateTime");

        messageList = new ArrayList<>();

        ConstraintLayout activity_main = findViewById(R.id.activity_main);
        emojiButton = findViewById(R.id.emoticonButton);
        emojiconEditText = findViewById(R.id.emoticonEditTxt);
        emojIconActions = new EmojIconActions(getApplicationContext(), activity_main, emojiButton, emojiconEditText);
        emojIconActions.ShowEmojicon();


        recyclerView = findViewById(R.id.recyclerViewChats);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);

        runOnUiThread(() -> {

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        messageList.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            Message message = ds.getValue(Message.class);
                            messageList.add(message);
                        }

                        recyclerView.scrollToPosition(messageList.size() - 1);
                        adapter.notifyDataSetChanged();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        });


    }


    private void addChat() {
        String postChat = emojiconEditText.getText().toString();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM HH:mm", Locale.ENGLISH);

        String dateTime = simpleDateFormat.format(calendar.getTime());
        if (!postChat.trim().isEmpty()) {
            HashMap<String, Object> chats = new HashMap<>();
            chats.put("message", postChat);
            chats.put("senderId", senderID);
            chats.put("senderName", senderName);
            chats.put("senderPhoto", senderPhoto);
            chats.put("messageDateTime", dateTime);
            chats.put("receiverName", servicePersonName);
            chats.put("receiverId", receiverId);

            String chatId = chatsDbRef.push().getKey();
            assert chatId != null;

            chatsDbRef.child("Chats").child(chatId).setValue(chats);
            emojiconEditText.getText().clear();

        } else if (postChat.trim().isEmpty()) {
            emojiconEditText.setError("Cannot send empty message");
            //  makeToast("Comment cannot be empty");
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        //  adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //  adapter.stopListening();
    }

}