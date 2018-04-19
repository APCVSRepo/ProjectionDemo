package demo.projection.ford.com.projectiondemo.display;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import demo.projection.ford.com.projectiondemo.R;

/**
 * Created by leon on 2018/3/27.
 */

public class WebAppDisplayView extends DisplayView implements View.OnTouchListener
{
    private Button mBtnCtrip;
    private Button mBtnDianping;
    private Button mBtnWeather;
    private Button mBtnMap;
    private Button mBtnFM;
    private Button mBtnStock;
    private Button mBtnQQ;
    private Button mBtnBili;

    @Override
    public int getId()
    {
        return R.layout.display_web_app;
    }

    @Override
    public void create()
    {
        super.create();

        mBtnCtrip = findViewById(R.id.btnCtrip);
        mBtnDianping = findViewById(R.id.btnDianping);
        mBtnWeather = findViewById(R.id.btnWeather);
        mBtnMap = findViewById(R.id.btnBaiduMap);
        mBtnFM = findViewById(R.id.btnFM);
        mBtnStock = findViewById(R.id.btnStock);
        mBtnQQ = findViewById(R.id.btnQQ);
        mBtnBili = findViewById(R.id.btnBili);

        mBtnCtrip.setOnTouchListener(this);
        mBtnDianping.setOnTouchListener(this);
        mBtnWeather.setOnTouchListener(this);
        mBtnMap.setOnTouchListener(this);
        mBtnFM.setOnTouchListener(this);
        mBtnStock.setOnTouchListener(this);
        mBtnQQ.setOnTouchListener(this);
        mBtnBili.setOnTouchListener(this);
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public WebAppDisplayView(ProjectionDisplay projectionDisplay)
    {
        super(projectionDisplay, true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch(v.getId())
            {
            case R.id.btnCtrip:
                launch(ProjectionDisplay.DisplayType.WEB_CRIP);
                break;
            case R.id.btnDianping:
                launch(ProjectionDisplay.DisplayType.WEB_DIANPING);
                break;
            case R.id.btnWeather:
                launch(ProjectionDisplay.DisplayType.WEB_WEATHER);
                break;
            case R.id.btnBaiduMap:
                launch(ProjectionDisplay.DisplayType.WEB_MAP);
                break;
            case R.id.btnFM:
                launch(ProjectionDisplay.DisplayType.WEB_FM);
                break;
            case R.id.btnStock:
                launch(ProjectionDisplay.DisplayType.WEB_STOCK);
                break;
            case R.id.btnQQ:
                launch(ProjectionDisplay.DisplayType.WEB_QQ);
                break;
            case R.id.btnBili:
                launch(ProjectionDisplay.DisplayType.WEB_BILI);
                break;
            default:
                break;
            }
        }

        return false;
    }
}
