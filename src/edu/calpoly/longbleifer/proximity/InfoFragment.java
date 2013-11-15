package edu.calpoly.longbleifer.proximity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class InfoFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		
		int position = this.getArguments().getInt("id");
		InfoTab tab = (InfoTab) ProximityActivity.trigger.tabs[position];
        
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.info_fragment, container, false);
        WebView webview = (WebView) layout.findViewById(R.id.webView);

        webview.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

        if(container == null){
            return null;
        }
        
        webview.loadData(tab.html, "text/html", null);

        return layout;
	}
}
