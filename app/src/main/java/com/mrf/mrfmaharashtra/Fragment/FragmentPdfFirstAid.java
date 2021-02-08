package com.mrf.mrfmaharashtra.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mrf.mrfmaharashtra.Activity.MainActivity;
import com.mrf.mrfmaharashtra.Activity.Preferences;
import com.mrf.mrfmaharashtra.R;

import javax.security.auth.callback.Callback;

public class FragmentPdfFirstAid extends Fragment {

    Preferences preferences;
    ProgressBar progressBar;
    WebView webView;

    Dialog dialog;
    String pdfFileName;


    View view;
    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pdf_content_fragment, container, false);


        preferences = new Preferences(getActivity());

        Bundle b = getArguments();
        String sub_id = b.getString("sub_id");
        String pdf_content=b.getString("pdf_content");



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


        //MainActivity.tvHeaderText.setText(getString(R.string.selfdefence));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentWithAnimation(new FragmentFirstaid());
               /* Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);*/

            }
        });

        return view;
    }
    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
