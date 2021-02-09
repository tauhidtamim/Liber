package com.inxs5859.liber.User;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inxs5859.liber.HelperClasses.BookRead;
import com.inxs5859.liber.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class AddBooksOnlyReadFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String Title, Author, Thumbnail, Review, Date, userName;
    ImageView thumbnail;
    TextView textView_author, textView_title;
    EditText datePicker;
    RatingBar ratingBar_review;
    Button button;

    public AddBooksOnlyReadFragment() {
        // Required empty public constructor
    }

    public AddBooksOnlyReadFragment(String title, String author, String thumbnail, String _username) {
        Title = title;
        Author = author;
        Thumbnail = thumbnail;
        userName = _username;
    }


    public static AddBooksOnlyReadFragment newInstance(String param1, String param2) {
        AddBooksOnlyReadFragment fragment = new AddBooksOnlyReadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_books_only_read, container, false);

        thumbnail = view.findViewById(R.id.img);
        textView_title = view.findViewById(R.id.title);
        textView_author = view.findViewById(R.id.author);
        ratingBar_review = view.findViewById(R.id.ratingBar);
        datePicker = view.findViewById(R.id.etDate);
        button = view.findViewById(R.id.add_btn);

        textView_title.setText(Title);
        textView_author.setText(Author);
        if(!Thumbnail.isEmpty()) {
            Picasso.get().load(Thumbnail).into(thumbnail);
        }

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month + 1;
                                Date = dayOfMonth+"/"+month+"/"+year;
                                datePicker.setText(Date);
                            }
                        },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Review = String.valueOf(Math.round(ratingBar_review.getRating()));

                if(Review.isEmpty()){
                    Toast.makeText(getContext(), "PLEASE GIVE A RATING!", Toast.LENGTH_LONG).show();
                    return;
                }else if(Date.isEmpty()){
                    Toast.makeText(getContext(), "PLEASE SET YOUR READ DATE!", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Read").child(userName);

                BookRead bookRead = new BookRead(Author,Title,Thumbnail,Review,Date);
                databaseReference.child(Title+" & "+Author).setValue(bookRead);
                Toast.makeText(getContext(), "BOOK ADDED TO YOUR READ SHELF", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        return view;
    }

    public void onBackPressed(){

        AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new SearchFragment())
                .commit();

    }
}