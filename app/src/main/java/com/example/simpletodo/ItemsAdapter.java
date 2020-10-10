package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for displaying data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.Viewholder>{

    public interface OnLongClickListener {
        void OnItemLongClicked(int position); //notify the adapter of the adapter of where would we delete
    }

    List<String> items; // passing in the items in to item Adapter
    OnLongClickListener longClickListener; // passing in the long click for deleting items purposes

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // creating holders
        // use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a view holder and return it
        return new Viewholder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) { //taking data from position to Viewholder
    // Grab  the item at the position
        String item = items.get(position);
    // Bind the item into the specified view holder
        holder.bind(item);
    }
    //R esponsible for binding data to particular view holder
    //Tells the recycler view how many item are in the list
    @Override
    public int getItemCount() { // items available in the data
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class Viewholder extends RecyclerView.ViewHolder{

        TextView tvItem; // defined text view item
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1); //declared as a view pasted inside view holder
        }
        // Update the view inside of the viewholder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                // remove item from the recycler view
                    // Notify the listener which position was long pressed.
                    longClickListener.OnItemLongClicked(getAdapterPosition());
                    return true; // make true so it can consume the long click
                }
            });
        }
    }
}
