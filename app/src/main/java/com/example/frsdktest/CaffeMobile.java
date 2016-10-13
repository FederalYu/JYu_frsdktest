package com.example.frsdktest;

import android.os.Environment;

public class CaffeMobile {
	
	static {
	    System.loadLibrary("caffe");
	    System.loadLibrary("caffe_jni");
	}
	
	private String filePath = Environment.getExternalStorageDirectory()+"/FRSDK";
	public long handle = 0;
	
	public CaffeMobile(){
		 enableLog(true);		 
		 handle = Init(filePath+"/dfdat.dat", filePath+"/dfbin.bin");
		 System.out.println("handle:" + handle);
	}
	
	public native void enableLog(boolean enabled);
	public native long Init(String datfile, String binfile);
	public native int Detect(long handle, byte [] BGR, int width, int height, int [] rectarray);		//0为失败 1为检测到人脸
	public native int FeaExtract(long handle, byte [] BGR, int width, int height, double [] fea, int [] m_rect);
	public native int Detectpath(long handle, String imgpath, int [] rectarray);		//0为失败 1为检测到人脸
	public native int FeaExtractpath(long handle, String imgpath, double [] fea, int [] m_rect);
	public native double Similarity(double [] feaA, double [] feaB);
	public native void Destroy(long handle); 
    
}
