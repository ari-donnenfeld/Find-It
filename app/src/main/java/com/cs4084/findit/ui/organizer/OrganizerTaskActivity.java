package com.cs4084.findit.ui.organizer;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrganizerTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button addAnswer = binding.addAnswer;
        final Button save = binding.save;
        final TextInputEditText playerAnswer = binding.newAnswer;
        final TextInputLayout description = binding.description;
        final ListView answerList = binding.answerList;
        final Spinner penaltiesSpinner = binding.penaltiesSpinner;

        // Setup the answer list
        ArrayList<String> listItems=new ArrayList<String>();
        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        answerList.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
                Log.v("tag", description.getEditText().getText().toString());
                SHTextTask task = new SHTextTask();
                task.description = description.getEditText().getText().toString();
                task.accepted_answers = listItems;
            }
        });
        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Checking...", Toast.LENGTH_SHORT).show();
                Log.v("tag", playerAnswer.getText().toString());
                listItems.add(playerAnswer.getText().toString());
                adapter.notifyDataSetChanged();
                ViewGroup.LayoutParams params = answerList.getLayoutParams();
                View listItem = adapter.getView(0, null, answerList);
                listItem.measure(0, 0);
                Log.v("tag", String.valueOf(listItem.getMeasuredHeight()));
                params.height += listItem.getMeasuredHeight();
                answerList.setLayoutParams(params);
                answerList.requestLayout();
            }
        });

//        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.activity_organizer_task);
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_organizer_task);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        Spinner spinner = (Spinner) findViewById(R.id.penalties_spinner);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.penalties_array, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_player_task);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}