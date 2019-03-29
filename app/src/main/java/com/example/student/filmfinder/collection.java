package com.example.student.filmfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class collection extends AppCompatActivity
{
    String[] collectionArray = new String[15];

    String outputString = "";
    String collectionString = "";

    ListView collectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        collectionList = findViewById((R.id.collectionList));

        // get data back from the shared preferences
        SharedPreferences collectionSave = getSharedPreferences("userCollection", Context.MODE_PRIVATE);

        // update the string with the shared preferences data
        collectionString = collectionSave.getString("collection", "");
        // split the string into an array
        String[] inputArray = collectionString.split(",");

        // remapping input values to the collection array
        for (int i = 0; i < inputArray.length; i ++)
        {
            collectionArray[i] = inputArray[i];
        }

        // outputting the values for the array
        outputString = "";
        for (int i = 0; i < 15; i++)
        {
            if (collectionArray[i] != null)
            {
                outputString =  outputString + collectionArray[i] + ",";
            }
            else
            {
                collectionArray[i] = "";
            }
        }

        // converting array to arraylist
        ArrayList<String> collectionArrayList = new ArrayList<>(Arrays.asList(collectionArray));

        //creating array adapter for the list view
        ArrayAdapter<String> collectionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, collectionArrayList);
        collectionList.setAdapter(collectionAdapter);
    }

    public void addButton(View view)
    {
        // add the new addition to the collection string
        EditText addText = (EditText) findViewById(R.id.addText);
        //collectionString = collectionString + addText.getText().toString() + "   |   ";

        // adding the value to the array
        for (int i = 0; i < 15; i++)
        {
            if (collectionArray[i] == "")
            {
                collectionArray[i] = addText.getText().toString();
                break;
            }
        }

        // outputting the values for the array
        outputString = "";
        for (int i = 0; i < 15; i++)
        {
            if (collectionArray[i] != null)
            {
                outputString =  outputString + collectionArray[i] + ",";
            }
            else
            {
                collectionArray[i] = "";
            }
        }

        // converting array to arraylist
        ArrayList<String> collectionArrayList = new ArrayList<>(Arrays.asList(collectionArray));

        //creating array adapter for the list view
        ArrayAdapter<String> collectionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, collectionArrayList);
        collectionList.setAdapter(collectionAdapter);

        //open shared preferences
        SharedPreferences collectionSave = getSharedPreferences("userCollection", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = collectionSave.edit();

        // add the collection string to the shared preferences
        editor.putString("collection", outputString);
        editor.commit();
    }
}



