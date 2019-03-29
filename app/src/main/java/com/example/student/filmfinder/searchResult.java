package com.example.student.filmfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// add below
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

import java.security.Key;
import java.util.ArrayList;

import android.util.Base64;
import android.view.View;
import android.widget.*;

import java.util.Arrays;
import java.util.Date;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class searchResult extends AppCompatActivity
{
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> imageURL = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> contentType = new ArrayList<>();

    String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // get the data from the search activity
        searchKey = getIntent().getStringExtra("SEARCH_RESULT");

        JSONparse();
    }

    public void JSONparse()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.omdbapi.com/?s=" + searchKey + "&apikey=c858a229";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    // Argument Method for Response
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            // get the list view
                            final ListView outputList = findViewById(R.id.searchList);

                            // get just the search json array from the api call
                            JSONArray jsonArray = response.getJSONArray("Search");
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                // add the title of the film to the arraylist
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                titles.add(jsonObject.getString("Title"));
                                imageURL.add(jsonObject.getString("Poster"));
                                year.add(jsonObject.getString("Year"));
                                contentType.add(jsonObject.getString("Type"));
                            }

                            // output the list to the list view
                            ArrayAdapter<String> outputArrayAdapter = new ArrayAdapter<>(searchResult.this, android.R.layout.simple_expandable_list_item_1, titles);
                            outputList.setAdapter(outputArrayAdapter);

                            // onclick listener to open fragment showing more info
                            outputList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                                {

                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                    Bundle fragmentData = new Bundle();
                                    fragmentData.putString("TITLE", titles.get(position));
                                    fragmentData.putString("YEAR", year.get(position));
                                    fragmentData.putString("IMAGE", imageURL.get(position));
                                    fragmentData.putString("TYPE", contentType.get(position));

                                    searchFragment fragment = new searchFragment();
                                    fragmentTransaction.add(R.id.fragmentContainer, fragment);

                                    fragment.setArguments(fragmentData);

                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            });

                            // Save the data to local storage
                            String cacheSaveString = "";
                            for (int i = 0; i < titles.size(); i ++ )
                            {
                                cacheSaveString = cacheSaveString + titles.get(i) + "/ ";
                            }

                            //open shared preferences
                            SharedPreferences collectionSave = getSharedPreferences("APIcache", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = collectionSave.edit();
                            editor.putString("APIcache", cacheSaveString);
                            editor.commit();

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(searchResult.this, "Error retrieving results! ", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // OUTPUT LOCAL DATA IF NO INTERNET CONNECTION
                        Toast toast = Toast.makeText(searchResult.this, "No internet connection, cached data loaded!", Toast.LENGTH_LONG);
                        toast.show();
                        // get the list view
                        final ListView outputList = findViewById(R.id.searchList);

                        SharedPreferences APIsave = getSharedPreferences("APIcache", Context.MODE_PRIVATE);
                        String APIcachestring = APIsave.getString("APIcache", "");
                        String[] cacheArray = APIcachestring.split("/ ");

                        ArrayList<String> cacheArrayList = new ArrayList<>((Arrays.asList(cacheArray)));
                        //creating array adapter for the list view
                        ArrayAdapter<String> cacheAdapter = new ArrayAdapter<>(searchResult.this, android.R.layout.simple_list_item_1, cacheArrayList);
                        outputList.setAdapter(cacheAdapter);
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}


