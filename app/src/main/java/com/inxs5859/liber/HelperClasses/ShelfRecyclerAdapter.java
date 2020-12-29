package com.inxs5859.liber.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.inxs5859.liber.R;
import com.inxs5859.liber.User.CurrentlyReadingFragment;
import com.inxs5859.liber.User.ReadFragment;
import com.inxs5859.liber.User.ToReadFragment;

import java.util.ArrayList;

public class ShelfRecyclerAdapter extends RecyclerView.Adapter<ShelfRecyclerAdapter.myViewHolder>{

    ArrayList <ShelfModel> shelfHolder;

    public ShelfRecyclerAdapter(ArrayList<ShelfModel> shelfHolder) {
        this.shelfHolder = shelfHolder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelf_design,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {

        holder.imageView.setImageResource(shelfHolder.get(position).getImage());
        holder.header.setText(shelfHolder.get(position).getHeader());
        holder.subHeader.setText(shelfHolder.get(position).getDesc());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = holder.getAdapterPosition();
                AppCompatActivity appCompatActivity = (AppCompatActivity)view.getContext();
                //System.out.println(position);
                if(position==0){
                    appCompatActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new ReadFragment()).addToBackStack(null).commit();
                }else if(position==1){
                    appCompatActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new CurrentlyReadingFragment()).addToBackStack(null).commit();
                }else{
                    appCompatActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new ToReadFragment()).addToBackStack(null).commit();
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return shelfHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView header, subHeader;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setClickable(true);

            imageView = itemView.findViewById(R.id.img);
            header = itemView.findViewById(R.id.heading);
            subHeader = itemView.findViewById(R.id.sub_heading);
        }
    }

}
