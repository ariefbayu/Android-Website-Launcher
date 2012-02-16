package id.flwi.example.weblauncher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	WebView mainWebView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//must be called before adding content!
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mainWebView = (WebView) findViewById(R.id.mainWebView);
		mainWebView.loadUrl("http://bayu.freelancer.web.id/");

		mainWebView.setWebViewClient(new MainWebViewClient());

		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);

		mainWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				MainActivity.this.setTitle("Loading...");
				MainActivity.this.setProgress(progress * 100);
				if (progress == 100){
					MainActivity.this.setTitle(view.getTitle());
				}
			}
		});
		
		FrameLayout mContentView = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
		final View zoom = mainWebView.getZoomControls();
		mContentView.addView(zoom, new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				Gravity.BOTTOM));
	    zoom.setVisibility(View.GONE);
		

	}

	@Override
	public void onBackPressed(){
		if(mainWebView.canGoBack())
			mainWebView.goBack();
		else
			super.onBackPressed();
	}

	private class MainWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("Log", "loading: " + url);

			view.loadUrl(url);
			return true;
		}
	}
}
