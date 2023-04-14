package com.cs4084.findit.ui.player;

import android.os.Bundle;

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

public class PlayerTaskActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPlayerTaskBinding binding;
    private SHTextTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        task = new SHTextTask();
        task.description = "In this task you must answer me these riddles three. What is a 'pato'? What animal has waterproof feathers? What is the best animal?";
        task.addAnswer("duck");
        task.addAnswer("Duck");
        task.addAnswer("Quack");




        Log.v("TAG", "Well it made it to here...");
        super.onCreate(savedInstanceState);
        Log.v("TAG", "And Now here");

        binding = ActivityPlayerTaskBinding.inflate(getLayoutInflater());
        Log.v("TAG", "Alright, here");
        setContentView(binding.getRoot());
        Log.v("TAG", "No! here!");

        final TextView description = binding.description;
        final Button checkButton = binding.checkButton;
        final Button nextButton = binding.nextButton;
        final TextInputLayout playerAnswer = binding.playerAnswer;

        description.setText(task.description);



//        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_player_task);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        Log.v("TAG", "All the way to the bottom.");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Checking...", Toast.LENGTH_SHORT).show();
                Log.v("tag", playerAnswer.getEditText().getText().toString());
                for (String acc : task.accepted_answers) {
                    if (acc.equals(playerAnswer.getEditText().getText().toString())) {
                        Log.v("tag", "YES YOU GOT IT");
                        nextButton.setEnabled(true);

                    }
                }
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