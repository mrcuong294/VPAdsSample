package com.nguyencuong.vpadssample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.valuepotion.sdk.AdContainer;
import com.valuepotion.sdk.AdDimension;
import com.valuepotion.sdk.AdListener;
import com.valuepotion.sdk.VPAdView;
import com.valuepotion.sdk.ValuePotion;
import com.valuepotion.sdk.ad.AdRequestOptions;

import static com.nguyencuong.vpadssample.Constants.VP_CLIENT_ID;
import static com.nguyencuong.vpadssample.Constants.VP_SECRET_KEY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout bottomAdsLayout;

    private Button btnReloadAds;

    private AdRequestOptions vpAdOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValuePotion.init(this, VP_CLIENT_ID, VP_SECRET_KEY);

        bottomAdsLayout = (FrameLayout) findViewById(R.id.home_ads_layout);
        btnReloadAds = (Button) findViewById(R.id.home_btn_reload_ads);
        btnReloadAds.setOnClickListener(this);

        ValuePotion.getInstance().setInterstitialPlacement(this, "home_page");
        ValuePotion.getInstance().cacheEndingInterstitial(this);
        addBottomAdView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ValuePotion.getInstance().onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VPAdView.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VPAdView.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ValuePotion.getInstance().onStop(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ValuePotion.getInstance().onBackPressed(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnReloadAds) {
            ValuePotion.getInstance().setInterstitialPlacement(this, "home_page");
            ValuePotion.getInstance().requestAd(vpAdOption);
        }
    }

    private void addBottomAdView() {
        vpAdOption = new AdRequestOptions.Builder(this, "home_bottom_banner", AdDimension.BANNER, new AdListener() {
            @Override
            public void adPrepared(AdContainer adContainer) {
                VPAdView vpAdView = new VPAdView(MainActivity.this); // Tạo một AdView.
                bottomAdsLayout.addView(vpAdView);             // Chèn một AdView vào một ParentView thích hợp.
                vpAdView.load(adContainer.popAd());         // Khởi động một quảng cáo từ adContainer và tải nó trong vpAdView.
            }

            @Override
            public void adNotFound() {

            }
        }).build(); // Xây dựng tùy chọn.
        ValuePotion.getInstance().requestAd(vpAdOption);
    }
}
