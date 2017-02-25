package priv.lucifer.buttonlistener.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.lucifer.buttonlistener.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {


    @BindView(R.id.web_main)
    WebView mWebView;

    private WebSettings settings ;
    private WebViewClient client ;

    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);

        settings = this . mWebView .getSettings();
        client = new WebViewClient();
        settings .setDefaultTextEncodingName( "UTF-8" );
        mWebView .setWebViewClient( this . client );
        mWebView.loadUrl("https://github.com/LuciferWong");
        return view;
    }

}
