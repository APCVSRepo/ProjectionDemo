package demo.projection.ford.com.projectiondemo.display;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import demo.projection.ford.com.projectiondemo.R;

/**
 * Created by leon on 2018/3/22.
 */

public abstract class DisplayView implements View.OnTouchListener
{
    private ProjectionDisplay mProjectionDisplay;
    private MotionEvent mPrevMotionEvent = null;
    private DrawerLayout mMainLayout = null;

    public DisplayView(ProjectionDisplay projectionDisplay)
    {
        mProjectionDisplay = projectionDisplay;
    }

    public int getId()
    {
        return 0;
    }

    public void create()
    {
        setContentView(getId());

        mMainLayout = findViewById(R.id.main_layout);
        mMainLayout.setOnTouchListener(this);

        findViewById(R.id.btnCAQ).setOnTouchListener(this);
        findViewById(R.id.btnVHA).setOnTouchListener(this);
        findViewById(R.id.btnApp).setOnTouchListener(this);
    }

    public void destroy()
    {

    }

    protected final void setContentView(int id)
    {
        mProjectionDisplay.setContentView(id);
    }

    protected final <T extends View> T findViewById(int id)
    {
        return mProjectionDisplay.findViewById(id);
    }

    protected final void launch(ProjectionDisplay.DisplayType type)
    {
        if (mProjectionDisplay.getCurrentDisplayType() != type)
            mProjectionDisplay.launchDisplay(type);
    }

//    protected final void back()
//    {
//        mProjectionDisplay.backDisplay();
//    }

    protected final Context getContext()
    {
        return mProjectionDisplay.getContext();
    }


    private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent secondDown)
    {
        if (secondDown.getEventTime() - firstDown.getEventTime() > 200)
            return false;

        int deltaX =(int) firstDown.getX() - (int)secondDown.getX();
        int deltaY =(int) firstDown.getY()- (int)secondDown.getY();
        return deltaX * deltaX + deltaY * deltaY < 10000;
    }

    protected boolean onDoubleTap(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (mPrevMotionEvent == null)
            {
                mPrevMotionEvent = event;
                return false;
            }
            else
            {
                boolean ret = isConsideredDoubleTap(mPrevMotionEvent, event);
                if (ret)
                    mPrevMotionEvent = null;
                else
                    mPrevMotionEvent = event;

                return ret;
            }
        }

        return false;

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
                mMainLayout.closeDrawer(Gravity.LEFT, true);
                return true;
            case R.id.btnVHA:
                launch(ProjectionDisplay.DisplayType.VHA);
                mMainLayout.closeDrawer(Gravity.LEFT, true);
                return true;
            case R.id.btnApp:
                launch(ProjectionDisplay.DisplayType.WEB_APP);
                mMainLayout.closeDrawer(Gravity.LEFT, true);
                return true;
            case R.id.main_layout:
            default:
                showDrawer();
                return false;
            }
        }

        return false;
    }


    protected void showDrawer()
    {
        if (mMainLayout.isDrawerOpen(Gravity.LEFT))
            mMainLayout.closeDrawer(Gravity.LEFT, true);
        else
            mMainLayout.openDrawer(Gravity.LEFT, true);

    }
}
