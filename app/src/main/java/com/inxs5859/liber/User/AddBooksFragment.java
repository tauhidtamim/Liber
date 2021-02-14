package com.inxs5859.liber.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.service.quicksettings.Tile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.HelperClasses.Book;
import com.inxs5859.liber.HelperClasses.BookRead;
import com.inxs5859.liber.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class AddBooksFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    String Title, Author, Thumbnail, userName;
    FirebaseDatabase rootNode;

    public AddBooksFragment() {
        // Required empty public constructor
    }

    public AddBooksFragment(String _title, String _author, String _thumbnail) {
        this.Title = _title;
        this.Author = _author;
        this.Thumbnail = _thumbnail;
    }


    public static AddBooksFragment newInstance(String param1, String param2) {
        AddBooksFragment fragment = new AddBooksFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_books, container, false);

        TextView textView_title = view.findViewById(R.id.title);
        TextView textView_author = view.findViewById(R.id.author);
        ImageView imageView = view.findViewById(R.id.img);
        Button readBtn, currentBtn, toReadBtn;
        readBtn = view.findViewById(R.id.read_button);
        currentBtn = view.findViewById(R.id.curread_button);
        toReadBtn = view.findViewById(R.id.toread_button);

        textView_title.setText(Title);
        textView_author.setText(Author);
        if(!Thumbnail.isEmpty()) {
            Picasso.get().load(Thumbnail).into(imageView);
        }

        //retrieve username
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        userName = userDetails.get(SessionManager.KEY_USERNAME);

        rootNode = FirebaseDatabase.getInstance();


        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go to review and date add fragment
                AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new AddBooksOnlyReadFragment(Title,Author,Thumbnail, userName))
                        .addToBackStack(null).commit();

            }
        });

        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = Title + " & " + Author;
                //removing whitespaces
                key = key.replaceAll("\\s+", "");
                //removing some characters because firebase paths must not contain these
                key = key.replace(".","");
                key = key.replace("#","");
                key = key.replace("$","");
                key = key.replace("[","");
                key = key.replace("]","");

                //add cur-read book to cur-read shelf
                Book book = new Book(Author,Title,Thumbnail);
                DatabaseReference reference = rootNode.getReference("Current").child(userName);
                reference.child(key).setValue(book);
                Toast.makeText(getContext(), "BOOK ADDED TO YOUR CURRENTLY READING SHELF", Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

        toReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = Title + " & " + Author;
                //removing whitespaces
                key = key.replaceAll("\\s+", "");
                //removing some characters because firebase paths must not contain these
                key = key.replace(".","");
                key = key.replace("#","");
                key = key.replace("$","");
                key = key.replace("[","");
                key = key.replace("]","");

                //add to-read book to to-read shelf
                Book book = new Book(Author,Title,Thumbnail);
                DatabaseReference reference = rootNode.getReference("ToRead").child(userName);
                reference.child(key).setValue(book);
                Toast.makeText(getContext(), "BOOK ADDED TO YOUR WANT TO READ SHELF", Toast.LENGTH_LONG).show();
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