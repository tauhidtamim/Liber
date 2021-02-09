package com.inxs5859.liber.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.inxs5859.liber.R;
import com.inxs5859.liber.User.AddBooksFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//THIS ADAPTER IS FOR THE SEARCH RECYCLERVIEW

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    //creating variables for arraylist and context.
    private ArrayList<BookInfo> bookInfoArrayList;
    private Context mcontext;

    //creating constructor for array list and context.
    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context mcontext) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelf_design_search, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        //inside on bind view holder method we are setting ou data to each UI component.
        final BookInfo bookInfo = bookInfoArrayList.get(position);
        holder.bookTitle.setText(bookInfo.getmTitle());
        holder.authorName.setText(bookInfo.getmAuthors());
        //below line is use to set image from URL in our image view.
        final String imageLink = bookInfo.getmThumbnail();
        if(!imageLink.isEmpty()) {
            Picasso.get().load(bookInfo.getmThumbnail()).into(holder.bookThumbnail);
        }

        //below line is use to add on click listner for our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new AddBooksFragment(bookInfo.getmTitle(), bookInfo.getmAuthors(), imageLink)).addToBackStack(null).commit();

            }
        });
    }


    @Override
    public int getItemCount() {

        //inside get item count method we are returning the size of our array list.
        return bookInfoArrayList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        //below line is use to initialize our text view and image views.
        TextView bookTitle, authorName;
        ImageView bookThumbnail;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.heading);
            authorName = itemView.findViewById(R.id.sub_heading);
            bookThumbnail = itemView.findViewById(R.id.img);


        }
    }
}
