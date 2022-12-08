package com.example.chatbot;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.chatbot.databinding.ActivityMainBinding;

import com.salesforce.servicee;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String ORG_ID = "00D5g000007oLC8";
    public static final String DEPLOYMENT_ID = "5725g000000Pf86";
    public static final String BUTTON_ID = "5735g000000Pjc0";
    public static final String LIVE_AGENT_POD = "d.la2-c1-ukb.salesforceliveagent.com";

    ChatConfiguration chatConfiguration =
            new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
                    DEPLOYMENT_ID, LIVE_AGENT_POD)
                    .build();

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Configure a chat UI object
                ChatUI.configure(ChatUIConfiguration.create(chatConfiguration))
                        .createClient(getApplicationContext())
                        .onResult(new Async.ResultHandler<ChatUIClient>() {
                            @Override public void handleResult (Async<?> operation,
                                                                ChatUIClient chatUIClient) {

                                // Once configured, let’s start a chat session
                                chatUIClient.startChatSession(MainActivity.this);
                            }
                        });
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}