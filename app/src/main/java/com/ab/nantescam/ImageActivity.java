package com.ab.nantescam;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageActivity extends Activity implements OnDismissListener {

	ImageView imView;
	String fileUrl [];
	String waitMessage;
	AsyncTask<String, Integer, Void> async;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_main);
		imView = (ImageView) findViewById(R.id.image_viewer_image);
		Bundle extras = getIntent().getExtras();
		fileUrl = extras.getStringArray(Cams.KEY_IMAGEURL);
		waitMessage = getResources().getString(R.string.wait);
		if (fileUrl != null && fileUrl[0] != null) {
			setTitle(fileUrl[1]);
			launchDownload("");
		} else
			showErrorImage();
	}

	private void launchDownload(final String addon) {
		async = new AsyncTask<String, Integer, Void>() {

			private ProgressDialog progressDialog;
			
			protected void onPreExecute() {
				super.onPreExecute();
				this.progressDialog = ProgressDialog.show(ImageActivity.this, "", waitMessage, true);
				this.progressDialog.setCancelable(true);
				this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				this.progressDialog.setOnDismissListener(ImageActivity.this);
				this.progressDialog.setMessage(waitMessage);
				this.progressDialog.show();
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				this.progressDialog.dismiss();
			}

			@Override
			protected Void doInBackground(String... params) {
				URL myFileUrl = null;
				try {
					myFileUrl = new URL(params[0] + addon);
					HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream is = conn.getInputStream();

					if (!isCancelled())
						bmImg = BitmapFactory.decodeStream(is);
					if (!isCancelled())
						imView.post(new Runnable() {
	
							@Override
							public void run() {
								imView.setImageBitmap(bmImg);
							}
						});
					if (isCancelled())
						showErrorImage();
				} catch (Exception e) {
					e.printStackTrace();
					showErrorImage();
				}
				return null;
			}
		};
		async.execute(fileUrl[0]);
	}

	private void showErrorImage() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh && fileUrl != null) {
			launchDownload("?time=" + System.currentTimeMillis());
		}
		return false;
	}

	Bitmap bmImg;

	void downloadFile(String fileUrl) {
		
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		async.cancel(true);
	}

}
