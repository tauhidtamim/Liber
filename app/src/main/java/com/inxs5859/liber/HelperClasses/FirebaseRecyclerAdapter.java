package com.inxs5859.liber.HelperClasses;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

//THIS RECYCLER ADAPTER IS FOR THE MAIN SHELVES- CURRENTLY READING AND WANT TO READ

public class FirebaseRecyclerAdapter extends com.firebase.ui.database.FirebaseRecyclerAdapter<Book,FirebaseRecyclerAdapter.myViewHolder> {


    public FirebaseRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Book> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, int position, @NonNull Book model) {
        holder.Title.setText(model.getTitle());
        holder.Author.setText(model.getAuthor());
        String imageLink = model.getThumbnail();
        if(!imageLink.isEmpty() && !imageLink.equals("Null")) {
            Picasso.get().load(imageLink).into(holder.Thumbnail);
        }

        //get shelf
        DatabaseReference databaseReference = getRef(position).getParent().getParent();
        final String shelf = databaseReference.getKey();

        //get user
        databaseReference = getRef(position).getParent();
        final String userName = databaseReference.getKey();

        //get book
        databaseReference = getRef(position);
        final String book = databaseReference.getKey();


        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "BOOK REMOVED FROM THE SHELF!", Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference(shelf).child(userName).child(book).removeValue();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row_design,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView Author,Title;
        ImageView Thumbnail;
        Button removeBtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Author = itemView.findViewById(R.id.author);
            Title = itemView.findViewById(R.id.title);
            Thumbnail = itemView.findViewById(R.id.img);
            removeBtn = itemView.findViewById(R.id.remove_btn);

        }
    }



}
