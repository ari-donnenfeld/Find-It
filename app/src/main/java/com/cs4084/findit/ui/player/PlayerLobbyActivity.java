package com.cs4084.findit.ui.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cs4084.findit.data.SHTask;
import com.cs4084.findit.data.SHTextTask;
import com.cs4084.findit.databinding.ActivityPlayerLobbyBinding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4084.findit.R;
import com.cs4084.findit.ui.organizer.OrganizerHuntEditorActivity;
import com.cs4084.findit.ui.organizer.OrganizerTaskActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;

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

        final String[] huntId = {null};
        final ArrayList<SHTask> taskList = new ArrayList<>();


        // Declare the UI elements
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

                // Waiting for scavenger hunt to start
                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d("tag", "onChildAdded:" + snapshot.getKey());
                        // This is called when we start the listener, not just when the children are added
                        // Therefore it will be called for tasks.
                        // Only continue for tasks
                        if (!snapshot.getKey().equals("tasks")) {return;}

                        for (DataSnapshot dss: snapshot.getChildren()) {
                            // the type could be either text or map
                            try {
                                taskList.add(dss.getValue(SHTextTask.class));
                                Log.v("tag", "It worked?");
                            } catch (Exception e) {
                                Log.v("tag", "It failed");
//                                taskList.add(dss.getValue(SHTask.class));
                            }
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Log.d("tag", "onChildRemoved:" + snapshot.getKey());
                        // Not expecting deletions here
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d("tag", "onChildChanged:" + snapshot.getKey());

                        // Needed because some values are not strings!
                        if (!snapshot.getKey().equals("status")) {return;}

                        Log.d("tag", snapshot.getValue(String.class));
                        // Scavenger hunt has started?
                        if (snapshot.getValue(String.class).equals("in progress")) {
                            Toast.makeText(getApplicationContext(), "Scavenger Hunt has Started!", Toast.LENGTH_SHORT).show();
                            Log.v("tag", "the hunt has started...");
                            Log.v("tag", huntId[0]);
                            Intent intent = new Intent(PlayerLobbyActivity.this, PlayerTaskActivity.class);
                            Bundle args = new Bundle();
                            args.putSerializable("tasks",(Serializable)taskList);
                            intent.putExtra("BUNDLE",args);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                };



                // Getting scavenger hunt id
                ValueEventListener uuidGetterEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.v("tag", "Got some data.");
                        huntId[0] = snapshot.getValue(String.class);
                        if (huntId[0] == null || huntId[0].isEmpty()) {
                            Toast.makeText(getApplicationContext(), "That hunt doesn't exist.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Here is where you might check that it's actually starting now
                        // But no time for security or error checking
                        Toast.makeText(getApplicationContext(), "Found the room. You are being added.", Toast.LENGTH_SHORT).show();
                        Log.v("tag", huntId[0]);
                        // Add player to list in hunt
                        mDatabase.child("hunts").child(huntId[0]).child("players").child(playerNameInput.getText().toString()).setValue(0);
                        // Add listener to wait for start of hunt
                        mDatabase.child("hunts").child(huntId[0]).addChildEventListener(childEventListener);
                        // Swap view to be waiting for hunt to start
                        joiningBlock.setVisibility(View.GONE);
                        waitingBlock.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.v("tag", "It was cancelled, not sure what that means");
                    }
                };
                welcomeText.setText("Welcome " + playerNameInput.getText().toString() + "! You have joined successfully");
                // Get the huntId based on the room number
                mDatabase.child("rooms").child(huntCodeInput.getText().toString().toLowerCase()).addListenerForSingleValueEvent(uuidGetterEventListener);
            }
        });
    }
}