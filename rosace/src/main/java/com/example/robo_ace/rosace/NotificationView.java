package com.example.robo_ace.rosace;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;

import java.util.Stack;

import std_msgs.Int8;

/**
 * Created by robo-ace on 7/4/15.
 */
public class NotificationView extends RelativeLayout implements MessageListener<std_msgs.Int8>, NodeMain {

    private TextView[] notificationText;
    private short notice_code;
    private Stack<Short> notification_history = new Stack();
    private String[] notification_decode = new String[10];
    private String topicName;
    private String messageType;


    public NotificationView(Context context) {
        super(context);
        this.initNotificationView(context);
    }

    public NotificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initNotificationView(context);
    }

    public NotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initNotificationView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NotificationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initNotificationView(context);
    }


    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }



    @Override
    public void onNewMessage(Int8 int8) {
        notice_code = int8.getData();
        notification_history.push(notice_code);
        if (notification_history.size() > 10 ){
            notification_history.pop();
        }
        this.post(new Runnable() {
            public void run() {
                printNotifications();
            }
        });
        this.postInvalidate();

    }

    private void initNotificationView(Context context) {
        //this.setGravity(17);
        LayoutInflater.from(context).inflate(R.layout.notification_panel_view, this, true);
        notificationText = new TextView[10];
        notificationText[0] = (TextView)this.findViewById(R.id.NotificationPanelContent1);
        notificationText[1] = (TextView)this.findViewById(R.id.NotificationPanelContent2);
        notificationText[2] = (TextView)this.findViewById(R.id.NotificationPanelContent3);
        notificationText[3] = (TextView)this.findViewById(R.id.NotificationPanelContent4);
        notificationText[4] = (TextView)this.findViewById(R.id.NotificationPanelContent5);
        notificationText[5] = (TextView)this.findViewById(R.id.NotificationPanelContent6);
        notificationText[6] = (TextView)this.findViewById(R.id.NotificationPanelContent7);
        notificationText[7] = (TextView)this.findViewById(R.id.NotificationPanelContent8);
        notificationText[8] = (TextView)this.findViewById(R.id.NotificationPanelContent9);
        notificationText[9] = (TextView)this.findViewById(R.id.NotificationPanelContent10);

        notification_decode[0] = "Cleaning complete !! \n it was fun cleaning the house :) ";
        notification_decode[1] = "Charging !! its time \n for me to take some rest, call me if you need something ;)";
        notification_decode[2] = "Charging job done !! \n feel like I had enough rest its time do some rock and roll :D ";
        notification_decode[3] = "Blocked !! \n I am obstructed by something please help me !! Help ! Help !";
        notification_decode[4] = "Just finished cleaning your living room :)";
        notification_decode[5] = "Just finished cleaning your top floor bed room :)";
        notification_decode[6] = "Just finished cleaning your bottom floor bed room :)";
        notification_decode[7] = "Just finished cleaning your Kitchen :)";
        notification_decode[8] = "New Updattes available.. \n Update me please !!";
        notification_decode[9] = "Its good to have newer version of software on me, \n Thank you :)";




    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("robot_notification_node");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<std_msgs.Int8> subscriber = connectedNode.newSubscriber(topicName, std_msgs.Int8._TYPE);
        subscriber.addMessageListener((MessageListener<std_msgs.Int8>) this);

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

    public void printNotifications(){
        int count = notification_history.size()-1;
        for (Short i : notification_history){
            notificationText[count].setText(notification_decode[i]);
            count--;
        }

    }
}
