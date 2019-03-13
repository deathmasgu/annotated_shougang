package com.newgen.tools;

import java.util.ArrayList;
import java.util.List;

import com.newgen.domain.Category;
import com.newgen.domain.Member;
import com.newgen.sg_news.activity.BuildConfig;

import android.graphics.drawable.Drawable;
import android.os.Environment;

public final class PublicValue {

    public static boolean DEBUG = false;

    public static List<Category> CATEGORYS = new ArrayList<Category>();

    /**
     * 今日首钢
     */

    // 内网
//	public static String ADDRESS = "192.168.66.188:8080";
//	public static String BASEURL = "http://" + ADDRESS+ "/sgApp/hw/";
    public static String DEBUG_IP = "http://192.168.66.188:8081/sgApp/";

    //外网
    public static String PRODUCTION_DOMAIN = "jr.shougang.com.cn:8080";
    public static String ADDRESS = "220.194.33.40:8080";
    public static String BASEURL = DEBUG ? DEBUG_IP : "http://" + PRODUCTION_DOMAIN + "/sgApp/";


    public static String IMGSERVER = "220.194.33.40:8099";


//	public static String SERVERBASEURL = "http://"+ADDRESS+"/JBNewsAppServer/";
//	public static String HEADSTRINGURL=SERVERBASEURL+"/head/";

    /**
     * 稿件提交
     */
//	http://210.76.60.100:4401/JBNewsAppServer/
    public static String BAOCOMMTIEURL = BASEURL;
    /**
     * 边看边聊列表
     */
//	public static String CHATURL ="http://"+ADDRESS+"/JBNewsAppServer/getLiveNewsDetail.do?newsid=";
    public static String CHATURL = BASEURL + "/getLiveNewsDetail.do?newsid=";

    public static String IMAGE_SERVER_PATH = "";//"http://"+IMGSERVER+"/img";
    public static String IMAGESERVERPATH = "http://" + IMGSERVER + "/img/M/";
    public static String IMAGESERVERPATH_BIG = "http://" + IMGSERVER + "/img/B/";
    public static String MYUPLOADIMAGEPATH = "http://" + IMGSERVER + "/img/M/";

    public static String SHAREUSPATH = BASEURL + "html/aboutus.htm";

    public static String IMG_SIZE_S = "/S_";
    public static String IMG_SIZE_M = "/M_";
    public static String IMG_SIZE_L = "/L_";

    public static void ToTestModle() {
        BASEURL = "http://" + ADDRESS + "/JBNewsAppServer/";
//		SERVERBASEURL = "http://"+ADDRESS+"/";
        IMAGESERVERPATH = "http://" + IMGSERVER + "/img/M/";
        IMAGESERVERPATH_BIG = "http://" + IMGSERVER + "/img/B/";
        MYUPLOADIMAGEPATH = "http://" + IMGSERVER + "/img/M/";
    }


    public static Drawable IMAGE;
    public static int WIDTH;
    public static int HEIGHT;

    /***
     * 手机唯一ID
     */
    public static String HARDID = "xxxx";
    public static Member USER;

    public static float SUPERBIG = 26;
    public static float BIG = 22;
    public static float MIDDEL = 18;
    public static float SMALL = 14;

    public static float NORMAL = 18;

    public static boolean PLAYCONTINUOUS = true;//音频循环播放
    public static boolean ISPLAYING = false;//是否正在播放
    public static String PATH = "isSamePath";//是否为同一首音频

    public static boolean haveNewsVision = false;
    public static String lastVersion = "";

    public static boolean WIFIOPEND = false;//wifi是否打开

    public static int RecommendshowType = 4;

    /***
     * 临时图片
     */
    public static String tempImage = Environment.getExternalStorageDirectory().getPath()
            + "/sgrb_temp/";  //"/sdcard/sgrb_temp/";

    /**
     * 缓存栏目数据时的键
     */
    public static final String WORD_NEWS_CATEGORY_FILE = "word_news_category";
    public static final String WORD_NEWS_CATEGORY_FILEEE = "word_news_categoryyy";


    /* 新闻展示类型 */
    public static final int NEWS_TYPE_RUNIMAGE = 0;// 轮换图
    public static final int NEWS_TYPE_COMMON = 1;//普通新闻
    public static final int NEWS_TYPE_IMG = 2;// 图片新闻
    //	public static final int NEWS_TYPE_VOICE = 6; // 音频
    public static final int NEWS_TYPE_VIDEO = 7;// 视频
    public static final int NEWS_TYPE_EXPAND = 8; // 推广
    public static final int NEWS_TYPE_BIG_IMA = 9;// 大图文字新闻

    public static final int NEWS_TYPE_IMG_TWO = 10;// 两张图
    public static final int NEWS_TYPE_IMG_FOUR = 11; // 四张图

//	public static final int NEWS_TYPE_IMG_FACE=12;// 专题头部大图


    /* 新闻类型 */
    public static final int NEWS_STYLE_WORD = 0;
    public static final int NEWS_STYLE_IMG = 1;
    public static final int NEWS_STYLE_VIDEO = 2;
    public static final int NEWS_STYLE_AUDIO = 3;
    public static final int NEWS_STYLE_IMG_LIVE = 4;
    public static final int NEWS_STYLE_READPAPER = 6;// 读报
    public static final int NEWS_STYLE_LINK = 7;// 超链


}
