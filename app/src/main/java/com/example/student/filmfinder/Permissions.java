package com.example.student.filmfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Permissions extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
    }

    // Called when the user clicks the Camera Permissions button
    public void cameraPermissions(View view)
    {
        ActivityCompat.requestPermissions(Permissions.this, new String[]{Manifest.permission.CAMERA}, 1);
    }

    // Called when the user clicks the location permissions button
    public void locationPermissions(View view)
    {
        ActivityCompat.requestPermissions(Permissions.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    // handles the users permission options
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(Permissions.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                }
                else {

                }
                return;
            }
        }
    }

}
