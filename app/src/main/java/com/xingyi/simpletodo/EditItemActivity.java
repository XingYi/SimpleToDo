package com.xingyi.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.xingyi.simpletodo.R.id.etItem;

public class EditItemActivity extends AppCompatActivity {

    EditText item;
    int position = 0;
    int taskId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = (EditText)findViewById(etItem);

        position = getIntent().getIntExtra("position", 0);
        String text = getIntent().getStringExtra("task");
        item.setText(text);

        taskId = getIntent().getIntExtra("taskId", 0);
        //item.setText(task.name);

        //Toast.makeText(this, "Task Id: " + taskId, Toast.LENGTH_LONG).show();
    }

    public void onSubmit(View v) {
        item = (EditText) findViewById(R.id.etItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("taskId", taskId);
        data.putExtra("task", item.getText().toString());
        data.putExtra("position", position);
        data.putExtra("code", 1); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
