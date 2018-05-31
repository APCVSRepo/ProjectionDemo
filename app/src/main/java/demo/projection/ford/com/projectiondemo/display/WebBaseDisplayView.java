package demo.projection.ford.com.projectiondemo.display;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;


import demo.projection.ford.com.projectiondemo.R;
import demo.projection.ford.com.projectiondemo.Utils;
import demo.projection.ford.com.projectiondemo.display.web.H5WebView;

/**
 * Created by leon on 2018/4/3.
 */

public class WebBaseDisplayView extends DisplayView implements View.OnTouchListener, H5WebView.OnPageListener
{
    private H5WebView mWebView;
    private String mOnInputJs;
    private String mSetValueJs;
    private String mURL;
    private String mJsID;


    private Handler mUIHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            showKeyboard();
            super.handleMessage(msg);
        }
    };

    public class JSCallback
    {
        @JavascriptInterface
        public void onInput(String id)
        {
            mJsID = id;
            mUIHandler.sendEmptyMessage(0);
        }
    }


    public WebBaseDisplayView(ProjectionDisplay projectionDisplay, String url)
    {
        super(projectionDisplay);
        mURL = url;

        mOnInputJs = "javascript:" + Utils.getAssetsFileAsText(getContext(), "js/inject.onInput.js");
        mSetValueJs = "javascript:" + Utils.getAssetsFileAsText(getContext(), "js/inject.setValue.js") + "\ninject_setValue(\"%s\");";
    }

    @Override
    public int getId()
    {
        return R.layout.display_web_base;
    }

    @Override
    public void create()
    {
        super.create();

        mWebView = findViewById(R.id.wvContent);
        mWebView.setOnTouchListener(this);

        mWebView.loadUrl(mURL);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setOnPageListener(this);
        mWebView.addJavascriptInterface(new JSCallback(), "callback");
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (v.getId() == R.id.wvContent)
        {
            return mWebView.onTouchEvent(event);
        }
        else
        {
            return super.onTouch(v, event);
        }
    }

    @Override
    public void OnKeyDone(String value)
    {
        super.OnKeyDone(value);
        mWebView.evaluateJavascript(String.format(mSetValueJs, value),null);
    }

    @Override
    public void onPageFinished()
    {
        mWebView.evaluateJavascript(mOnInputJs, null);
    }
}
