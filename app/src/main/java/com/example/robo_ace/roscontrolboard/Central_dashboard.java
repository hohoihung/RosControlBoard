package com.example.robo_ace.roscontrolboard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.robo_ace.rosace.NotificationView;
import com.example.robo_ace.rosace.StatusView;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;


public class Central_dashboard extends RosActivity {

    private NotificationView notifications;



    private StatusView stat;
    private StatusView stat1;


    public Central_dashboard() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("Central Tutorial", "Central Tutorial");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_central_dashboard);
        stat = (StatusView) findViewById(R.id.central_clean_e_statView);
        stat1 = (StatusView) findViewById(R.id.central_walk_e_statView);



        notifications = (NotificationView) findViewById(R.id.central_notificationPubSub);
        notifications.setTopicName("/walkE/notifications");
        stat.setToRobotTopicName("/walkE/Controls");
        stat.setFromRobotTopicName("/walkE/BatteryStatus");

        stat1.setToRobotTopicName("/walkE/Controls");
        stat1.setFromRobotTopicName("/walkE/BatteryStatus");
        stat1.setIconImage(R.drawable.walke);


    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());
        nodeMainExecutor
                .execute(stat, nodeConfiguration.setNodeName("central_statusNode"));

        nodeMainExecutor
                .execute(stat1, nodeConfiguration.setNodeName("central_statusNode"));
        nodeMainExecutor
                .execute(notifications, nodeConfiguration.setNodeName("central_notificationNode"));



    }

    public void centralDashboardOnClick(View v){
        Intent activity_clean_e = new Intent(this,CleanE_ControlBoard.class);
        Intent activity_walk_e = new Intent(this,WalkE_ControlBoard.class);

        switch (v.getId()){
            case R.id.central_clean_e_Button:
                Toast.makeText(getApplicationContext(), "Clicked on Button",
                        Toast.LENGTH_LONG).show();
                onDestroy();
                startActivity(activity_clean_e);
                break;
            case R.id.central_walk_e_Button:
                Toast.makeText(getApplicationContext(), "Clicked on Button",
                        Toast.LENGTH_LONG).show();
                onDestroy();
                startActivity(activity_walk_e);
                break;

        }

    }
}
