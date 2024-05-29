package com.yjinpk.eatgo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initially load the login layout
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the create layout when the login button is clicked
                setContentView(R.layout.activity_create);

                // Optionally, re-attach the click listener if needed
                attachCommonListeners();
            }
        });

        // Find the sign-in button in the login layout
        Button signInButton = findViewById(R.id.signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the signup layout when the sign-in button is clicked
                setContentView(R.layout.activity_signup);

                // Find the back button in the signup layout
                TextView backButton = findViewById(R.id.back);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Load the login layout when the back button is clicked
                        setContentView(R.layout.activity_login);

                        // Re-attach the click listener to the sign-in button
                        attachCommonListeners();
                    }
                });
            }
        });
    }

    private void attachCommonListeners() {
        Button signInButton = findViewById(R.id.signin);
        if (signInButton != null) {
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Load the signup layout when the sign-in button is clicked
                    setContentView(R.layout.activity_signup);

                    // Find the back button in the signup layout
                    TextView backButton = findViewById(R.id.back);
                    backButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Load the login layout when the back button is clicked
                            setContentView(R.layout.activity_login);

                            // Re-attach the click listener to the sign-in button
                            attachCommonListeners();
                        }
                    });
                }
            });
        }

        Button createDeliveryButton = findViewById(R.id.create_delivery_button);
        if (createDeliveryButton != null) {
            createDeliveryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Display a toast message when the create delivery button is clicked
                    Toast.makeText(MainActivity.this, "배달팟 만들기 버튼 클릭됨", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Spinner menuSelection = findViewById(R.id.menu_selection);
        if (menuSelection != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.menu_items, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            menuSelection.setAdapter(adapter);
        }
    }
}
