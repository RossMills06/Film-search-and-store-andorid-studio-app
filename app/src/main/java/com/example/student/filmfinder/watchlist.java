package com.example.student.filmfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class watchlist extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        // create the database object
        watchListDatabase db = new watchListDatabase(watchlist.this, "watchList.db", null, 1);

        // get the database returned into a list view
        ArrayList<String> watchListArrayList = db.databasetoArrayList();
        // String watchlistString = db.databaseToString();

        //output watch list to the list view
        ListView outputList = findViewById(R.id.watchListList);
        ArrayAdapter<String> watchListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, watchListArrayList);
        outputList.setAdapter(watchListAdapter);
    }
}
