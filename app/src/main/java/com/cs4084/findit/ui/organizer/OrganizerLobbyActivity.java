package com.cs4084.findit.ui.organizer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

import com.cs4084.findit.databinding.ActivityOrganizerLobbyBinding;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class OrganizerLobbyActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityOrganizerLobbyBinding binding;

    String huntId;
    String roomCode;

    ListView playerList;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrganizerLobbyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(getIntent().getExtras() != null) {
            huntId = (String) getIntent().getStringExtra("huntId");
            roomCode = huntId.substring(0, 6);

            Log.v("tag", "Lobby is waiting now....");
            Log.v("tag", huntId);
            Log.v("tag", roomCode);
        }

        final TextView roomCodeSign = binding.roomCode;
        final Button beginButton = binding.beginButton;
        playerList = binding.waitingPlayers;

        // Setup the waiting players
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        playerList.setAdapter(adapter);


        roomCodeSign.setText(roomCode);


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("tag", "onChildAdded:" + snapshot.getKey());
                // A new comment has been added, add it to the displayed list
                int place = snapshot.getValue(int.class);

                listItems.add(snapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d("tag", "onChildRemoved:" + snapshot.getKey());
                // A new comment has been added, add it to the displayed list
                Comment comment = snapshot.getValue(Comment.class);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("hunts").child(huntId).child("players").addChildEventListener(childEventListener);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_owner_lobby);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_owner_lobby);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}