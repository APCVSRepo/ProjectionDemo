package demo.projection.ford.com.projectiondemo.display;

import android.view.MotionEvent;
import android.view.View;

import demo.projection.ford.com.projectiondemo.R;

/**
 * Created by leon on 2018/3/9.
 */

public class MainDisplayView extends DisplayView implements View.OnTouchListener
{
    public MainDisplayView(ProjectionDisplay projectionDisplay)
    {
        super(projectionDisplay, false);
    }

    @Override
    public int getId()
    {
        return R.layout.display_main;
    }

    @Override
    public void create()
    {
        super.create();

        findViewById(R.id.btnCAQ).setOnTouchListener(this);
        findViewById(R.id.btnVHA).setOnTouchListener(this);
        findViewById(R.id.btnApp).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch(v.getId())
            {
            case R.id.btnCAQ:
                launch(ProjectionDisplay.DisplayType.CAQ);
                break;
            case R.id.btnVHA:
                launch(ProjectionDisplay.DisplayType.VHA);
                break;
            case R.id.btnApp:
                launch(ProjectionDisplay.DisplayType.WEB_APP);
                break;
            default:
                break;
            }

        }

        return false;
    }



}
