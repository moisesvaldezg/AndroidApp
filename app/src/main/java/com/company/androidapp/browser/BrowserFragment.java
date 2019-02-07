package com.company.androidapp.browser;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.company.androidapp.R;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowserFragment extends Fragment {

    private WebView webView;
    private ProgressDialog prDialog;

    private String url;

    public BrowserFragment(){}
    public BrowserFragment(String url) {
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        webView.setWebViewClient(new LoadingWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString("url");
        }
        webView.loadUrl(url);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString("url");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("url", url);
    }

    private class LoadingWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prDialog = new ProgressDialog(BrowserFragment.this.getActivity());
            prDialog.setMessage("Please wait...");
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(prDialog != null)
                prDialog.dismiss();
        }
    }
}
