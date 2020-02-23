package com.tencent.ieg.ntv.ctrl.chat;

import android.content.Context;
import android.view.View;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventGeneralWordData;
import com.tencent.ieg.ntv.model.chat.CommonWordModel;
import com.tencent.ieg.ntv.network.BaseInfo;
import com.tencent.ieg.ntv.network.NetworkModule;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.view.CommonWordView;
import java.util.ArrayList;
import java.util.List;

public class CommonWordCtrl {
    /* access modifiers changed from: private */
    public static final String TAG = CommonWordCtrl.class.getSimpleName();
    private static CommonWordCtrl _instance = null;
    private ArrayList<CommonWordModel> commonwords = new ArrayList<>();
    private Context context = null;
    private IEventListener mGeneralWordUpdater = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            Logger.d(CommonWordCtrl.TAG, "mGeneralWordUpdater onEvent");
            CommonWordCtrl.this.onGeneralWordList(((EventGeneralWordData) event).generalWordList);
        }
    };
    private View parentView = null;
    private CommonWordView popView = null;

    public static CommonWordCtrl getInstance() {
        if (_instance == null) {
            _instance = new CommonWordCtrl();
        }
        return _instance;
    }

    public void CommonWordCtrl() {
    }

    public View getParentView() {
        return this.parentView;
    }

    public void setParentView(View parentView2) {
        this.parentView = parentView2;
    }

    public ArrayList<CommonWordModel> getCommonwords() {
        return this.commonwords;
    }

    public void setCommonwords(ArrayList<CommonWordModel> commonwords2) {
        this.commonwords = commonwords2;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public CommonWordView getPopView() {
        return this.popView;
    }

    public void setPopView(CommonWordView popView2) {
        this.popView = popView2;
    }

    private void showCommonView(ArrayList<CommonWordModel> words) {
        this.popView.show(words);
    }

    private boolean convertToModel(List<String> wordList) {
        boolean convertSuccess;
        if (this.commonwords == null) {
            this.commonwords = new ArrayList<>();
        }
        this.commonwords.clear();
        if (wordList == null || wordList.size() == 0) {
            Logger.w(TAG, "BaseInfo.getGeneralWordList() is null Or size is 0");
            convertSuccess = false;
        } else {
            for (int i = 0; i < wordList.size(); i++) {
                this.commonwords.add(new CommonWordModel(wordList.get(i)));
            }
            convertSuccess = true;
        }
        if (convertSuccess) {
            return convertSuccess;
        }
        this.commonwords.clear();
        this.commonwords.add(new CommonWordModel().convertStringToModel("这里是测试数据"));
        this.commonwords.add(new CommonWordModel().convertStringToModel("你好"));
        this.commonwords.add(new CommonWordModel().convertStringToModel("这是一个很好很好的问候"));
        this.commonwords.add(new CommonWordModel().convertStringToModel("留下你的联系方式"));
        this.commonwords.add(new CommonWordModel().convertStringToModel("我的电话是xxxx"));
        return true;
    }

    public void onGeneralWordList(List<String> generalWordList) {
        if (convertToModel(generalWordList)) {
            showCommonView(this.commonwords);
        } else {
            Logger.w(TAG, "BaseInfo.GeneralWordListListener() onGeneralWordList Error");
        }
    }

    public void showCommonWord(Context context2, View parentView2) {
        setContext(context2);
        setParentView(parentView2);
        this.popView = new CommonWordView(getContext());
        EventManager.getInstance().register(5002, this.mGeneralWordUpdater);
        BaseInfo baseInfo = NetworkModule.getInstance().getBaseInfo();
        if (baseInfo != null) {
            EventGeneralWordData generalWordData = new EventGeneralWordData();
            if (generalWordData.parse(baseInfo.getGeneralWordArray())) {
                Logger.d(TAG, "EventGeneralWordData parse success");
                onGeneralWordList(generalWordData.generalWordList);
            }
        }
    }

    public void onItemClickCallBack(int position) {
        ChatManager.GetInstance().sendNewMsg(getInstance().getCommonwords().get(position).get_text());
    }
}
