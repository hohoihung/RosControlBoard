package com.example.robo_ace.roscontrolboard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.RosActivity;
import org.ros.android.view.RosImageView;
import org.ros.android.view.VirtualJoystickView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import sensor_msgs.CompressedImage;
import geometry_msgs.Twist;


public class MobileROSControllerActivity extends RosActivity {

    private RosImageView<sensor_msgs.CompressedImage> image;
    private VirtualJoystickView virtualJoystickView;

    public MobileROSControllerActivity() {
        super("ImageTransportTutorial", "ImageTransportTutorial");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_roscontroller);
        virtualJoystickView = (VirtualJoystickView) findViewById(R.id.virtual_joystick_with_cam);
        image = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.image11);
        image.setTopicName("/clean_e/camera/rgb/compressed/compressed");

        image.setMessageType(sensor_msgs.CompressedImage._TYPE);
        image.setMessageToBitmapCallable(new BitmapFromCompressedImage());
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());
        nodeMainExecutor
                .execute(virtualJoystickView, nodeConfiguration.setNodeName("virtual_joystick"));
        nodeMainExecutor.execute(image, nodeConfiguration.setNodeName("android/video_view"));
    }
}
