package demo.projection.ford.com.projectiondemo.display;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import demo.projection.ford.com.projectiondemo.MainActivity;
import demo.projection.ford.com.projectiondemo.R;
import demo.projection.ford.com.projectiondemo.layout.SdlAQILayout;
import demo.projection.ford.com.projectiondemo.layout.SdlAQILayoutCrystal;

/**
 * Created by leon on 2018/3/9.
 */

public class CAQDisplayView extends DisplayView implements View.OnTouchListener
{
    private final static int MSG_UPDATE_UI = 100;
    private final static int MAX_AQI = 350;

    private ImageView mImageView = null;
    private Timer mTimer = null;
    private int mCurInnerAQI = 0;
    private int mCurOuterAQI = 0;
    private SdlAQILayout.WeatherInfo mWeatherInfo = null;


    public CAQDisplayView(ProjectionDisplay projectionDisplay)
    {
        super(projectionDisplay, true);
    }

    @Override
    public int getId()
    {
        return R.layout.display_caq;
    }

    @Override
    public void create()
    {
        super.create();

        mImageView = findViewById(R.id.ivVehicle);
        mImageView.setOnTouchListener(this);

        mWeatherInfo = new SdlAQILayout.WeatherInfo();
        mWeatherInfo.city = "Shanghai";
        mWeatherInfo.condition = "晴";
        mWeatherInfo.pressure = "1000";
        mWeatherInfo.temperature = "20";
        mWeatherInfo.windDir = "5";

        mTimer = new Timer();
        mTimer.schedule(new UpdateTask(), 0, 150);
    }

    @Override
    public void destroy()
    {
        mTimer.cancel();
        mTimer = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        onDoubleTap(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch(v.getId())
            {
            case R.id.ivVehicle:
                break;
            default:
                break;
            }
        }


        return false;
    }


    private Handler mUIHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
            case MSG_UPDATE_UI:
                if (mCurInnerAQI >= MAX_AQI)
                    mCurInnerAQI = 1;
                else
                    ++mCurInnerAQI;

                if (mCurOuterAQI >= MAX_AQI)
                    mCurOuterAQI = 2;
                else
                    mCurOuterAQI += 4;

                update(mCurInnerAQI, mCurOuterAQI, mWeatherInfo);
                break;
            default:
                break;
            }

            super.handleMessage(msg);
        }
    };


    private class UpdateTask extends TimerTask
    {
        @Override
        public void run()
        {
            mUIHandler.sendEmptyMessage(MSG_UPDATE_UI);
        }
    }


    public void update(int inner, int outer, SdlAQILayout.WeatherInfo weatherInfo)
    {
        // Send broadcast
        Intent intent = new Intent(MainActivity.AQIBroadcastReceiver.ACTION);
        intent.putExtra(MainActivity.AQIBroadcastReceiver.INNER_AQI, inner);
        intent.putExtra(MainActivity.AQIBroadcastReceiver.OUTER_AQI, outer);
        getContext().sendBroadcast(intent);

        // Update UI
        SdlAQILayout mLayout = SdlAQILayoutCrystal.getInstance(getContext());
        mImageView.setImageBitmap(mLayout.update(inner, outer, weatherInfo));
    }

}
