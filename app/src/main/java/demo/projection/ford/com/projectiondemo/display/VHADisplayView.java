package demo.projection.ford.com.projectiondemo.display;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import demo.projection.ford.com.projectiondemo.R;
import demo.projection.ford.com.projectiondemo.Utils;

/**
 * Created by leon on 2018/3/22.
 */

public class VHADisplayView extends DisplayView implements View.OnTouchListener
{
    private ImageView mIvVehicle;
    private ImageView mIvOK;
    private ImageButton mBtnWarningEngine;
    private ImageButton mBtnWarningTire;
    private ImageButton mBtnWarningSteer;
    private ImageButton mBtnWarningExhaust;
    private ImageButton mBtnWarningFuel;
    private ImageButton mBtnWarningMark;
    private RelativeLayout mLayoutMark;
    private TextView mTvWarning;
    private View mVehicleView;
    private View mVehicleMsgView;
    private ListView mMsgListView;
    private MessageAdapter mMsgAdapter;
    private Map<Utils.VHAMsgType, List<Utils.VHAMsg>> mMsgMap;
    private List<Utils.VHAMsg> mCurMsgList;
    private Typeface mFontSheepSans;
    private Typeface mFontOstrichSans;


    private Animation mVehicleMsgShowAnimation;
    private Animation mVehicleMsgDismissAnimation;
    private Animation mVHAWarningAnimation;
    private Animation mVHAWarningPopupAnimation;
    private Animation.AnimationListener mVHAAnimationListener = new Animation.AnimationListener()
    {
        @Override
        public void onAnimationStart(Animation animation)
        {

        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            if (animation == mVHAWarningPopupAnimation)
                showVehicleMsg(true);
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {

        }
    };



    public VHADisplayView(ProjectionDisplay projectionDisplay)
    {
        super(projectionDisplay);

        mFontSheepSans = Typeface.createFromAsset(getContext().getAssets(), "font/sheep-sans.ttf");
        mFontOstrichSans = Typeface.createFromAsset(getContext().getAssets(), "font/ostrich-sans-rounded.ttf");
    }

    public void shuffleVHAMsg()
    {
        mMsgMap = Utils.generateVHAMessageList();
        showVehicleMsg(false);
        showVehicleWarningButton();
    }

    @Override
    public int getId()
    {
        return R.layout.display_vha;
    }

    @Override
    public void create()
    {
        super.create();

        mVehicleMsgShowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.vehicle_msg_show);
        mVehicleMsgDismissAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.vehicle_msg_dismiss);
        mVHAWarningAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.vha_warning);
        mVHAWarningPopupAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.vha_warning_popup);
        mVHAWarningPopupAnimation.setAnimationListener(mVHAAnimationListener);

        mIvVehicle = findViewById(R.id.ivVehicle);
        mIvVehicle.setImageResource(R.drawable.vha_vehicle);

        mBtnWarningEngine = findViewById(R.id.btnWarningEngine);
        mBtnWarningTire = findViewById(R.id.btnWarningTire);
        mBtnWarningSteer = findViewById(R.id.btnWarningSteer);
        mBtnWarningExhaust = findViewById(R.id.btnWarningExhaust);
        mBtnWarningFuel = findViewById(R.id.btnWarningFuel);
        mBtnWarningMark = findViewById(R.id.btnWarning);
        mLayoutMark = findViewById(R.id.layoutMark);
        mTvWarning = findViewById(R.id.tvWarning);

        mVehicleView = findViewById(R.id.layoutVehicle);
        mVehicleMsgView = findViewById(R.id.layoutVehicleMsg);
        mMsgListView = findViewById(R.id.listViewMsg);
        mIvOK = findViewById(R.id.ivOK);

        mBtnWarningEngine.setOnTouchListener(this);
        mBtnWarningTire.setOnTouchListener(this);
        mBtnWarningSteer.setOnTouchListener(this);
        mBtnWarningExhaust.setOnTouchListener(this);
        mBtnWarningFuel.setOnTouchListener(this);
        mBtnWarningMark.setOnTouchListener(this);
        mIvOK.setOnTouchListener(this);
        mIvVehicle.setOnTouchListener(this);

        // For SDL bug. orz...
        mBtnWarningEngine.setImageResource(R.drawable.vha_warning);
        mBtnWarningTire.setImageResource(R.drawable.vha_warning);
        mBtnWarningSteer.setImageResource(R.drawable.vha_warning);
        mBtnWarningExhaust.setImageResource(R.drawable.vha_warning);
        mBtnWarningFuel.setImageResource(R.drawable.vha_warning);
        mBtnWarningMark.setImageResource(R.drawable.vha_warning_mark);
        mIvOK.setImageResource(R.drawable.ok);


        mMsgAdapter = new MessageAdapter(getContext());
        shuffleVHAMsg();
    }


    private void showVehicleWarningButton()
    {
        ImageButton[] btns =
                {
                        mBtnWarningEngine,
                        mBtnWarningTire,
                        mBtnWarningSteer,
                        mBtnWarningExhaust,
                        mBtnWarningFuel,
                };

        Utils.VHAMsgType[] types =
                {
                        Utils.VHAMsgType.ENGINE,
                        Utils.VHAMsgType.TIRE,
                        Utils.VHAMsgType.STEER,
                        Utils.VHAMsgType.EXHAUST,
                        Utils.VHAMsgType.FUEL,
                };

        // Message Button animation
        for (int i = 0; i < btns.length; i++)
        {
            if (mMsgMap.containsKey(types[i]))
            {
                btns[i].startAnimation(mVHAWarningAnimation);
                btns[i].setVisibility(View.VISIBLE);
            }
            else
            {
                btns[i].clearAnimation();
                btns[i].setVisibility(View.INVISIBLE);
            }
        }

        // Show Mark
        int count = 0;
        for(List one : mMsgMap.values())
            count += one.size();

        if (count > 0)
        {
            mTvWarning.setText("Alert:" + count);
            mLayoutMark.setVisibility(View.VISIBLE);
        }
        else
        {
            mLayoutMark.setVisibility(View.INVISIBLE);
        }
    }

    private void showVehicleMsg(boolean show)
    {
        mVehicleMsgView.setClickable(show);
        mVehicleView.setClickable(!show);

        if (show)
        {
            if (mVehicleMsgView.getVisibility() == View.INVISIBLE)
            {
                mMsgListView.setAdapter(mMsgAdapter);
                mVehicleMsgView.setVisibility(View.VISIBLE);
                mVehicleMsgView.startAnimation(mVehicleMsgShowAnimation);
            }

        }
        else
        {
            if (mVehicleMsgView.getVisibility() == View.VISIBLE)
            {
                mVehicleMsgView.setVisibility(View.INVISIBLE);
                mVehicleMsgView.startAnimation(mVehicleMsgDismissAnimation);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch(v.getId())
            {
            case R.id.ivOK:
                showVehicleMsg(false);
                return true;
            case R.id.btnWarningEngine:
                v.startAnimation(mVHAWarningPopupAnimation);
                mCurMsgList = mMsgMap.get(Utils.VHAMsgType.ENGINE);
                return true;
            case R.id.btnWarningExhaust:
                v.startAnimation(mVHAWarningPopupAnimation);
                mCurMsgList = mMsgMap.get(Utils.VHAMsgType.EXHAUST);
                return true;
            case R.id.btnWarningSteer:
                v.startAnimation(mVHAWarningPopupAnimation);
                mCurMsgList = mMsgMap.get(Utils.VHAMsgType.STEER);
                return true;
            case R.id.btnWarningTire:
                v.startAnimation(mVHAWarningPopupAnimation);
                mCurMsgList = mMsgMap.get(Utils.VHAMsgType.TIRE);
                return true;
            case R.id.btnWarningFuel:
                v.startAnimation(mVHAWarningPopupAnimation);
                mCurMsgList = mMsgMap.get(Utils.VHAMsgType.FUEL);
                return true;
            case R.id.btnWarning:
                mCurMsgList = new ArrayList<>();
                for(List one : mMsgMap.values())
                {
                    mCurMsgList.addAll(one);
                }
                showVehicleMsg(true);
                return true;
            case R.id.ivVehicle:
                showDrawer();
                return true;
            default:
                break;
            }
        }

        return super.onTouch(v, event);
    }





    // ListView
    private class ViewHolder
    {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvDetail;
    }

    public class MessageAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater = null;

        private MessageAdapter(Context context)
        {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
            return mCurMsgList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return mCurMsgList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;

            if(convertView == null)
            {
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.vehicle_message, null);
                holder.ivIcon = convertView.findViewById(R.id.ivIcon);
                holder.tvTitle = convertView.findViewById(R.id.tvTitle);
                holder.tvDetail = convertView.findViewById(R.id.tvDetail);

                holder.tvTitle.setTypeface(mFontOstrichSans);
                holder.tvDetail.setTypeface(mFontSheepSans);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.ivIcon.setImageResource(mCurMsgList.get(position).icon);
            holder.tvTitle.setText(mCurMsgList.get(position).title);
            holder.tvDetail.setText(mCurMsgList.get(position).detail);

            return convertView;
        }
    }
}
