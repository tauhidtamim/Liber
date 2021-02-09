package com.inxs5859.liber.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.HelperClasses.ShelfModel;
import com.inxs5859.liber.HelperClasses.ShelfRecyclerAdapter;
import com.inxs5859.liber.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MyBooksFragment extends Fragment {

    String fullName,userName;
    TextView userFullName;
    RecyclerView recyclerView;
    ArrayList <ShelfModel> shelfHolder;
    long readCount,toReadCount,currentReadCount;
    ShelfRecyclerAdapter adapter;
    ShelfModel read, currentRead, toRead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_books, container, false);

        //fetch user's full name from shared preference
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        userName = userDetails.get(SessionManager.KEY_USERNAME);
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


        read = new ShelfModel(R.drawable.bookshelf,"Read","0 Books");
        currentRead = new ShelfModel(R.drawable.ic_reading_person,"Currently Reading","0 Books");
        toRead = new ShelfModel(R.drawable.ic_search_books,"Want To Read","0 Books");

        shelfHolder.add(read);
        shelfHolder.add(currentRead);
        shelfHolder.add(toRead);
        adapter = new ShelfRecyclerAdapter(shelfHolder);
        recyclerView.setAdapter(adapter);


        Query databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Read");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shelfHolder.clear();
                readCount = snapshot.child(userName).getChildrenCount();
                String desc1 = Long.toString(readCount-1);
                String desc2 = Long.toString(currentReadCount-1);
                String desc3 = Long.toString(toReadCount-1);
                desc1 = desc1+" "+"Books";
                desc2 = desc2+" "+"Books";
                desc3 = desc3+" "+"Books";

                read = new ShelfModel(R.drawable.bookshelf,"Read",desc1);
                currentRead = new ShelfModel(R.drawable.ic_reading_person,"Currently Reading",desc2);
                toRead = new ShelfModel(R.drawable.ic_search_books,"Want To Read",desc3);

                shelfHolder.add(read);
                shelfHolder.add(currentRead);
                shelfHolder.add(toRead);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Current");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shelfHolder.clear();
                currentReadCount = snapshot.child(userName).getChildrenCount();
                String desc1 = Long.toString(readCount-1);
                String desc2 = Long.toString(currentReadCount-1);
                String desc3 = Long.toString(toReadCount-1);
                desc1 = desc1+" "+"Books";
                desc2 = desc2+" "+"Books";
                desc3 = desc3+" "+"Books";

                read = new ShelfModel(R.drawable.bookshelf,"Read",desc1);
                currentRead = new ShelfModel(R.drawable.ic_reading_person,"Currently Reading",desc2);
                toRead = new ShelfModel(R.drawable.ic_search_books,"Want To Read",desc3);

                shelfHolder.add(read);
                shelfHolder.add(currentRead);
                shelfHolder.add(toRead);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("ToRead");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shelfHolder.clear();
                toReadCount = snapshot.child(userName).getChildrenCount();
                String desc1 = Long.toString(readCount-1);
                String desc2 = Long.toString(currentReadCount-1);
                String desc3 = Long.toString(toReadCount-1);
                desc1 = desc1+" "+"Books";
                desc2 = desc2+" "+"Books";
                desc3 = desc3+" "+"Books";

                read = new ShelfModel(R.drawable.bookshelf,"Read",desc1);
                currentRead = new ShelfModel(R.drawable.ic_reading_person,"Currently Reading",desc2);
                toRead = new ShelfModel(R.drawable.ic_search_books,"Want To Read",desc3);

                shelfHolder.add(read);
                shelfHolder.add(currentRead);
                shelfHolder.add(toRead);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }
}