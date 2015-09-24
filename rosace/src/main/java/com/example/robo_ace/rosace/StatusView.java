package com.example.robo_ace.rosace;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import java.lang.String;

import status_msgs.Status;
import std_msgs.*;

/**
 * Created by robo-ace on 7/3/15.
 */
public class StatusView extends RelativeLayout implements MessageListener<status_msgs.Status>, NodeMain{
    private Long charge ;
    private Publisher<Status> publisher ;
    private long carge_status = 0;
    private TextView status_text ;
    private RelativeLayout main_layout;
    private Switch powerSwitch;
    private Switch wifiSwitch;
    private Switch chargeSwitch;
    private String TotopicName;
    private String FromtopicName;
    private ImageView imageIcon;

    status_msgs.Status str;

    private IconRoundCornerProgressBar progressOne;

    public StatusView(Context context) {
        super(context);

        this.initStatusView(context);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.initStatusView(context);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setToRobotTopicName(String topicName) {
        this.TotopicName = topicName;
    }
    public void setFromRobotTopicName(String topicName) {
        this.FromtopicName = topicName;
    }



    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("robot_status_node");
    }

    public void setIconImage(int i){
        imageIcon.setImageResource(i);
    }
    @Override
    public void onStart(final ConnectedNode connectedNode) {
        Subscriber<status_msgs.Status> subscriber = connectedNode.newSubscriber(FromtopicName, status_msgs.Status._TYPE);
        subscriber.addMessageListener((MessageListener<Status>) this);

        publisher =  connectedNode.newPublisher(TotopicName, status_msgs.Status._TYPE);
        str = publisher.newMessage();

        powerSwitch .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    str.setRobotStatus(true);
                    publisher.publish(str);

                } else {
                    str.setRobotStatus(false);
                    publisher.publish(str);

                }

            }
        });

        wifiSwitch .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    str.setWifiStatus(true);
                    publisher.publish(str);

                } else {
                    str.setWifiStatus(false);
                    publisher.publish(str);

                }

            }
        });

        chargeSwitch .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    str.setChargeStatus(true);
                    publisher.publish(str);

                } else {
                    str.setChargeStatus(false);
                    publisher.publish(str);

                }

            }
        });
        //this.status_text.setText("lol");


    }

    private void initStatusView(Context context) {
        //this.setGravity(17);
        LayoutInflater.from(context).inflate(R.layout.status_view, this, true);
        this.main_layout = (RelativeLayout) this.findViewById(R.id.status_view_layout);
        //this.status_text = (TextView)this.findViewById(R.id.statusText);

        progressOne = (IconRoundCornerProgressBar) findViewById(R.id.progress_one);
        progressOne.setProgressColor(
                getResources().getColor(R.color.custom_progress_green_header),
                getResources().getColor(R.color.custom_progress_green_progress_half)
        );
        progressOne.setHeaderColor(getResources().getColor(R.color.custom_progress_blue_header));
        progressOne.setBackgroundColor(getResources().getColor(R.color.custom_progress_background));
        progressOne.setIconImageResource(R.drawable.battery);
        progressOne.setMax(100);

        powerSwitch = (Switch) findViewById(R.id.mySwitchPower);
        powerSwitch .setChecked(true);

        wifiSwitch = (Switch) findViewById(R.id.mySwitchWifi);
        wifiSwitch .setChecked(true);

        chargeSwitch = (Switch) findViewById(R.id.mySwitchCharge);
        chargeSwitch .setChecked(true);
        imageIcon = (ImageView) findViewById(R.id.imageViewStatusView);
        //attach a listener to check for changes in state

        //this.status_text.setText("lol");
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

    public void printText(){

        try {

            progressOne.setProgress(charge.intValue());
            progressOne.setSecondaryProgress(charge.intValue() + 5);

            //this.status_text.setText(charge.toString());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onClickStatus(){


        carge_status = carge_status + 5;
        str.setBattery(carge_status );
        publisher.publish(str);
    }

    @Override
    public void onNewMessage(Status status) {
        charge = status.getBattery();
        this.post(new Runnable() {
            public void run() {
                printText();
            }
        });
        this.postInvalidate();

    }
}
