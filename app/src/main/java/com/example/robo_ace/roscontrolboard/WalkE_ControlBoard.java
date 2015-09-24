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


public class WalkE_ControlBoard extends RosActivity {

    private NotificationView notifications;



    private StatusView stat;


    public WalkE_ControlBoard() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("WalkE Tutorial", "WalkE Tutorial");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_walk_e__control_board);
        stat = (StatusView) findViewById(R.id.walk_e_statView);



        notifications = (NotificationView) findViewById(R.id.walk_e_notificationPubSub);
        notifications.setTopicName("/walkE/notifications");
        stat.setToRobotTopicName("/walkE/Controls");
        stat.setFromRobotTopicName("/walkE/BatteryStatus");
        stat.setIconImage(R.drawable.walke);


    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());
        nodeMainExecutor
                .execute(stat, nodeConfiguration.setNodeName("walk_e_statusNode"));
        nodeMainExecutor
                .execute(notifications, nodeConfiguration.setNodeName("walk_e_notificationNode"));



    }

    public void walkeDashboardOnClick(View v){
        Intent activity_task_builder = new Intent(this,TaskBuilder_Activity.class);
        Intent activity_map_view = new Intent(this,MapViewActivity.class);


        switch (v.getId()){
            case R.id.walk_e_button1:
                Toast.makeText(getApplicationContext(), "Clicked on Button",
                        Toast.LENGTH_LONG).show();
                onDestroy();
                startActivity(activity_task_builder);
                break;

            case R.id.walk_e_button5:
                Toast.makeText(getApplicationContext(), "Clicked on Button",
                        Toast.LENGTH_LONG).show();
                onDestroy();
                startActivity(activity_map_view);
                break;
            default:
                break;

        }

    }
}
