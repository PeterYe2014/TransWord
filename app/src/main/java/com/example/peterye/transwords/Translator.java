package com.example.peterye.transwords;

import com.lsj.trans.Dispatch;


import static com.example.peterye.transwords.Constant.*;


/**
 * Translate English to Chinese
 * Created by PeterYe on 2017/3/11.
 */

public class Translator {

    private String srcLan;
    private String destLan;
    /**
     * 定义翻译平台
     */
    private enum TranPlatform{GOOGLE,BAIDU,JINSHAN,YOUDAO};
    private TranPlatform platform;

    private static  Dispatch dispatch;

    private Translator(String src, String dest) throws  Exception{
        this.srcLan = src;
        this.destLan = dest;
        this.platform = TranPlatform.GOOGLE;
        switch (platform){
            case GOOGLE:
                Class.forName(GOOGLE_CLASS_NAME);
                dispatch = Dispatch.Instance("google");
                break;
            case BAIDU:
                Class.forName(BAIDU_CLASS_NAME);
                dispatch = Dispatch.Instance("baidu");
                break;
            case JINSHAN:
                Class.forName(JINSHAN_CLASS_NAME);
                dispatch = Dispatch.Instance("jinshan");
                break;
            case YOUDAO:
                Class.forName(YOUDAO_CLASS_NAME);
                dispatch = Dispatch.Instance("youdao");
                break;
        }

    }
    public static Translator getInstance(String src, String dest) throws Exception{

        return  new Translator(src,dest);
    }

    public String translate(String text) throws  Exception{

        return dispatch.Trans(srcLan,destLan,text);
    }
}
