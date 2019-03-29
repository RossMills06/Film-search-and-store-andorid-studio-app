package com.example.student.filmfinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class searchFragment extends Fragment
{
    String title1 = "";
    String imageURL = "";
    String year = "";
    String contentType = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        // getting the relevant data from the activity
        title1 = getArguments().getString("TITLE");
        imageURL = getArguments().getString("IMAGE");
        year = getArguments().getString(("YEAR"));
        contentType = getArguments().getString(("TYPE"));

        TextView tvTitle = (TextView)view.findViewById(R.id.titleText);
        TextView tvYear = (TextView)view.findViewById(R.id.yearText);
        TextView tvType = (TextView)view.findViewById(R.id.typeText);
        ImageView poster = (ImageView)view.findViewById(R.id.posterView);

        tvTitle.setText("Title: " + title1);
        tvYear.setText("Year of Release: " + year);
        tvType.setText("Type: " + contentType);

        // getting the poster image using picasso and the image url
        Picasso.get().load(imageURL).into(poster);


        // https://stackoverflow.com/questions/6091194/how-to-handle-button-clicks-using-the-xml-onclick-within-fragments
        // button on click listener within fragments
        final Button button = view.findViewById(R.id.watchlistButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                watchListDatabase db = new watchListDatabase(getContext(), "watchList.db", null, 1);
                db.addFilm(title1);
                Toast.makeText(getContext(), "Added to database: " + title1, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
