package com.xingyi.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ArrayList<String> items;

    // Construct the data source
    ArrayList<Task> items;

    //ArrayAdapter<String> itemsAdapter;
    TaskAdapter itemsAdapter;
    ListView lvItems;

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        //items = new ArrayList<>();
        items = new ArrayList<Task>();

        //readItems();
        readItemsFromDb();

        //itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // Create the adapter to convert the array to views
        itemsAdapter = new TaskAdapter(this, items);

        lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        //items.add("Second Item");

        // Attach the adapter to a ListView
        //ListView listView = (ListView) findViewById(lvItems);
        //listView.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        //itemsAdapter.add(itemText);

        long unixTime = System.currentTimeMillis() / 1000L;

        Task newTask = new Task();
        newTask.setName(itemText);
        newTask.setDateCreated(unixTime);
        items.add(newTask);

        etNewItem.setText("");

        //writeItems();
        writeItemsToDb(itemText, unixTime);

        //Read again to retrieve the auto-incremented task id
        itemsAdapter.clear();
        readItemsFromDb();
    }

    public void setupListViewListener(){

        //Deletion
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){

                        Task task = (Task)items.get(pos);
                        deleteItemsFromDb(task.id);

                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        //writeItems();
                        //deleteItemsFromDb(pos);
                        Toast.makeText(getApplicationContext(), task.name + " has been deleted.", Toast.LENGTH_LONG).show();

                        return true;
                    }
                });

        //Edit
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
                        //Log.v("On click: ", "True");
                        // first parameter is the context, second is the class of the activity to launch
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        // put "extras" into the bundle for access in the second activity
                        i.putExtra("position", pos);
                        //i.putExtra("task", itemsAdapter.getItem(pos).toString());

                        Task task = new Task();
                        task = items.get(pos);
                        i.putExtra("taskId", task.id);
                        i.putExtra("task", task.name);

                        // brings up the second activity
                        startActivityForResult(i, REQUEST_CODE);
                    }

                }
        );
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            int taskId = data.getExtras().getInt("taskId", 0);
            String task = data.getExtras().getString("task");
            int position = data.getExtras().getInt("position", 0);
            int code = data.getExtras().getInt("code", 0);

            long unixTime = System.currentTimeMillis() / 1000L;
            Task newTask = new Task();
            newTask.setId(taskId);
            newTask.setName(task);
            newTask.setDateCreated(unixTime);

            //Update the item
            //items.set(position, task);
            items.set(position, newTask);

            //Refresh the listview
            itemsAdapter.notifyDataSetChanged();

            //writeItems();
            writeItemsToDb( newTask.name, unixTime, taskId );

            //Toast.makeText(this, "Task has been edited successfully.", Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "Position: " + position + " Task Id: " + taskId, Toast.LENGTH_LONG).show();
        }
    }

    /*
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    */

    private void readItemsFromDb(){
        // Query all tasks
        List<Task> itemList = SQLite.select().
                from(Task.class).queryList();

        for (Task task : itemList) {
            // 1 - can call methods of element
            Log.v("Task Id Loop: ", Long.toString(task.id) );
            // ...
            // Add item to adapter
            Task newTask = new Task();
            newTask.setId( task.id );
            newTask.setName( task.name.toString() );
            newTask.setDateCreated( task.dateCreated );

            //items.add(task.name.toString());
            items.add(newTask);
        }
    }

    private void writeItemsToDb(String taskName, long unixTime){
        // Create task
        Task task = new Task();
        task.setName(taskName);
        task.setDateCreated(unixTime);
        task.save(); // checks if exists, if true update, else insert.
    }

    //Method overloading
    private void writeItemsToDb(String taskName, long unixTime, int id){
        // Create task
        Task task = new Task();
        task.setId(id);
        task.setName(taskName);
        task.setDateCreated(unixTime);
        task.save();
    }

    private void deleteItemsFromDb(int id){
        Task task = new Task();
        task.setId(id);
        task.delete();
    }
}
