package com.example.frsdktest;
import android.os.Message;
import android.util.Log;

public class FRSDKThread implements Runnable{

	private static final String TAG = FRSDKThread.class.getSimpleName();
	private CaffeMobile caffeMobile;
	private MainActivity activity;
	private static int FACEFEATURESIZE = 512;
	public FRSDKThread(MainActivity activity){
		this.activity = activity;
	}
	
	
	 public void test(CaffeMobile caffeMobile)
	 {
		    Message message;
		    double [] feaA = new double[FACEFEATURESIZE];
		    double [] feaB = new double[FACEFEATURESIZE];
		    int [] rectarrayA = new int[5];	//left top width height nQuality
		    int [] rectarrayB = new int[5]; //left top width height nQuality
		    int flagA;
		    int flagB;

		    //A图：检测人脸，提取特征
		    flagA = caffeMobile.Detectpath(caffeMobile.handle, "/mnt/sdcard/FRSDK/01_ID.jpg", rectarrayA);
			//int flagA = caffeMobile.Detect(caffeMobile.handle, byteA, widthA, heightA, rectarrayA);
			if(flagA == 1)
			{
				Log.d(TAG, rectarrayA[0]+" "+rectarrayA[1] + " " + rectarrayA[2] + " " + rectarrayA[3]);
				flagA = caffeMobile.FeaExtractpath(caffeMobile.handle, "/mnt/sdcard/FRSDK/01_ID.jpg", feaA, rectarrayA);
				//caffeMobile.FeaExtract(caffeMobile.handle, byteA, widthA, heightA, feaA, rectarrayA);
			}
			else
			{
				Log.d(TAG, "detect no face");
				return;
			}

			//B图：检测人脸，提取特征
			flagB = caffeMobile.Detectpath(caffeMobile.handle, "/mnt/sdcard/FRSDK/01_C.jpg", rectarrayB);
			if(flagB == 1)
			{
				Log.d(TAG, rectarrayB[0]+" "+rectarrayB[1] + " " + rectarrayB[2] + " " + rectarrayB[3]);
				flagB = caffeMobile.FeaExtractpath(caffeMobile.handle, "/mnt/sdcard/FRSDK/01_C.jpg", feaB, rectarrayB);
			}
			else
			{
				Log.d(TAG, "detect no face");
				return;
			}

			if(flagA == 1 && flagB == 1)
			{
				double  score = caffeMobile.Similarity(feaA, feaB);
				message = Message.obtain(activity.getHandler(),R.id.finish_test_ComputeSimCos,score);
				message.sendToTarget();

			}

			
			
	 }

	@Override
	public void run() {
		
		caffeMobile = new CaffeMobile();
		for(int i = 0 ; i < 99; i++)
		{
			test(caffeMobile);
		}
		caffeMobile.Destroy(caffeMobile.handle);
		Log.d(TAG, "release and finished!");
		
	}

	
}
