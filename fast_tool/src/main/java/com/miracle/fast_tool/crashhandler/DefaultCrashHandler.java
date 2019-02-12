package com.miracle.fast_tool.crashhandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DefaultCrashHandler implements Thread.UncaughtExceptionHandler, ICrashHandler {

    private static final String TAG = "CrashHandler";
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashTest/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".txt";

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    @Override
    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void dumpExceptionToSdcard(Throwable ex) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "sdcard unmounted, skip dump exception ");
            return;
        }
        File dir = new File(PATH);
        if (!dir.exists())
            dir.mkdirs();
        long current = System.currentTimeMillis();
        String time = formatter.format(current);
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
            Log.e(TAG, "dump crash info success");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "dump crash info failed");
        }
    }

    @Override
    public void dumpExceptionToCloud(Throwable e) {

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpExceptionToSdcard(e);
        dumpExceptionToCloud(e);
        if (mDefaultCrashHandler != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(2000);
                Toast.makeText(mContext, "程序出错了~", Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e1) {
                Log.e(TAG, "error: ", e);
            }
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        //android版本号
        pw.print("Android Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        //CPU架构
        pw.print("CPU　ABI： ");
        pw.println(Build.CPU_ABI);
    }
}
