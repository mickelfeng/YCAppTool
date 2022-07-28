package com.yc.appserver;

import android.view.View;

import com.yc.appserver.log.LogTestActivity;
import com.yc.library.base.mvp.BaseActivity;
import com.yc.roundcorner.view.RoundTextView;
import com.yc.toolutils.click.PerfectClickListener;

public class ServerMainActivity extends BaseActivity {

    private RoundTextView tvLogServer;

    @Override
    public int getContentView() {
        return R.layout.activity_server_main;
    }

    @Override
    public void initView() {
        tvLogServer = findViewById(R.id.tv_log_server);
    }

    @Override
    public void initListener() {
        tvLogServer.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LogTestActivity.Companion.startActivity(ServerMainActivity.this);
            }
        });
    }

    @Override
    public void initData() {

    }
}