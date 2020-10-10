package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItem = findViewById(R.id.rvItems);

        loadItems(); // runs the code once when app starts up

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){ // defining the items on long click listener
            @Override
            public void OnItemLongClicked(int position) {
                //delete item from model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed!!", Toast.LENGTH_SHORT).show(); // inform user item removed
                saveItems(); // saving Items after every run
            }
        };
        //removed final ItemsAdapter defined globally
        itemsAdapter = new ItemsAdapter(items, onLongClickListener); //taking in item and long click listener parameter
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager( this));

         btnAdd.setOnClickListener(new View.OnClickListener() { // do command on action
             @Override
             public void onClick(View v) { // command action
                 String todoItem = etItem.getText().toString();
                 //Add item to the model
                 items.add(todoItem);
                 //Notify adapter that an item is inserted
                 itemsAdapter.notifyItemInserted(items.size() - 1);
                 etItem.setText(""); //clear the edit text 
                 Toast.makeText(getApplicationContext(), "Item was added!!", Toast.LENGTH_SHORT).show(); // Inform user item added
                 saveItems(); // saves Item after every run
             }
         });
    }
    // private because only used in main activity
    private File getDataFile(){ //returns a file
        return new File(getFilesDir(), "data.txt"); // passing in the dir of this app first then passing in the name of the file
    }

    //This function will load items by reading every line of the data file ".txt"
    private void loadItems(){
        // tries to execute the line of code
        try {
            // using apache common library not android Files Utils
            items = new ArrayList<>(org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())); // Array list will be the contents of our data files
        }catch (IOException e) { // If IO error handle in this catch block
            Log.e("MainACtivity", "error reading items", e); // e is the exception
            items = new ArrayList<>(); // if error set as empty array list to start off with
        }
    }
    //This function saves items by writing them into the data file
    private void saveItems(){
        // using apache common library to use File Utils
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), items);
        }catch (IOException e){ // logging in the error
            Log.e("MainACtivity", "error writing items", e); // e is the exception
        }
    }
}