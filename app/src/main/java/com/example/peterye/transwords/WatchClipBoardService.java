package com.example.peterye.transwords;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 *  Watch for the clipBoard, if copy a new word,
 *  we translate it using online dictionary
 *  @author  ochar_bird
 */
public class WatchClipBoardService extends Service {

    public  static ClipboardManager cm;
    private  static Translator translator;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String destTxt = data.getString("dest");
            Toast.makeText(getApplicationContext(),
                    destTxt,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(cm == null) {
            cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            try {
                translator = Translator.getInstance(Constant.EN_LAN,
                        Constant.ZH_LAN);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),
                        "加载翻译组件出错",Toast.LENGTH_SHORT);
                return Service.START_STICKY;
            }
            cm.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    String txt = null;

                    if(cm.hasPrimaryClip()) {
                        ClipData data = cm.getPrimaryClip();
                        ClipData.Item item = data.getItemAt(0);

                        //  Must check the getText value, it can be null
                        if (item.getText() != null) {
                            txt = item.getText().toString();
                            // Translate the text in new Thread
                            TransTask task = new TransTask();
                            task.setPreTxt(txt);
                            new Thread(task).start();
                        }
                    }

                }
            });

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    class TransTask implements Runnable {
        private  String preTxt; // 要翻译的文本

        public void setPreTxt(String txt){
            this.preTxt = txt;
        }

        @Override
        public void run() {
            // 翻译任务
            String destTxt = "";
            try{
                destTxt = translator.translate(preTxt);
            }
            catch (Exception e){
                destTxt =  "翻译出错，请连网后重试";
                e.printStackTrace();
            }
            Message msg = handler.obtainMessage();
            Bundle data = new Bundle();
            data.putString("dest",destTxt);
            msg.setData(data);
            handler.sendMessage(msg);

        }
    }

}
