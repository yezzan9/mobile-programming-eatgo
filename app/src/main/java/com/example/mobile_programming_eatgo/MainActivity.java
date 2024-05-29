package com.example.mobile_programming_eatgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the login layout initially
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the room layout when the button is clicked
                // Change this to start activity_menu
                Intent intent = new Intent(MainActivity.this, activity_menu.class);
                startActivity(intent);
            }
        });

        // Find the sign-in button in the login layout
        Button signInButton = findViewById(R.id.signin);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the signup layout when the button is clicked
                setContentView(R.layout.activity_signup);

                // Find the back button in the signup layout
                TextView backButton = findViewById(R.id.back);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Load the login layout when the back button is clicked
                        setContentView(R.layout.activity_login);

                        // Re-attach the click listener to the sign-in button
                        Button signInButton = findViewById(R.id.signin);
                        signInButton.setOnClickListener(this);
                    }
                });
            }
        });

    }
}
