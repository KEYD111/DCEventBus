package eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dadac.eventbus.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ Create by dadac on 2018/9/13.
 * @Function:
 * @Return:
 */
public class EventBusSendActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.DC_Button_MainThradSend)
    Button DCButtonMainThradSend;
    @BindView(R.id.DC_Button_ReceiveStickyData)
    Button DCButtonReceiveStickyData;
    @BindView(R.id.DC_TextViewShowResult)
    TextView DCTextViewShowResult;

    private boolean isFirstFlag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);
        DCButtonMainThradSend.setOnClickListener(this);
        DCButtonReceiveStickyData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DC_Button_MainThradSend:    //主线程发送数据
                //4、发布消息
                EventBus.getDefault().post(new MessageEvent("主线程发送过来的数据"));
                break;
            case R.id.DC_Button_ReceiveStickyData:   //接受粘性数据
                //44、注册   注册一次就行了注册多次就会报崩溃；
                if (isFirstFlag == true) {
                    EventBus.getDefault().register(EventBusSendActivity.this);
                    isFirstFlag = false;
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //55,解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendActivity.this);
    }

    // 33、接受粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(StickyEventBus stickyEventBus) {
        DCTextViewShowResult.setText(stickyEventBus.msg);
    }

}
