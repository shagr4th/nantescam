package com.ab.nantescam;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CameraDetailFragment extends Fragment implements OnDismissListener {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private WebCam webCam;
	
	public WebCam getWebCam() {
		return webCam;
	}

	ImageView imView;
	TextView imText;
	String waitMessage;
	AsyncTask<String, Integer, Void> async;
	Timer timer = new Timer();

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CameraDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			webCam = getArguments().getParcelable(ARG_ITEM_ID);
		} else
			webCam = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_camera_detail,
				container, false);

		if (webCam != null) {
			imView = (ImageView) rootView.findViewById(R.id.image_viewer_image);
			imText = (TextView) rootView.findViewById(R.id.image_viewer_text);
			waitMessage = getResources().getString(R.string.wait);
		} else
			showErrorImage();

		return rootView;
	}
	
	void launchDownload() {
		if (paused)
			return;
		async = new AsyncTask<String, Integer, Void>() {

			//private ProgressDialog progressDialog;
			
			protected void onPreExecute() {
				super.onPreExecute();
				/*this.progressDialog = ProgressDialog.show(CameraDetailFragment.this.getActivity(), "", waitMessage, true);
				this.progressDialog.setCancelable(true);
				this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				this.progressDialog.setOnDismissListener(CameraDetailFragment.this);
				this.progressDialog.setMessage(waitMessage);
				this.progressDialog.show();*/
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				//this.progressDialog.dismiss();
			}

			@Override
			protected Void doInBackground(String... params) {
				URL myFileUrl = null;
				try {
					myFileUrl = new URL(params[0] + "?time=" + System.currentTimeMillis());
					//Log.i("nantescam", myFileUrl.toString());
					HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream is = conn.getInputStream();

					if (!isCancelled())
						bmImg = BitmapFactory.decodeStream(is);
					if (!isCancelled()) {
						imView.post(new Runnable() {
	
							@Override
							public void run() {
								imText.setText(webCam.getName());
								if (bmImg != null) {
									imView.setImageBitmap(bmImg);
								}
								relaunch();
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
					showErrorImage();
				}
				return null;
			}
		};
		if (webCam != null) {
			async.execute(webCam.getURL());
		}
	}
	
	private void relaunch() {
		final Handler handler = new Handler();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						launchDownload();
					}
				});
				
			}
		}, 3000);
	}
	
	private void showErrorImage() {
		bmImg = BitmapFactory.decodeResource(getResources(), R.drawable.warning);
		imView.post(new Runnable() {
			
			@Override
			public void run() {
				imText.setText(R.string.warning);
				imView.setImageBitmap(bmImg);
				relaunch();
			}
		});
	}
	
	boolean paused;
	Bitmap bmImg;

	void downloadFile(String fileUrl) {
		
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		async.cancel(true);
	}

	@Override
	public void onPause() {
		paused = true;
		super.onPause();
	}

	@Override
	public void onResume() {
		paused = false;
		launchDownload();
		super.onResume();
	}

}
