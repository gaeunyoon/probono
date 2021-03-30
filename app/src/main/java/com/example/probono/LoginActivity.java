package com.example.probono;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG="LoginActivity";
    private View loginButton,logoutButton;
    private TextView nickname;
    private ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=findViewById(R.id.kakaologin);
        logoutButton=findViewById(R.id.kakaologout);
        nickname=findViewById(R.id.nickname);
        profile=findViewById(R.id.profile);
        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.i(TAG, "onCreate: keyHash:" + keyHash);
        Function2<OAuthToken, Throwable, Unit> callback=new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken!=null){
                    //
                }
                if(throwable!=null){
                    //
                }
                updateKakao();
                return null;
            }
        };
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                }
                else{
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this,callback);
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakao();
                        return null;
                    }
                });
            }
        });
        updateKakao();
    }

    private void updateKakao(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user!=null){
                    Log.i(TAG,"invoke: id"+user.getId());
                    Log.i(TAG,"invoke: nickname"+user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG,"invoke: E-mail"+user.getKakaoAccount().getEmail());
                    Log.i(TAG,"invoke: gender"+user.getKakaoAccount().getGender());
                    Log.i(TAG,"invoke: age"+user.getKakaoAccount().getAgeRange());
                    nickname.setText(user.getKakaoAccount().getProfile().getNickname());
                    Glide.with(profile).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profile);
                    loginButton.setVisibility(View.GONE);
                    logoutButton.setVisibility(View.VISIBLE);
                }
                else{
                    nickname.setText(null);
                    profile.setImageBitmap(null);
                    loginButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }
}