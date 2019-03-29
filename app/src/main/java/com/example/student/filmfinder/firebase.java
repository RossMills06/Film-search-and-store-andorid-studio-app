package com.example.student.filmfinder;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class firebase
{
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    // private StorageReference mStorageRef = storage.getReferenceFromUrl("gs://filmfinder-5751d.appspot.com");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public void uploadImage(String path)
    {
        try
        {
            Uri file = Uri.fromFile(new File(path));
            StorageReference imageRef = mStorageRef.child("images/profilePic.jpg");

            imageRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        catch (Exception e)
        {

        }
    }

    public void downloadImage()
    {
        File localFile = new File("/data/data/com.example.student.filmfinder/files/firebaseDownload.jpg");

        StorageReference imageRef = mStorageRef.child("images/profilePic.jpg");
        imageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                    {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

    }

}
