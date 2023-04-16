package com.cs4084.findit.ui.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cs4084.findit.databinding.ActivityPlayerLobbyBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4084.findit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

public class PlayerLobbyActivity extends AppCompatActivity {

    private ActivityPlayerLobbyBinding binding;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_lobby);

        binding = ActivityPlayerLobbyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Button joinButton = binding.join;
        final TextInputEditText playerNameInput = binding.playerName;
        final TextInputEditText huntCodeInput = binding.huntCode;
        final TextView welcomeText = binding.welcome;
        final ConstraintLayout waitingBlock = binding.waitingBlock;
        final LinearLayout joiningBlock = binding.joiningBlock;



        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("tag", "Joinging...");
                Log.v("tag", playerNameInput.getText().toString());
                Log.v("tag", huntCodeInput.getText().toString());
                if (playerNameInput.getText().toString() == null || playerNameInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a Team/Player name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (huntCodeInput.getText().toString().length() != 6) {
                    Toast.makeText(getApplicationContext(), "Hunt code should be 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d("tag", "onChildAdded:" + snapshot.getKey());
                        // Not expecting any additions here, just updates
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Log.d("tag", "onChildRemoved:" + snapshot.getKey());
                        // Not expecting deletions here
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d("tag", "onChildChanged:" + snapshot.getKey());
                        Log.d("tag", snapshot.getValue(String.class));
                        if (snapshot.getValue(String.class) == "in progress") {
                            Toast.makeText(getApplicationContext(), "Scavenger Hunt has Started!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                };





                ValueEventListener uuidGetterEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.v("tag", "Got some data.");
                        String huntId = snapshot.getValue(String.class);
                        if (huntId == null || huntId.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "That code doesn't exist.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Here is where you might check that it's actually starting now
                        // But no time for security or error checking
                        Toast.makeText(getApplicationContext(), "Found the room. You are being added.", Toast.LENGTH_SHORT).show();
                        Log.v("tag", huntId);
                        mDatabase.child("hunts").child(huntId).child("players").child(playerNameInput.getText().toString()).setValue(0);
                        mDatabase.child("hunts").child(huntId).child("status").addChildEventListener(childEventListener);
                        joiningBlock.setVisibility(View.GONE);
                        waitingBlock.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.v("tag", "It was cancelled, not sure what that means");
                    }
                };
                welcomeText.setText("Welcome " + playerNameInput.getText().toString() + "! You have joined successfully");
                mDatabase.child("rooms").child(huntCodeInput.getText().toString().toLowerCase()).addListenerForSingleValueEvent(uuidGetterEventListener);
            }
        });


    }
}