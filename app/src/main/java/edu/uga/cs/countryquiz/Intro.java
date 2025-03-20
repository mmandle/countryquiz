package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // Link to your activity_intro layout

        // Find the Start button
        Button startButton = findViewById(R.id.startButton);

        // Set click listener to navigate to MainActivity
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intro.this, MainActivity.class);
            startActivity(intent);
            finish();  // Close the IntroActivity so the user can't go back to it
        });
    }
}
