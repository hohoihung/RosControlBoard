package com.example.robo_ace.roscontrolboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.robo_ace.rosace.RosSliderView;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;


public class MainActivity extends Activity {


    Intent activity1;


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnClickMainActivity(View view){
        activity1 = new Intent(this,MobileROSControllerActivity.class);
        Intent activity2 = new Intent(this,ROS_PubSub.class);
        Intent activity3 = new Intent(this,CleanE_ControlBoard.class);
        Intent activity4 = new Intent(this,WalkE_ControlBoard.class);
        Intent activity5 = new Intent(this,Central_dashboard.class);
        Intent activity6 = new Intent(this,TaskBuilder_Activity.class);

        switch (view.getId()){
            case R.id.main_button_1:
                startActivity(activity1);
                break;
            case R.id.main_button_2:
                startActivity(activity2);
                break;

            case R.id.main_button_3:
                startActivity(activity3);
                break;

            case R.id.main_button_4:
                startActivity(activity4);
                break;

            case R.id.main_button_5:
                startActivity(activity5);
                break;

            case R.id.main_button_6:
                startActivity(activity6);
                break;

            default:
                break;

        }
    }


}
