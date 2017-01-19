package com.xingyi.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Xing Yi on 18/1/2017.
 * Reference: http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }
        // Lookup view for data population
        TextView tvTask = (TextView) convertView.findViewById(R.id.tvTask);
        TextView tvDateCreated = (TextView) convertView.findViewById(R.id.tvDateCreated);
        // Populate the data into the template view using the data object
        tvTask.setText(task.name);

        //Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String formattedDate = df.format(c.getTime());
        //Log.v("Date Created: ", Long.toString(task.dateCreated) );
        String formattedDate = df.format(task.dateCreated *1000);

        tvDateCreated.setText(formattedDate);
        // Return the completed view to render on screen
        return convertView;
    }

}
