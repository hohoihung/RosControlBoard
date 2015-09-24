package com.example.robo_ace.rosace;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.view.Display;
import android.graphics.Point;
import android.view.WindowManager;
import android.content.res.Configuration;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;

import java.util.ArrayList;
import java.util.Stack;

import std_msgs.Int8;

/**
 * Created by robo-ace on 7/10/15.
 */
public class TaskListBuilderView extends RelativeLayout implements MessageListener<Int8>, NodeMain, OnItemClickListener {

    private ListView lv1 ;
    private ListView lv2 ;
    private TaskItem tasks_ ;
    private String[] allTasks = new String[10];
    private Stack<Short> ChosenTasksCodes = new Stack();
    private int width_screen;
    private int height_screen;

    public void setWidth_screen(int width_screen) {
        this.width_screen = width_screen;
    }

    public void setHeight_screen(int height_screen) {
        this.height_screen = height_screen;
    }

    public TaskListBuilderView(Context context) {
        super(context);
        this.initTaskBuilderView(context);
    }

    public TaskListBuilderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initTaskBuilderView(context);
    }

    public TaskListBuilderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initTaskBuilderView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TaskListBuilderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initTaskBuilderView(context);
    }

    public void initTaskBuilderView(Context context){
        LayoutInflater.from(context).inflate(R.layout.task_builder_view, this, true);


        lv1 = (ListView) findViewById(R.id.ros_first_list);
        lv2 = (ListView) findViewById(R.id.ros_second_list);

        lv1.setLayoutParams(new LinearLayout.LayoutParams(600, 400));
        lv2.setLayoutParams(new LinearLayout.LayoutParams(600, 400));





        init_all_task_list();
        Stack<Short> allTasksCodes = new Stack();
        for(short i = 0 ; i < 10 ; i++){
            allTasksCodes.add(i);
        }

        ArrayList<TaskItem> image_details = getListData(allTasksCodes);



        lv1.setAdapter(new RosCustomListAdapter(this.getContext(), image_details));

        lv1.setOnItemClickListener(this);

    }

    private void init_all_task_list(){
        allTasks[0] = "Clean the bedroom 1";
        allTasks[1] = "Clean the Kitchen";
        allTasks[2] = "Clean the Living room";
        allTasks[3] = "Clean the Drawing room";
        allTasks[4] = "Clean the hall way";
        allTasks[5] = "Re-charge";
        allTasks[6] = "Pick up item in kitchen";
        allTasks[7] = "Clean the bathroom";
        allTasks[8] = "Check the lights";
        allTasks[9] = "Empty the dirt bin";


    }


    @Override
    public void onNewMessage(Int8 int8) {

    }

    @Override
    public GraphName getDefaultNodeName() {
        return null;
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {




    }

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<TaskItem> results2 = new ArrayList<TaskItem>();
        tasks_ = (TaskItem) lv1.getItemAtPosition(position);

        ChosenTasksCodes.remove(Short.valueOf(tasks_.getTask_id()));
        ChosenTasksCodes.push(Short.valueOf(tasks_.getTask_id()));

        lv2.setAdapter(new RosCustomListAdapter(this.getContext(), getListData(ChosenTasksCodes)));

    }

    private ArrayList<TaskItem> getListData(Stack<Short> i_c) {
        ArrayList<TaskItem> results = new ArrayList<TaskItem>();

        for (int i : i_c){
            TaskItem taskData = new TaskItem();
            Log.d("lolros", allTasks[i]);
            taskData.setTask_name(allTasks[i]);
            taskData.setTask_id(String.valueOf(i));
            results.add(taskData);
        }
        return results;
    }
}
