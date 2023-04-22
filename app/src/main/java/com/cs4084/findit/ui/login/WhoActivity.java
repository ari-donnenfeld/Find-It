package com.cs4084.findit.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.cs4084.findit.ui.organizer.OrganizerHuntEditorActivity;
import com.cs4084.findit.ui.player.PlayerLobbyActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cs4084.findit.databinding.ActivityWhoBinding;

import com.cs4084.findit.R;

public class WhoActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityWhoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWhoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button amPlayerButton = binding.amPlayerButton;
        Button amOrganizerButton = binding.amOrganizerButton;


        amOrganizerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WhoActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });

        amPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WhoActivity.this, PlayerLobbyActivity.class);
                startActivity(myIntent);
            }
        });

    }
}