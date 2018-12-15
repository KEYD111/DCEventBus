package com.example.dadac.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import eventbus.EventBusSendActivity;
import eventbus.MessageEvent;
import eventbus.StickyEventBus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //绑定
    @BindView(R.id.DC_Button_EventBus)
    Button DCButtonEventBus;
    @BindView(R.id.DC_Button_EventBusSticky)
    Button DCButtonEventBusSticky;
    @BindView(R.id.DC_TextViewShowReceive)
    TextView DCTextViewShowReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DCButtonEventBus.setOnClickListener(this);
        DCButtonEventBusSticky.setOnClickListener(this);

        //1、注册广播事件
        EventBus.getDefault().register(MainActivity.this);
        //2、解注册
        //3、构建发送消息的类 MessageEvent
        //4、发布消息  在别的页面里面，一点击，数据就被传出去，，省的 intent bundle  啥啥的了
        //5、接受消息


    }


    //5、接受消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent messageEvent) {
        //显示接受的消息
        DCTextViewShowReceive.setText(messageEvent.name);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //2、解注册
        EventBus.getDefault().unregister(MainActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DC_Button_EventBus:   //接受EventBus发送的消息
                Intent myIntent1 = new Intent(this, EventBusSendActivity.class);
                startActivity(myIntent1);
                break;
            case R.id.DC_Button_EventBusSticky:   //发送粘性事件跳转到发送页面
                //22、发送粘性事件
                EventBus.getDefault().postSticky(new StickyEventBus("我是粘性事件"));
                //直接跳转到发送数据的页面
                Intent myIntent2 = new Intent(this, EventBusSendActivity.class);
                startActivity(myIntent2);
                break;
            default:
                break;
        }
    }
}
