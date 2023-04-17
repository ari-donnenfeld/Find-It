package com.cs4084.findit.ui.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cs4084.findit.data.SHClue;
import com.cs4084.findit.data.SHTask;
import com.cs4084.findit.data.SHTextTask;
import com.cs4084.findit.databinding.ActivityOrganizerHuntEditorBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.cs4084.findit.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class OrganizerHuntEditorActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityOrganizerHuntEditorBinding binding;

    LinearLayout tasksContainer;
    ArrayList<SHTask> taskList = new ArrayList<>();

    // Setup Firebase
    private DatabaseReference mDatabase;

    String huntId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrganizerHuntEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        tasksContainer = binding.tasksContainer;
        Button addTaskButton = binding.addTaskButton;
        Button saveButton = binding.saveButton;
        Button createButton = binding.createButton;

        // Setup Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        huntId = UUID.randomUUID().toString();



//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_organizer_hunt_editor);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                String roomCode = huntId.substring(0, 6);
                mDatabase.child("rooms").child(roomCode).setValue(huntId);
                mDatabase.child("hunts").child(huntId).child("owner").setValue("james@email.com");
                mDatabase.child("hunts").child(huntId).child("tasks").setValue(taskList);
                mDatabase.child("hunts").child(huntId).child("status").setValue("waiting room");

                Intent intent = new Intent(OrganizerHuntEditorActivity.this, OrganizerLobbyActivity.class);
                intent.putExtra("huntId", huntId);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                mDatabase.child("hunts").child(huntId).child("owner").setValue("james@email.com");
                mDatabase.child("hunts").child(huntId).child("status").setValue("Not Started");
                mDatabase.child("hunts").child(huntId).child("tasks").setValue(taskList);
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Adding...", Toast.LENGTH_SHORT).show();
                SHTask task = new SHTextTask();
                taskList.add(task);
                addCard(task);
            }
        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            SHTask savedTask = (SHTask) data.getSerializableExtra("savedTask");
            int savedTaskIndex = (int) data.getIntExtra("index", 0);
            Log.v("tag", "I've gotten the new one. Lets se...");
            Log.v("tag", savedTask.description);
            taskList.set(savedTaskIndex, savedTask);
            tasksContainer.removeViewAt(savedTaskIndex);
            addCard(savedTask, savedTaskIndex);
            Log.v("tag", String.valueOf(savedTaskIndex));
            Log.v("tag", String.valueOf(tasksContainer.getChildCount()));
            Log.v("tag", String.valueOf(taskList.stream().count()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_GET_MAP_LOCATION && resultCode == Activity.RESULT_OK) {
//            int latitude = data.getIntExtra("latitude", 0);
//            int longitude = data.getIntExtra("longitude", 0);
//            // do something with B's return values
//        }
//    }


    private void addCard(SHTask cardTask) {
        addCard(cardTask, tasksContainer.getChildCount());
    }
    private void addCard(SHTask cardTask, int index) {
        final View view = getLayoutInflater().inflate(R.layout.task_card, null);

        TextView nameView = view.findViewById(R.id.name);
        ImageButton edit = view.findViewById(R.id.edit);
        ImageButton delete = view.findViewById(R.id.closeButton);
        String title = cardTask.getName();

        nameView.setText(title);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Editing...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrganizerHuntEditorActivity.this, OrganizerTaskActivity.class);
                intent.putExtra("task", cardTask);
                intent.putExtra("index", taskList.indexOf(cardTask));
                startActivityForResult(intent, 0);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Deleting...", Toast.LENGTH_SHORT).show();
                // For removing it:
                tasksContainer.removeView(view);
                taskList.remove(cardTask);
            }
        });

        tasksContainer.addView(view, index);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_organizer_tasks_list);
////        return NavigationUI.navigateUp(navController, appBarConfiguration)
////                || super.onSupportNavigateUp();
//    }
}