package com.example.robo_ace.roscontrolboard;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.robo_ace.rosace.NotificationView;
import com.example.robo_ace.rosace.RosSliderView;
import com.example.robo_ace.rosace.StatusView;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import std_msgs.Int32;


public class CleanE_ControlBoard extends RosActivity {


    private NotificationView notifications;



    private StatusView stat;


    public CleanE_ControlBoard() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("CleanE Tutorial", "CleanE Tutorial");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clean_e__control_board);
        stat = (StatusView) findViewById(R.id.clean_e_statView);



        notifications = (NotificationView) findViewById(R.id.clean_e_notificationPubSub);
        notifications.setTopicName("/cleanE/notifications");
        stat.setToRobotTopicName("/cleanE/Controls");
        stat.setFromRobotTopicName("/cleanE/BatteryStatus");


    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());
        nodeMainExecutor
                .execute(stat, nodeConfiguration.setNodeName("clean_e_statusNode"));
        nodeMainExecutor
                .execute(notifications, nodeConfiguration.setNodeName("clean_e_notificationNode"));



    }

    public void cleaneDashboardOnClick(View v){
        Intent activity_task_builder = new Intent(this,TaskBuilder_Activity.class);


        switch (v.getId()){
            case R.id.clean_e_button1:
                Toast.makeText(getApplicationContext(), "Clicked on Button",
                        Toast.LENGTH_LONG).show();
                onDestroy();
                startActivity(activity_task_builder);
                break;
            default:
                break;

        }

    }
}
