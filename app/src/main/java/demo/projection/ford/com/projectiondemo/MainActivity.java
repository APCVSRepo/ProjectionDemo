package demo.projection.ford.com.projectiondemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import demo.projection.ford.com.projectiondemo.display.DisplayView;
import demo.projection.ford.com.projectiondemo.display.ProjectionDisplay;
import demo.projection.ford.com.projectiondemo.display.VHADisplayView;
import demo.projection.ford.com.projectiondemo.layout.SdlAQILayout;
import demo.projection.ford.com.projectiondemo.sdl.MySdlService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static MySdlService.ServiceBinder mBinder;
    private static TextView mTvInnerAQI;
    private static TextView mTvOuterAQI;

    public static class AQIBroadcastReceiver extends BroadcastReceiver
    {
        public static final String ACTION = "demo.projection.ford.com.receiver.aqi";
        public static final String INNER_AQI = "inner";
        public static final String OUTER_AQI = "outer";

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int inner = intent.getIntExtra(INNER_AQI, 0);
            int outer = intent.getIntExtra(OUTER_AQI, 0);

            MainActivity.mTvInnerAQI.setTextColor(SdlAQILayout.getAQIColor(inner));
            MainActivity.mTvInnerAQI.setText("Inner:" + inner);

            MainActivity.mTvOuterAQI.setTextColor(SdlAQILayout.getAQIColor(outer));
            MainActivity.mTvOuterAQI.setText("Outer:" + outer);
        }
    }


    private ServiceConnection mSrvConn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mBinder = (MySdlService.ServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mBinder = null;
        }
    };


    @Override
    public void onClick(View v)
    {
        DisplayView displayView = null;
        ProjectionDisplay projectionDisplay = ProjectionDisplay.getInstance();
        if (projectionDisplay != null)
            displayView = projectionDisplay.getCurrentDisplayView();

        switch(v.getId())
        {
        case R.id.btnTest:
//            (new ProjectionDisplay(this)).show();
            break;
        case R.id.btnVHA:
            if (displayView instanceof VHADisplayView)
                ((VHADisplayView) displayView).shuffleVHAMsg();;
            break;
        default:
            break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvInnerAQI = findViewById(R.id.tvInnerAQI);
        mTvOuterAQI = findViewById(R.id.tvOuterAQI);

        findViewById(R.id.btnTest).setOnClickListener(this);
        findViewById(R.id.btnVHA).setOnClickListener(this);


        Intent intent = new Intent(this, MySdlService.class);
        bindService(intent, mSrvConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy()
    {
        unbindService(mSrvConn);
        super.onDestroy();
    }
}
