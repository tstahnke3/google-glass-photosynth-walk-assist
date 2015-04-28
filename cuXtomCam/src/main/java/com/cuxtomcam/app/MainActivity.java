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
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.glass.cuxtomcam.CuxtomCamActivity;
import com.glass.cuxtomcam.constants.CuxtomIntent;
import com.glass.cuxtomcam.constants.CuxtomIntent.CAMERA_MODE;
import com.glass.cuxtomcam.constants.CuxtomIntent.FILE_TYPE;
import com.google.android.glass.sample.level.LevelService;
import com.google.android.glass.touchpad.Gesture;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class MainActivity extends Activity {
	private final int CUXTOM_CAM_REQUEST = 1111;
	private ImageView mImageView;

    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    //Log.i(TAG, "OpenCV loaded successfully");
                    // Create and set View
                    setContentView(R.layout.activity_main);
                    mImageView = (ImageView) findViewById(R.id.mImageView);
                    startCuxtomCam();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mOpenCVCallBack))
        {
            //Log.e(TAG, "Cannot connect to OpenCV Manager");
        }

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
		if (requestCode == CUXTOM_CAM_REQUEST) {
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra(CuxtomIntent.FILE_PATH);
				int FIleType = data.getIntExtra(CuxtomIntent.FILE_TYPE,
						FILE_TYPE.PHOTO);
				if (FIleType == FILE_TYPE.PHOTO) {

					/*BitmapFactory.Options o = new BitmapFactory.Options();
					o.inSampleSize = 4;
					Bitmap bmp = BitmapFactory.decodeFile(path, o);

                    Mat myMat = new Mat(bmp.getWidth(), bmp.getHeight(), CvType.CV_8UC3);
                    org.opencv.android.Utils.bitmapToMat(bmp, myMat);
                    Mat blurred =  new Mat(bmp.getWidth(), bmp.getHeight(), CvType.CV_8UC3);
                    Mat blurred2 =  new Mat(bmp.getWidth(), bmp.getHeight(), CvType.CV_8UC1);
                    org.opencv.imgproc.Imgproc.cvtColor(myMat, blurred2, org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY);
                    Size mySize = new Size();
                    double[] mySizeDouble = {3,3};
                    mySize.set(mySizeDouble);
                    org.opencv.imgproc.Imgproc.blur(blurred2, blurred, mySize);
                    org.opencv.imgproc.Imgproc.Canny(blurred, blurred, 50*1.0, 50*3*1.0, 3, true);
                    for (int j = 0; j < blurred.rows(); j++) {
                        for (int i = 0; i < blurred.cols(); i++) {
                            if (blurred.get(j,i)[0] > 0)
                            {
                                double[]tmpDouble = {255.0};
                                blurred.put(j,i, tmpDouble);
                            }

                        }

                    }
                    org.opencv.android.Utils.matToBitmap(blurred, bmp);

                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    mImageView.setImageBitmap(bmp);
                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);*/
				} else {
					//Bitmap bmp = ThumbnailUtils
					//		.createVideoThumbnail(
					//				path,
					//				android.provider.MediaStore.Video.Thumbnails.MINI_KIND);
					//mImageView.setImageBitmap(bmp);
				}



			}
		}

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUXTOM_CAM_REQUEST) {
            if (resultCode == RESULT_OK) {

                /*String folder = Environment.getExternalStorageDirectory()
                        + File.separator + Environment.DIRECTORY_PICTURES
                        + File.separator + "CuxtomCam Sample";
                Intent intent = new Intent(getApplicationContext(),
                        CuxtomCamActivity.class);
                intent.putExtra(CuxtomIntent.CAMERA_MODE, CAMERA_MODE.PHOTO_MODE);
                //intent.putExtra(CuxtomIntent.ENABLE_ZOOM, true);
                intent.putExtra(CuxtomIntent.FILE_NAME, "walk");
                //intent.putExtra(CuxtomIntent.VIDEO_DURATION, 20);
                intent.putExtra(CuxtomIntent.FOLDER_PATH, folder);
                startActivityForResult(intent, CUXTOM_CAM_REQUEST);*/
                activityActive = true;
                //CreateWalkTask(1);
            }
        }
	}
}
