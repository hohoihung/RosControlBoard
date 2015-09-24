package com.example.robo_ace.rosace;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.ros.internal.message.RawMessage;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;
import std_msgs.Float32;
import std_msgs.Int8;

/**
 * Created by robo-ace on 7/5/15.
 */
public class MyMapView extends RelativeLayout implements MessageListener<geometry_msgs.Twist>, NodeMain{
    private ImageView map_img ;
    private MyView extra_view;
    private RelativeLayout map_rel;
    private String topicName = "/lol";
    private Publisher<geometry_msgs.Twist> publisher ;
    public geometry_msgs.Twist msg_slide;
    

    public MyMapView(Context context) {
        super(context);
        this.initMyMapView(context);
    }

    public MyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initMyMapView(context);
    }

    public MyMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initMyMapView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyMapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initMyMapView(context);
    }

    private void initMyMapView(Context context) {
        //this.setGravity(17);
        LayoutInflater.from(context).inflate(R.layout.my_map_view, this, true);
        map_rel = (RelativeLayout) findViewById(R.id.mapRelativeLayout);
        map_img = (ImageView) findViewById(R.id.mymapimageView);
        //map_img.setScaleType(ImageView.ScaleType.FIT_XY);
        extra_view = new MyView(context);
        map_rel.addView(extra_view);
        map_img.invalidate();


    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;


    }

    @Override
    public GraphName getDefaultNodeName() {
        return null;
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        publisher =  connectedNode.newPublisher(topicName, geometry_msgs.Twist._TYPE);
        //msg_slide = publisher.newMessage();

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
    public boolean onTouchEvent(MotionEvent event){
        map_rel.removeView(extra_view);
        Log.d("BlaBla", String.valueOf(event.getX()) + "," + String.valueOf(event.getY()));

        /*msg_slide.getLinear().setX(((double) event.getX()));
        msg_slide.getLinear().setY(((double) event.getY()));*/
        /*msg_slide.getLinear().setX(0.0D);
        msg_slide.getLinear().setY(0.0D);
        msg_slide.getLinear().setZ(0.0D);

        msg_slide.getAngular().setX(0.0D);
        msg_slide.getAngular().setY(0.0D);
        msg_slide.getAngular().setZ(0.0D);
        extra_view.setPoint(event.getX(), event.getY());*/
        extra_view.setPoint(event.getX(), event.getY());
        map_rel.addView(extra_view);


       
        

        //publisher.publish(msg_slide);

        return false;
    }

    @Override
    public void onNewMessage(Twist twist) {

    }

    class MyView extends View{


        Paint paint = new Paint();
        PointF point = new PointF();
        Canvas toPrint = new Canvas();
        public MyView(Context context) {
            super(context);
            paint.setColor(getResources().getColor(R.color.background_dark));
            this.point.x = 0;
            this.point.y = 0;
            //paint.setS
        }

        @Override
        protected void onDraw(Canvas canvas) {

            //canvas.drawPoint(point.x, point.y, paint);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            canvas.drawRect(this.point.x - 5 , this.point.y - 5 , this.point.x + 5 , this.point.y + 5, paint);
           /* paint.setStrokeWidth(0);
            paint.setColor(Color.CYAN);
            canvas.drawRect(33, 60, 77, 77, paint );
            paint.setColor(Color.YELLOW);
            canvas.drawRect(33, 33, 77, 60, paint);*/

        }

        public void setPoint(Float x,Float y){
            this.point.x = x;
            this.point.y = y;
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            toPrint.drawRect(this.point.x - 15, this.point.y - 15, this.point.x + 15 , this.point.y + 15, paint);


        }
        /*@Override
        public boolean onTouchEvent(MotionEvent event) {

            point.x = event.getX();
            point.y = event.getY();

            return true;

        }*/

    }
}
