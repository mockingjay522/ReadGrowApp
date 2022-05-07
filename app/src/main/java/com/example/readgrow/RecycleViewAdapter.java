package com.example.readgrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private ArrayList <Integer> testID;
    private ArrayList <String>  testEmail;
    public static ItemClickListener clickListener;


    public RecycleViewAdapter(Context context, ArrayList <Integer> _testID, ArrayList <String> _testEmail){
        inflater = LayoutInflater.from(context);
        testID = _testID;
        testEmail = _testEmail;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a long to our rows)
        //LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleview_item, parent, false);
        //Initialize inner class
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //assign values to the views we created in the recyclyer_view_row layout file
        //based on the position of the recycler view
        //hold the text, img... want to display
       ((MyViewHolder) holder).ID.setText(Integer.toString(testID.get(position)) );
        ((MyViewHolder) holder).email.setText(testEmail.get(position));
    }

    @Override
    public int getItemCount() {
        //the recycler view just wants to know the number of items you want display
        //Should be the length of the array want to display
        return testID.size();
    }
    void setClickListener (ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    //Integer getItem (int id){return testRecycle[id]}
    //Inner class
    public static class MyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        //grabbing the views from our recycler_view_row layout file
        //kinda like in the onCreate method
        TextView ID, email;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.recycleview_ID);
            email = itemView.findViewById(R.id.recycleview_Email);
            email.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if(clickListener!=null){
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    //inner Interface
    public  interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
