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
import com.inxs5859.liber.HelperClasses.BookRead;
import com.inxs5859.liber.HelperClasses.FirebaseRecyclerAdapterRead;
import com.inxs5859.liber.R;

import java.util.HashMap;


public class ReadFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapterRead adapter;

    public ReadFragment() {
        // Required empty public constructor
    }


    public static ReadFragment newInstance(String param1, String param2) {
        ReadFragment fragment = new ReadFragment();
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
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.read_recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //fetch username from sharedpreference
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        String userName = userDetails.get(SessionManager.KEY_USERNAME);

        //configuring firebase recycler adapter
        FirebaseRecyclerOptions<BookRead> options =
                new FirebaseRecyclerOptions.Builder<BookRead>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Read").child(userName), BookRead.class)
                        .build();

        adapter = new FirebaseRecyclerAdapterRead(options);
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