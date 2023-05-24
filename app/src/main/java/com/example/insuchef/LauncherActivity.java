package com.example.insuchef;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class LauncherActivity extends AppCompatActivity {

    private GetData get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        File jFile = new File(getFilesDir(), "food.json");

        // Fetch/Parse JSON data and create static FoodList object
        try {
            get = new GetData(jFile, this);
            get.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
