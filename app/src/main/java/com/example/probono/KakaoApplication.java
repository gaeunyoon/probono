package com.example.probono;
import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "7973b58ff7dda235011235c1a6721ff3");
    }
}
