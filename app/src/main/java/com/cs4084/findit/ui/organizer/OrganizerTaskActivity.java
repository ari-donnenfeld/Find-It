package com.cs4084.findit.ui.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cs4084.findit.data.SHTask;
import com.cs4084.findit.data.SHTextTask;
import com.cs4084.findit.databinding.ActivityOrganizerTaskBinding;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cs4084.findit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class OrganizerTaskActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityOrganizerTaskBinding binding;

    ListView answerList;
    SHTask task;
    int taskIndex;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public OrganizerTaskActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrganizerTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Takes existing task data
        // This should change the task type depending on the input
        if(getIntent().getExtras() != null) {
            task = (SHTask) getIntent().getSerializableExtra("task");
            taskIndex = (int) getIntent().getIntExtra("index", 0);
            if (task instanceof SHTextTask) {
                task = (SHTextTask) task;
            } else {
                task = (SHTask) task;
            }
        } else {
            // This should never occur
            task = new SHTextTask();
        }


        // Declare the UI elements
        final Button addAnswer = binding.addAnswer;
        final Button save = binding.save;
        final TextInputEditText playerAnswer = binding.newAnswer;
        final TextInputLayout description = binding.description;
        answerList = binding.answerList;
        final Spinner penaltiesSpinner = binding.penaltiesSpinner;

        // Setup the answer list
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        answerList.setAdapter(adapter);


        // Read the existing data
        description.getEditText().setText(task.description);
        if (task instanceof SHTextTask) {
            for (String answer: ((SHTextTask) task).accepted_answers) {
                newAnswer(answer);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                Log.v("tag", description.getEditText().getText().toString());
                task.description = description.getEditText().getText().toString();
                if (task instanceof SHTextTask) {
                    ((SHTextTask) task).accepted_answers = listItems;
                }
                Log.v("tag", "Returning the result:");
                Log.v("tag", String.valueOf(taskIndex));
                setResult(Activity.RESULT_OK,
                        new Intent().putExtra("savedTask", task).putExtra("index", taskIndex));
                finish();
            }
        });
        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Checking...", Toast.LENGTH_SHORT).show();
                Log.v("tag", playerAnswer.getText().toString());

                newAnswer(playerAnswer.getText().toString());
            }
        });

    }

    // Add the new answer to the UI list
    private void newAnswer(String text) {
        listItems.add(text);
        adapter.notifyDataSetChanged();
        ViewGroup.LayoutParams params = answerList.getLayoutParams();
        View listItem = adapter.getView(0, null, answerList);
        listItem.measure(0, 0);
        Log.v("tag", String.valueOf(listItem.getMeasuredHeight()));
        params.height += listItem.getMeasuredHeight();
        answerList.setLayoutParams(params);
        answerList.requestLayout();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_player_task);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}