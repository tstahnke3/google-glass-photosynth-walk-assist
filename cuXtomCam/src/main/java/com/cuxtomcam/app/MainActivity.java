package com.cuxtomcam.app;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.ImageView;

import com.glass.cuxtomcam.CuxtomCamActivity;
import com.glass.cuxtomcam.constants.CuxtomIntent;
import com.glass.cuxtomcam.constants.CuxtomIntent.CAMERA_MODE;
import com.glass.cuxtomcam.constants.CuxtomIntent.FILE_TYPE;
import com.google.android.glass.sample.level.LevelService;
import com.google.android.glass.touchpad.Gesture;

public class MainActivity extends Activity {
	private final int CUXTOM_CAM_REQUEST = 1111;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageView = (ImageView) findViewById(R.id.mImageView);
		startCuxtomCam();
        //Gesture myGesture = Gesture.TAP;
        //issueKey(27);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void startCuxtomCam() {
		String folder = Environment.getExternalStorageDirectory()
				+ File.separator + Environment.DIRECTORY_PICTURES
				+ File.separator + "CuxtomCam Sample";
		Intent intent = new Intent(getApplicationContext(),
				CuxtomCamActivity.class);
		intent.putExtra(CuxtomIntent.CAMERA_MODE, CAMERA_MODE.PHOTO_MODE);
		//intent.putExtra(CuxtomIntent.ENABLE_ZOOM, true);
		intent.putExtra(CuxtomIntent.FILE_NAME, "walk");
		//intent.putExtra(CuxtomIntent.VIDEO_DURATION, 20);
		intent.putExtra(CuxtomIntent.FOLDER_PATH, folder);
		startActivityForResult(intent, CUXTOM_CAM_REQUEST);
        activityActive = true;
        //startService(new Intent(this, LevelService.class));
	}


    private void issueKey(int keyCode)
    {
        try {
            java.lang.Process p = java.lang.Runtime.getRuntime().exec("input keyevent " + Integer.toString(keyCode) + "\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    Timer timer;
     public void CreateWalkTask(int seconds) {
         if (timer != null)
             timer.cancel();
        timer = new Timer();
        timer.schedule(new WalkTask(), seconds * 1250);
    }
    boolean activityActive = false;

    class WalkTask extends TimerTask {
        public synchronized void run() {
           // System.out.println("Time's up!");
            if (activityActive)
                issueKey(27);
            //timer.cancel(); //Not necessary because we call System.exit
            //System.exit(0); //Stops the AWT thread (and everything else)
        }
    }
    @Override
    protected void onStart() {
        //CreateWalkTask(9);
        timer = new Timer();
        super.onStart();

    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        activityActive = false;
		/*if (requestCode == CUXTOM_CAM_REQUEST) {
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra(CuxtomIntent.FILE_PATH);
				int FIleType = data.getIntExtra(CuxtomIntent.FILE_TYPE,
						FILE_TYPE.PHOTO);
				if (FIleType == FILE_TYPE.PHOTO) {

					//BitmapFactory.Options o = new BitmapFactory.Options();
					//o.inSampleSize = 4;
					//Bitmap bmp = BitmapFactory.decodeFile(path, o);
					//mImageView.setImageBitmap(bmp);
				} else {
					//Bitmap bmp = ThumbnailUtils
					//		.createVideoThumbnail(
					//				path,
					//				android.provider.MediaStore.Video.Thumbnails.MINI_KIND);
					//mImageView.setImageBitmap(bmp);
				}



			}
		}*/

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUXTOM_CAM_REQUEST) {
            if (resultCode == RESULT_OK) {

                String folder = Environment.getExternalStorageDirectory()
                        + File.separator + Environment.DIRECTORY_PICTURES
                        + File.separator + "CuxtomCam Sample";
                Intent intent = new Intent(getApplicationContext(),
                        CuxtomCamActivity.class);
                intent.putExtra(CuxtomIntent.CAMERA_MODE, CAMERA_MODE.PHOTO_MODE);
                //intent.putExtra(CuxtomIntent.ENABLE_ZOOM, true);
                intent.putExtra(CuxtomIntent.FILE_NAME, "walk");
                //intent.putExtra(CuxtomIntent.VIDEO_DURATION, 20);
                intent.putExtra(CuxtomIntent.FOLDER_PATH, folder);
                startActivityForResult(intent, CUXTOM_CAM_REQUEST);
                activityActive = true;

                CreateWalkTask(1);
            }
        }
	}
}
