package com.mrf.mrfmaharashtra.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
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

import es.dmoral.toasty.Toasty;

public class FragmentPdfContent extends Fragment {

    Preferences preferences;
   // ProgressBar progressBar;
    WebView webView;
    Dialog dialog;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pdf_content_fragment, container, false);


        preferences = new Preferences(getActivity());

        Bundle b = getArguments();
        String sub_id = b.getString("sub_id");

        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            ProgressDialogs();
            dialog.show();
            ApiPdfReader();

        } else {
            Toasty.error(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();

        }

       /* webView = view.findViewById(R.id.webview);
        progressBar=view.findViewById(R.id.progressbar);
        webView.getSettings().setJavaScriptEnabled(true);
        String pdf = preferences.get("pdf_product");
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

        webView.setWebViewClient(new WebViewClient(){

            public void onPageFinished(WebView view, String url) {
               progressBar.setVisibility(View.GONE);
            }
        });
*/


        MainActivity.tvHeaderText.setText(getString(R.string.selfdefence));
        MainActivity.iv_menu.setImageResource(R.drawable.ic_back);
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragmentWithAnimation(new FragmentCDOrgans());
               /* Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
*/
            }
        });



        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        WebView web_view = view.findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
        String myPdfUrl = "gymnasium-wandlitz.de/vplan/vplan.pdf";
        String url = "https://docs.google.com/viewer?embedded = true&url = "+myPdfUrl;
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress = = 100) {
                    progressDialog.dismiss();
                }
            }
        });
*/
        /*final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
        String pdf = preferences.get("pdf_product");
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);*/
        return view;
    }

    private void ApiPdfReader() {

    }

    private void ProgressDialogs() {
        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_for_load);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
