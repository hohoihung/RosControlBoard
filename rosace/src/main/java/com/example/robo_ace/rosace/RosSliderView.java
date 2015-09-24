package com.example.robo_ace.rosace;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import std_msgs.Float32;


/**
 * Created by robo-ace on 7/14/15.
 */
public class RosSliderView extends RelativeLayout implements  NodeMain, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private String topicName;
    private String messageType;



    private String slider_title = "Gain Adjuster";
    private Publisher<Float32> publisher ;
    private Float32 msg_slide;
    private SeekBar seek;


    private double seek_max = 10.0;
    private double seek_min =  -10.0;
    private TextView leftText;
    private TextView rightText;
    private TextView belowText;
    private TextView aboveText;
    private TextView bottomRightText;



    public void setSeek_max(double seek_max) {
        this.seek_max = seek_max;
    }

    public void setSeek_min(double seek_min) {
        this.seek_min = seek_min;
    }

    public void setSlider_title(String slider_title) {
        this.slider_title = slider_title;
    }

    public RosSliderView(Context context) {
        super(context);
        this.initSliderView(context);
    }

    public RosSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initSliderView(context);
    }

    public RosSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initSliderView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RosSliderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initSliderView(context);
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
        bottomRightText.setText("Topic :" + topicName);

    }


    private double seekFunction(int i){
        double conversion = (seek_max - seek_min)/1000;

        return seek_min + (conversion*i);
    }

    private void setMiddle(){
        seek.setProgress(500);
        double avg = (seek_max+seek_min)/2;
        belowText.setText(Double.toString(avg));

    }


    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("robot_slider_node");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        publisher =  connectedNode.newPublisher(topicName, std_msgs.Float32._TYPE);
        msg_slide = publisher.newMessage();
        seek.setOnSeekBarChangeListener(this);
        seek.setProgress(500);

        leftText = (TextView) findViewById(R.id.textViewLeftRosSliderView);
        leftText.setOnClickListener(this);
        rightText = (TextView) findViewById(R.id.textViewRightRosSliderView);
        rightText.setOnClickListener(this);
        belowText = (TextView) findViewById(R.id.textViewBottomRosSliderView);
        aboveText = (TextView) findViewById(R.id.textViewTopRosSliderView);
        aboveText.setOnClickListener(this);







        /**/
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


    private void initSliderView(Context context){
        LayoutInflater.from(context).inflate(R.layout.ros_slider_view, this, true);
        seek = (SeekBar) findViewById(R.id.seekBarRos);
        bottomRightText = (TextView) findViewById(R.id.textViewBottomRightRosSliderView);




        //seek.set
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        msg_slide.setData((float) seekFunction(progress));
        double bla =   Math.round( seekFunction(progress) * 100.0) / 100.0;
        belowText.setText(Double.toString(bla));

        publisher.publish(msg_slide);





    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void dialogEngine(int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());


        if (i == 1) {
            View promptView = layoutInflater.inflate(R.layout.prompts, null);



            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilder.setView(promptView);

            final EditText input = (EditText) promptView.findViewById(R.id.userInput);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // get user input and set it to result

                            if (input.getText().toString().matches("")) {
                                Log.d("Dialog_msg", "Nothing entered");
                            } else {
                                leftText.setText(input.getText());
                                setSeek_min(Integer.parseInt(input.getText().toString()));
                                setMiddle();
                            }

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

        } else if (i == 2) {
            View promptView = layoutInflater.inflate(R.layout.prompts, null);



            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilder.setView(promptView);

            final EditText input = (EditText) promptView.findViewById(R.id.userInput);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // get user input and set it to result

                            if (input.getText().toString().matches("")) {
                                Log.d("Dialog_msg", "Nothing entered");
                            } else {
                                rightText.setText(input.getText());
                                setSeek_max(Integer.parseInt(input.getText().toString()));
                                setMiddle();
                            }

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
        }

        else if (i == 3){
            View promptView = layoutInflater.inflate(R.layout.prompts_for_title, null);



            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilder.setView(promptView);

            final EditText input = (EditText) promptView.findViewById(R.id.userInputPromptsTitle);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // get user input and set it to result

                            if  (input.getText().toString().matches(""))
                            {
                                Log.d("Dialog_msg","Nothing entered");
                            }
                            else {
                                aboveText.setText(input.getText());

                            }

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
        }

        // setup a dialog window


        // create an alert dialog
        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.textViewLeftRosSliderView) {
            dialogEngine(1);

        } else if (i == R.id.textViewRightRosSliderView) {
            dialogEngine(2);

        } else if (i == R.id.textViewTopRosSliderView) {
            dialogEngine(3);

        }
    }


}
