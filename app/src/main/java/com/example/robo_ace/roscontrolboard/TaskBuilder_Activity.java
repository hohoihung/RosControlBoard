package com.example.robo_ace.roscontrolboard;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.robo_ace.rosace.TaskListBuilderView;

import org.ros.android.RosActivity;
import org.ros.node.NodeMainExecutor;


public class TaskBuilder_Activity extends RosActivity {

    public TaskBuilder_Activity() {
        super("Task Build", "Task Build");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_builder_);
        TaskListBuilderView task_builder = (TaskListBuilderView) findViewById(R.id.taskBuilderactivityTaskbulider);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Toast.makeText(getApplicationContext(), size.toString(),
                Toast.LENGTH_LONG).show();
        task_builder.setWidth_screen(width);
        task_builder.setHeight_screen(height);



    }



    @Override
    public void init(NodeMainExecutor nodeMainExecutor) {

    }
}
