package com.example.student.filmfinder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import android.net.Uri;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MainActivity extends AppCompatActivity
{
    //create firebase class object
    firebase fb = new firebase();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the image from firebase and load it into the imageview
        fb.downloadImage();
        Bitmap loadBM = BitmapFactory.decodeFile("/data/data/com.example.student.filmfinder/files/firebaseDownload.jpg");
        ImageView profilepic = findViewById(R.id.profilePic);
        profilepic.setImageBitmap(loadBM);
        profilepic.setRotation(90);
        Toast toast = Toast.makeText(this, "Image downloaded from firebase!", Toast.LENGTH_LONG);
        toast.show();
    }

    // Called when the user selects the Collection button
    public void collectionAct(View view)
    {
        Intent intent = new Intent(this, collection.class);
        startActivity(intent);
    }

    // Called when the user selects the Watch List button
    public void watchlistAct(View view)
    {
        Intent intent = new Intent(this, watchlist.class);
        startActivity(intent);
    }

    // Called when the user selects the find a Film button
    public void findAct(View view)
    {
        Intent intent = new Intent(this, search.class);
        startActivity(intent);
    }

    public void runPermissions(View view)
    {
        Intent intent = new Intent(this, Permissions.class);
        startActivity(intent);
    }

    // Called when the user selects the Cinema button
    public void cinemaAct(View view)
    {
        LocationManager locationManager;
        String locationProvider;
        Location lastKnownLocation = null;

        try
        {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationProvider = LocationManager.GPS_PROVIDER;
            lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            double lat = 0;
            double longi = 0;

            // check if no last location exists, set to lincoln as default
            if (lastKnownLocation == null)
            {
                lat = 53.228029;
                longi = -0.546055;
            }
            else
            {
                lat = lastKnownLocation.getLatitude();
                longi = lastKnownLocation.getLongitude();
            }

            // Search for cinemas nearby
            Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + longi + "?q=cinema");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        catch (Exception e)
        {
            Toast toast = Toast.makeText(this, "Check Location Permissions!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    //code for the profile picture feature
    int REQUEST_IMAGE_CAPTURE = 1;

    // called when the user clicks the edit button
    public void changeProfilePic(View view)
    {
        try
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        catch(Exception e)
        {
            Toast toast = Toast.makeText(this, "Get Camera Permission First", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView = findViewById((R.id.profilePic));
            mImageView.setImageBitmap(imageBitmap);
            mImageView.setRotation(90);
            // get the image and apply it to the image view

            // saving the profile pic to a file
            try
            {
                FileOutputStream fos = openFileOutput("profilepic.jpg", MODE_PRIVATE);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();

                // Toast toast = Toast.makeText(this, "Image saved!", Toast.LENGTH_LONG);
                // toast.show();

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            //upload the image to firebase storage
            fb.uploadImage("/data/data/com.example.student.filmfinder/files/profilepic.jpg");
            Toast toast = Toast.makeText(this, "Image uploaded to firebase!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
