package demo.projection.ford.com.projectiondemo.display;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import demo.projection.ford.com.projectiondemo.R;

/**
 * Created by leon on 2018/3/22.
 */

public abstract class DisplayView
{
    private static final int BACK_VIEW_WIDTH = 64;
    private static final int BACK_VIEW_HEIGHT = 64;
    private static final int ID_BACK = 0xF001;
    private ProjectionDisplay mProjectionDisplay;
    private boolean mHasBackBtn;
    private MotionEvent mPrevMotionEvent = null;

    private Animation mBackAnimation;
    private Animation.AnimationListener mBackAnimationListener = new Animation.AnimationListener()
    {
        @Override
        public void onAnimationStart(Animation animation)
        {

        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            back();
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {

        }
    };


    public DisplayView(ProjectionDisplay projectionDisplay, boolean hasBackBtn)
    {
        mProjectionDisplay = projectionDisplay;
        mHasBackBtn = hasBackBtn;
    }

    public int getId()
    {
        return 0;
    }

    public void create()
    {
        setContentView(getId());

        if (mHasBackBtn)
        {
            ImageView btnView = new ImageView(getContext());
            btnView.setId(ID_BACK);
            btnView.setImageResource(R.drawable.arrow_back);
            btnView.setLayoutParams(new ViewGroup.LayoutParams(BACK_VIEW_WIDTH, BACK_VIEW_HEIGHT));
            btnView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mProjectionDisplay.addContentView(btnView, new RelativeLayout.LayoutParams(BACK_VIEW_WIDTH, BACK_VIEW_HEIGHT));

            mBackAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.btn_back);
            mBackAnimation.setAnimationListener(mBackAnimationListener);

            btnView.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        switch (v.getId())
                        {
                        case ID_BACK:
                            v.startAnimation(mBackAnimation);
                            break;
                        default:
                            break;
                        }

                    }
                    return false;
                }
            });
        }

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
        mProjectionDisplay.launchDisplay(type);
    }

    protected final void back()
    {
        mProjectionDisplay.backDisplay();
    }

    protected final Context getContext()
    {
        return mProjectionDisplay.getContext();
    }


//    private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent secondDown)
//    {
//        if (secondDown.getEventTime() - firstDown.getEventTime() > 200)
//            return false;
//
//        int deltaX =(int) firstDown.getX() - (int)secondDown.getX();
//        int deltaY =(int) firstDown.getY()- (int)secondDown.getY();
//        return deltaX * deltaX + deltaY * deltaY < 10000;
//    }

    protected boolean onDoubleTap(MotionEvent event)
    {
        if (mPrevMotionEvent == null)
        {
            mPrevMotionEvent = event;
            return false;
        }
        else
        {
            boolean ret = event.getEventTime() - mPrevMotionEvent.getEventTime() < 200;
            if (ret)
            {
                Toast.makeText(getContext(), "这就是传说中的双击事件", Toast.LENGTH_SHORT).show();
            }
            mPrevMotionEvent = event;
            return ret;
        }

    }




}
