package com.inxs5859.liber.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.HelperClasses.Book;
import com.inxs5859.liber.HelperClasses.FirebaseRecyclerAdapter;
import com.inxs5859.liber.R;

import java.util.HashMap;


public class CurrentlyReadingFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;

    public CurrentlyReadingFragment() {
        // Required empty public constructor
    }


    public static CurrentlyReadingFragment newInstance(String param1, String param2) {
        CurrentlyReadingFragment fragment = new CurrentlyReadingFragment();
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

        View view = inflater.inflate(R.layout.fragment_currently_reading, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.currently_reading_recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //fetch username from sharedpreference
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String userName = userDetails.get(SessionManager.KEY_USERNAME);

        //configuring firebase recycler adapter
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Current").child(userName), Book.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter(options);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}