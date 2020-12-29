package com.inxs5859.liber.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.HelperClasses.ShelfModel;
import com.inxs5859.liber.HelperClasses.ShelfRecyclerAdapter;
import com.inxs5859.liber.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MyBooksFragment extends Fragment {

    String fullName;
    TextView userFullName;
    RecyclerView recyclerView;
    ArrayList <ShelfModel> shelfHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_books, container, false);

        //fetch user's full name from shared preference
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        fullName+="!";

        //hooks
        userFullName = root.findViewById(R.id.full_name);
        recyclerView = root.findViewById(R.id.recView);

        //set user's full name to text view
        userFullName.setText(fullName);

        //recycler view works
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shelfHolder = new ArrayList<>();

        ShelfModel read = new ShelfModel(R.drawable.bookshelf,"Read","0 Books");
        ShelfModel currentlyReading = new ShelfModel(R.drawable.ic_reading_person,"Currently Reading","0 Books");
        ShelfModel wantToRead = new ShelfModel(R.drawable.ic_search_books,"Want To Read","0 Books");

        shelfHolder.add(read);
        shelfHolder.add(currentlyReading);
        shelfHolder.add(wantToRead);

        recyclerView.setAdapter(new ShelfRecyclerAdapter(shelfHolder));


        return root;
    }
}