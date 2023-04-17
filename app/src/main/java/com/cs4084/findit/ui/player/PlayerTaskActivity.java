package com.cs4084.findit.ui.player;

import android.os.Build;
import android.os.Bundle;

import com.cs4084.findit.data.SHTask;
import com.cs4084.findit.data.SHTextTask;
import com.cs4084.findit.databinding.ActivityPlayerTaskBinding;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.cs4084.findit.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

// This activity no longer just holds a single task
// This activity is in charge of going through all the tasks given to it as an array.
public class PlayerTaskActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPlayerTaskBinding binding;



    ArrayList<SHTask> taskList = new ArrayList<>();
    SHTask currentTask;
    int currentTaskNum = -1;



    TextView description;
    TextView title;
    Button nextButton;


    void newTask() {
        Log.v("tag", "Setting up a new one");
        currentTaskNum += 1;
        int totalTasks = taskList.size();
        if (currentTaskNum >= totalTasks) {
                Toast.makeText(getApplicationContext(), "You have finished all the tasks!", Toast.LENGTH_SHORT).show();
                return;
        }
        currentTask = taskList.get(currentTaskNum);



        // Set it up!
        description.setText(currentTask.description);
        title.setText("Task " + (currentTaskNum + 1));
        nextButton.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if(getIntent().getExtras() != null) {
            taskList = (ArrayList<SHTask>) getIntent().getBundleExtra("BUNDLE").getSerializable("tasks");
        }



        Log.v("TAG", "Well it made it to here...");
        super.onCreate(savedInstanceState);
        Log.v("TAG", "And Now here");

        binding = ActivityPlayerTaskBinding.inflate(getLayoutInflater());
        Log.v("TAG", "Alright, here");
        setContentView(binding.getRoot());
        Log.v("TAG", "No! here!");

        description = binding.description;
        title = binding.title;
        final Button checkButton = binding.checkButton;
        nextButton = binding.nextButton;
        final TextInputLayout playerAnswer = binding.playerAnswer;


        newTask();


        Log.v("TAG", "All the way to the bottom.");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("tag", playerAnswer.getEditText().getText().toString());

                if (currentTask instanceof SHTextTask) {
                    for (String acc : ((SHTextTask) currentTask).accepted_answers) {
                        if (acc.equals(playerAnswer.getEditText().getText().toString())) {
                            Log.v("tag", "YES YOU GOT IT");
                            Toast.makeText(getApplicationContext(), "CORRECT!", Toast.LENGTH_SHORT).show();
                            nextButton.setEnabled(true);
                            return;

                        }
                    }
                    Toast.makeText(getApplicationContext(), "WRONG!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTask();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_player_task);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}