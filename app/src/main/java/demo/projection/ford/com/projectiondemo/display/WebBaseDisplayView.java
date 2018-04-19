package demo.projection.ford.com.projectiondemo.display;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import demo.projection.ford.com.projectiondemo.R;

/**
 * Created by leon on 2018/4/3.
 */

public class WebBaseDisplayView extends DisplayView
{
    private WebView mWbContent;
    private String mURL;
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
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
        }
    };

    public WebBaseDisplayView(ProjectionDisplay projectionDisplay, String url)
    {
        super(projectionDisplay, true);
        mURL = url;
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

        mWbContent = findViewById(R.id.wvContent);

        WebSettings ws = mWbContent.getSettings();
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
        //        ws.setBuiltInZoomControls(true);


        mWbContent.setWebViewClient(mWbClient);
        mWbContent.loadUrl(mURL);
//        mWbContent.setInitialScale(50);
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }
}
