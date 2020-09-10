package com.java.yangjianke.weibo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.java.yangjianke.R;
import com.sina.weibo.sdk.api.*;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ShareActivity extends Activity implements WbShareCallback { //View.OnClickListener,
//    private CheckBox mShareText;
//
//    private CheckBox mShareUrl;
//
//    private CheckBox mShareMultiImage;
//
//    private CheckBox mShareVideo;
//
//
//    private Button mCommitBtn;

    //在微博开发平台为应用申请的App Key
    private static final String APP_KY = "4102962125";
    //在微博开放平台设置的授权回调页
    private static final String REDIRECT_URL = "http://www.sina.com";
    //在微博开放平台为应用申请的高级权限
    private static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    private IWBAPI mWBAPI;
    private String url, content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share);
//        mShareText = findViewById(R.id.share_text_cb);
//        mShareUrl = findViewById(R.id.share_url_cb);
//        mShareMultiImage = findViewById(R.id.share_multi_image_cb);
//        mShareVideo = findViewById(R.id.share_video_cb);
//        mCommitBtn = findViewById(R.id.commit);
//        mCommitBtn.setOnClickListener(this);
        initSdk();
        mWBAPI.setLoggerEnable(true);
        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<String> data = bundle.getStringArrayList("data");
        url = data.get(0);
        content = data.get(1);
        doWeiboShare();
    }

//    @Override
//    public void onClick(View v) {
//        if (v == mCommitBtn) {
//            doWeiboShare();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mWBAPI.doResultIntent(data, this);
    }

    //init sdk
    private void initSdk() {
        AuthInfo authInfo = new AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE);
        mWBAPI = WBAPIFactory.createWBAPI(this);
        mWBAPI.registerApp(this, authInfo);
    }


    private void doWeiboShare() {
        WeiboMultiMessage message = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        String text = "我正在使用微博客户端发博器分享文字。";

        // 分享文字
//        if (mShareText.isChecked()) {
        text = "在使用新冠疫情新闻APP看新闻！\n" +
                "这条新闻的内容是： " + content + "\n" +
                "URL: " + url;
        textObject.text = text.substring(0, 240);
        message.textObject = textObject;
//        }


//        // 分享网页
//        if (mShareUrl.isChecked()) {
//            WebpageObject webObject = new WebpageObject();
//            webObject.identify = UUID.randomUUID().toString();
//            webObject.title = "标题";
//            webObject.description = "描述";
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//            ByteArrayOutputStream os = null;
//            try {
//                os = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
//                webObject.thumbData = os.toByteArray();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (os != null) {
//                        os.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            webObject.actionUrl = url;
//            webObject.defaultText = "分享网页";
//            message.mediaObject = webObject;
//        }
//
//        if (mShareMultiImage.isChecked()) {
//            // 分享多图
//            MultiImageObject multiImageObject = new MultiImageObject();
//            ArrayList<Uri> list = new ArrayList<>();
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/aaa.png")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/ccc.JPG")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/ddd.jpg")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/fff.jpg")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/ggg.JPG")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/eee.jpg")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/hhhh.jpg")));
//            list.add(Uri.fromFile(new File(getExternalFilesDir(null) + "/kkk.JPG")));
//            multiImageObject.imageList = list;
//            message.multiImageObject = multiImageObject;
//        }
//
//        if (mShareVideo.isChecked()) {
//            // 分享视频
//            VideoSourceObject videoObject = new VideoSourceObject();
//            videoObject.videoPath = Uri.fromFile(new File(getExternalFilesDir(null) + "/eeee.mp4"));
//            message.videoSourceObject = videoObject;
//        }

        mWBAPI.shareMessage(message, false);
    }

    @Override
    public void onComplete() {
        finish();
    }

    @Override
    public void onError(UiError error) {
        finish();
    }

    @Override
    public void onCancel() {
        finish();
    }
}
