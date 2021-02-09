package com.mrf.mrfmaharashtra.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mrf.mrfmaharashtra.Activity.MainActivity;
import com.mrf.mrfmaharashtra.Activity.Preferences;
import com.mrf.mrfmaharashtra.Activity.Utils;
import com.mrf.mrfmaharashtra.R;

import javax.security.auth.callback.Callback;

import es.dmoral.toasty.Toasty;

public class FragmentPdfOrgn extends Fragment {

    Preferences preferences;
    ProgressBar progressBar;
    WebView webView;


    Dialog dialog;
    String pdfFileName;


    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pdf_content_fragment, container, false);


        preferences = new Preferences(getActivity());

        Bundle b = getArguments();
        String pdf_content=b.getString("pdf_product");


        webView = view.findViewById(R.id.webview);
        progressBar=view.findViewById(R.id.progressbar);

        WebSettings webSettings = webView.getSettings();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());
        webSettings.setBuiltInZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");                if (view.getContentHeight() == 0)
                    view.reload();
                progressBar.setVisibility(View.GONE);

            }
        });
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdf_content );


        // MainActivity.tvHeaderText.setText(getString(R.string.selfdefence));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentWithAnimation(new FragmentCDOrgans());

            }
        });

        return view;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }

}
