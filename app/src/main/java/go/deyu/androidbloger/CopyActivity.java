package go.deyu.androidbloger;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy);
    }
    public void copyFile(View v){
        goCopyDirAFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
    }

    public void copyDir(View v){
        GoCopyFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"testCopyDir");
    }

    private void goCopyDirAFile(String path){
        File directory = new File(path);
        String[] filenames  = directory.list();
        for( String fileName : filenames){
//            忽略系統檔案
            if(fileName.charAt(0)=='.')
                continue;
            GoCopyFile(path+"/"+fileName,path+"/"+"GoCopyFile"+fileName);
            return ;
        }
    }
    public void GoCopyFile(String comepath, String gopath)  {
        Log.e(this.getClass().getSimpleName(),comepath);
        Log.e(this.getClass().getSimpleName(),gopath);
        try {
            File wantfile = new File(comepath);
            File newfile = new File(gopath);

            InputStream in = new FileInputStream(wantfile);
            OutputStream out = new FileOutputStream(newfile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            Log.e("copy file error", e.toString());
        }
    }

// TODO: 整個資料夾的複製齁，comePath輸入想要複製的資料夾路徑，goPath是目的地路徑拉


    public void GoCopyFolder(String comePath, String goPath){
        try {
            new File(goPath).mkdirs(); //弄資料夾拉
            File a= new File(comePath);
            String[] file=a.list();
            File temp= null ;
            for ( int i = 0 ; i < file.length; i++) {
                if (comePath.endsWith(File.separator)){
                    temp= new File(comePath+file[i]);
                }
                else
                {
                    temp= new File(comePath+File.separator+file[i]);
                }
                if (temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(goPath + "/" +
                            (temp.getName()).toString());
                    byte [] b = new byte [1024];
                    int len;
                    while ( (len = input.read(b)) != - 1 ) {
                        output.write(b, 0 , len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()){
                    //裡面還有東西要繼續幹

                    GoCopyFolder(comePath+ "/" +file[i],goPath+ "/" +file[i]);
                }
            }
        }
        catch (Exception e) {
            Log.e("folder error", e.toString());
        }
    }

}
