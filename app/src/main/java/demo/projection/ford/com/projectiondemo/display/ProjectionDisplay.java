package demo.projection.ford.com.projectiondemo.display;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;

import com.smartdevicelink.streaming.video.SdlRemoteDisplay;

import java.util.Stack;

/**
 * Created by leon on 2018/3/21.
 */

public class ProjectionDisplay extends SdlRemoteDisplay
{
    public enum DisplayType
    {
        CAQ,
        VHA,
        WEB_APP,
        WEB_CRIP,
        WEB_DIANPING,
        WEB_WEATHER,
        WEB_MAP,
        WEB_FM,
        WEB_STOCK,
        WEB_QQ,
        WEB_BILI,
    }

    private Stack<DisplayType> mDisplayStack;
    private DisplayView mCurDisplay;
    private static ProjectionDisplay mInstance;



    public static ProjectionDisplay getInstance()
    {
        return mInstance;
    }

//    public ProjectionDisplay(@NonNull Context context)
//    {
//        super(context);
//        mInstance = this;
//    }



    public ProjectionDisplay(Context context, Display display)
    {
        super(context, display);
        mInstance = this;
    }

    public DisplayView getCurrentDisplayView()
    {
        return mCurDisplay;
    }

    public DisplayType getCurrentDisplayType()
    {
        return mDisplayStack.peek();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mDisplayStack = new Stack<>();
        launchDisplay(DisplayType.CAQ);
    }

    private DisplayView createDisplay(DisplayType type)
    {
        switch(type)
        {
        case CAQ:
            return new CAQDisplayView(this);
        case VHA:
            return new VHADisplayView(this);
        case WEB_APP:
            return new WebAppDisplayView(this);
        case WEB_WEATHER:
            return new WebBaseDisplayView(this,
                                          "https://weather.html5.qq.com/index?action=getWeatherById&cityid=310115&ch=001401");
        case WEB_CRIP:
            return new WebBaseDisplayView(this,
                                          "http://m.ctrip.com/html5");
        case WEB_DIANPING:
            return new WebBaseDisplayView(this,
                                          "https://m.dianping.com");
        case WEB_MAP:
            return new WebBaseDisplayView(this,
                                          "https://map.baidu.com/mobile/webapp/index/foo=bar/vt=map");
        case WEB_FM:
            return new WebBaseDisplayView(this,
                                          "http://fm.baidu.com");
        case WEB_STOCK:
            return new WebBaseDisplayView(this,
                                          "https://gupiao.baidu.com/stock/sh000001.html");
        case WEB_BILI:
            return new WebBaseDisplayView(this,
                                          "https://m.bilibili.com/index.html");

        case WEB_QQ:
            return new WebBaseDisplayView(this,
                                          "http://web2.qq.com");
        default:
            break;
        }

        return null;
    }

    public void launchDisplay(DisplayType type)
    {
        if (mCurDisplay != null)
        {
            mCurDisplay.destroy();
        }
        mCurDisplay = createDisplay(type);
        mCurDisplay.create();

        mDisplayStack.push(type);
    }

    // Go back
    public void backDisplay()
    {
        if (mDisplayStack.size() > 1)
        {
            mDisplayStack.pop();

            mCurDisplay.destroy();
            mCurDisplay = createDisplay(mDisplayStack.peek());
            mCurDisplay.create();
        }
    }

}
