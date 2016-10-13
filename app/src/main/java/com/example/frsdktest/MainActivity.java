package com.example.frsdktest;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private CaptureHandler handler;
    public String filePath = Environment.getExternalStorageDirectory()+"/FRSDK/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.logText);
        createFile();
        handler = new CaptureHandler();
        FRSDKThread thread = new FRSDKThread(this);
        new Thread(thread).start();
    }

    class CaptureHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {
                case R.id.start_init_caffe:
                    mTextView.append("\nstart init Caffe");
                    break;
                case R.id.finish_init_caffe:
                    mTextView.append("\nfinish init Caffe");
                    break;
                case R.id.start_test_ExtracFea:
                    mTextView.append("\nstart test ExtracFea Interface");
                    break;
                case R.id.finish_test_ExtractFea:
                    mTextView.append("\nfinish test ExtracFea Interface");
                    break;
                case R.id.start_test_ComputeSimCos:
                    mTextView.append("\nstart test ComputeSimCos Interface");
                    break;
                case R.id.finish_test_ComputeSimCos:
                    mTextView.append("\nfinish test ComputeSimCose Interface"
                    		+"\nThe Score is: "+ msg.obj);
                    break;
                default:
                    break;
            }
        }
    }


    public Handler getHandler(){
        return handler;
    }
    private void createFile() {

        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                System.out.println("要存储的目录不存在");
                if (dir.mkdirs()) {
                    System.out.println("已经创建文件存储目录");
                } else {
                    System.out.println("创建目录失败");
                }
            }
            String datfilepath = filePath+"dfdat.dat";
            String binfilepath = filePath+"dfbin.bin";

            File datfile = new File(datfilepath);
            File binfile = new File(binfilepath);

            if(!datfile.exists() || !binfile.exists() ){
                System.out.println("要打开的文件不存在");

                InputStream datins = getResources().openRawResource(R.raw.dfdat);
                InputStream binins = getResources().openRawResource(R.raw.dfbin);

                System.out.println("开始读入");
                FileOutputStream datos = new FileOutputStream(datfile);
                FileOutputStream binos = new FileOutputStream(binfile);
                byte[] datbuffer = new byte[8192];
                byte[] binbuffer = new byte[8192];
                System.out.println("开始写出");
                int count = 0;
                while ((count = datins.read(datbuffer)) > 0) {
                    datos.write(datbuffer, 0, count);
                }
                count = 0;
                while ((count = binins.read(binbuffer)) > 0){
                    binos.write(binbuffer, 0, count);
                }


                System.out.println("已经创建目录下的两个文件");
                datins.close();
                binins.close();

                datos.close();
                binos.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
