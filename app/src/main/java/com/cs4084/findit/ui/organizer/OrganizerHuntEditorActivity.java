package com.cs4084.findit.ui.organizer;

import android.os.Bundle;

import com.cs4084.findit.databinding.ActivityOrganizerHuntEditorBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.cs4084.findit.R;

public class OrganizerHuntEditorActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityOrganizerHuntEditorBinding binding;

    LinearLayout tasksContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrganizerHuntEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        tasksContainer = binding.tasksContainer;
        Button addTaskButton = binding.addTaskButton;

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_organizer_hunt_editor);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);




        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Adding...", Toast.LENGTH_SHORT).show();
                addCard("Task1");
            }
        });






    }

    private void addCard(String name) {
        final View view = getLayoutInflater().inflate(R.layout.task_card, null);

        TextView nameView = view.findViewById(R.id.name);
        Button edit = view.findViewById(R.id.edit);

        nameView.setText(name);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksContainer.removeView(view);
            }
        });

        tasksContainer.addView(view);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_organizer_tasks_list);
////        return NavigationUI.navigateUp(navController, appBarConfiguration)
////                || super.onSupportNavigateUp();
//    }
}