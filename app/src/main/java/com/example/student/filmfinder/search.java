package com.example.student.filmfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class search extends AppCompatActivity
{

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    //called when the user clicks the search film button
    public void searchFilm(View view)
    {
        //get the search term from the text box
        EditText searchText = (EditText)findViewById(R.id.searchText);
        result = searchText.getText().toString();

        // start the search result activity and pass through search term
        Intent intent = new Intent(this, searchResult.class);
        intent.putExtra("SEARCH_RESULT", result);
        startActivity(intent);
    }
}
