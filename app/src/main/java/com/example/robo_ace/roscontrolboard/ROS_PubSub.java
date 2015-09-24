package com.example.robo_ace.roscontrolboard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import com.example.robo_ace.rosace.NotificationView;
import com.example.robo_ace.rosace.StatusView;
import com.example.robo_ace.rosace.MyMapView;
import com.example.robo_ace.rosace.RosSliderView;

import std_msgs.Int32;
//import org.ros.rosjava_tutorial_pubsub.Talker;

/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public class ROS_PubSub extends RosActivity {

    private RosTextView<Int32> rosTextView;
    private NotificationView notifications;
    private Drawable draw;

    //private Talker talker;
    private StatusView stat;
    private StatusView stat1;
    private RosSliderView slide;

    public ROS_PubSub() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("Pubsub Tutorial", "Pubsub Tutorial");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ros__pub_sub);
        setContentView(R.layout.activity_ros_pub_sub2);
        stat = (StatusView) findViewById(R.id.statView);
        stat1 = (StatusView) findViewById(R.id.statView2);
        slide = (RosSliderView) findViewById(R.id.ros_slider_1);

        notifications = (NotificationView) findViewById(R.id.notificationPubSub);
        notifications.setTopicName("/cleanE/notifications");
        stat.setToRobotTopicName("/cleanE/Controls");
        stat.setFromRobotTopicName("/cleanE/BatteryStatus");
        slide.setTopicName("/cleanE/slide_gain");

        stat1.setToRobotTopicName("/walkE/Controls");
        stat1.setFromRobotTopicName("/walkE/BatteryStatus");

        stat1.setIconImage(R.drawable.walke);
        /*rosTextView = (RosTextView<std_msgs.Int32>) findViewById(R.id.pubSubText);
        rosTextView.setTopicName("chatter");
        rosTextView.setMessageType(std_msgs.Int32._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, Int32>() {
            @Override
            public String call(Int32 int32) {
                return String.valueOf(int32.getData());
            }

            *//*@Override
            public String call(std_msgs.String message) {
                return message.getData();
            }*//*
        });*/
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        //talker = new Talker();
        //NodeConfiguration nodeConfiguration = NodeConfiguration.newPrivate();
        // At this point, the user has already been prompted to either enter the URI
        // of a master to use or to start a master locally.
        //nodeConfiguration.setMasterUri(getMasterUri());\

        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());
        //stat = new StatusView();
        //nodeMainExecutor.execute(stat, nodeConfiguration);

        nodeMainExecutor
                .execute(stat, nodeConfiguration.setNodeName("statusNode"));
        nodeMainExecutor
                .execute(notifications, nodeConfiguration.setNodeName("CleanE"));

        nodeMainExecutor
                .execute(stat1, nodeConfiguration.setNodeName("WalkE"));

        nodeMainExecutor.execute(slide,nodeConfiguration.setNodeName("slider_gain"));
        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        //nodeMainExecutor.execute(rosTextView, nodeConfiguration);
    }

    public void onClickStatusView(View view){
                stat.onClickStatus();
    }
}