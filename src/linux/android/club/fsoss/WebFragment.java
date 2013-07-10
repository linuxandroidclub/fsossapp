package linux.android.club.fsoss;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_web, container, false);
		final ProgressBar progress = (ProgressBar) rootView.findViewById(R.id.ProgressBar);
		
        WebView webView = (WebView) rootView.findViewById(R.id.webView1);
		
        webView.getSettings().setJavaScriptEnabled(true);
        
        webView.setInitialScale(1);
        
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        
        
        webView.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onPageStarted(WebView view, String url, Bitmap favicon) {
        		
        	    progress.setVisibility(View.VISIBLE);
        		super.onPageStarted(view, url, favicon);
        	}
        	
        	@Override
        	public void onPageFinished(WebView view, String url) {
        	    progress.setVisibility(View.GONE);
        		super.onPageFinished(view, url);
        	}
        });
        webView.loadUrl("http://fsoss.senecac.on.ca/2013/");				
		
		return rootView;
	}
	
}
