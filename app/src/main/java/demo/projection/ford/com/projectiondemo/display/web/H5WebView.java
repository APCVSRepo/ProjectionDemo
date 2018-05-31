package demo.projection.ford.com.projectiondemo.display.web;

import android.content.Context;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by leon on 2018/5/22.
 */

public class H5WebView extends WebView
{
    private OnPageListener mPageListener = null;

    public interface OnPageListener
    {
        void onPageFinished();
    }

    private final WebViewClient mWbClient = new WebViewClient()
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (url == null)
                return true;

            if (!url.startsWith("http://") && !url.startsWith("https://"))
                return true;

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            if (mPageListener != null)
                mPageListener.onPageFinished();

            super.onPageFinished(view, url);
        }
    };

    private final WebChromeClient mWbChromeClient = new WebChromeClient()
    {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback)
        {
            callback.invoke(origin, true, true);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };


    public H5WebView(Context context)
    {
        super(context);
    }

    public H5WebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public H5WebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadUrl(String url)
    {
        super.loadUrl(url);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        return super.onCreateInputConnection(outAttrs);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        WebSettings ws = getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setSaveFormData(false);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setSupportZoom(false);
        ws.setGeolocationEnabled(true);
        ws.setSupportMultipleWindows(true);
        //        ws.setBuiltInZoomControls(true);

        setWebViewClient(mWbClient);
        setWebChromeClient(mWbChromeClient);
        //        mWbContent.setInitialScale(50);
    }

    public void setOnPageListener(OnPageListener listener)
    {
        mPageListener = listener;
    }

}
