package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.ctrl.chat.CommonWordCtrl;
import com.tencent.ieg.ntv.model.chat.CommonWordModel;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.ArrayList;
import java.util.Map;

public class CommonWordView extends ListPopupWindow {
    private static final String TAG = CommonWordView.class.getSimpleName();
    private int HEIGHT = 0;
    private int HORIZONTAL_OFFSET = -300;
    private int PER_HEIGHT = 0;
    private int VERTICAL_OFFSET = -30;
    private int WIDTH = 780;
    Context vContext;

    public CommonWordView(Context context) {
        super(context);
        this.vContext = context;
    }

    public void show(ArrayList<CommonWordModel> words) {
        initValue();
        Logger.i(TAG, "In List Show!");
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        int count = words.size();
        for (int i = 0; i < count; i++) {
            list.add(words.get(i).convertModelToMap());
        }
        setAdapter(new SimpleAdapter(this.vContext, list, R.layout.ntvs_chat_commonword_item, new String[]{"cText"}, new int[]{R.id.cText}));
        setAnchorView(CommonWordCtrl.getInstance().getParentView());
        setModal(true);
        setWidth(this.WIDTH);
        setHeight(this.HEIGHT);
        setHorizontalOffset(this.HORIZONTAL_OFFSET);
        setVerticalOffset(this.VERTICAL_OFFSET);
        setBackgroundDrawable(new ColorDrawable());
        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CommonWordView.this.dismiss();
                CommonWordCtrl.getInstance().onItemClickCallBack(position);
            }
        });
        show();
        getListView().setBackgroundResource(R.drawable.common_tips_frame);
        getListView().setDivider(this.vContext.getResources().getDrawable(R.drawable.chat_area_line));
        getListView().setDividerHeight(2);
        getListView().setFooterDividersEnabled(false);
        Logger.i(TAG, "CommonWordView ListView HEIGHT is  " + getListView().getHeight());
    }

    private int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void initValue() {
        WindowManager wm = (WindowManager) this.vContext.getSystemService("window");
        int width = wm.getDefaultDisplay().getWidth();
        int heigth = wm.getDefaultDisplay().getHeight();
        this.WIDTH = (width * 2) / 5;
        this.PER_HEIGHT = this.vContext.getResources().getDimensionPixelSize(R.dimen.ntvs_chat_common_text_height);
        this.HORIZONTAL_OFFSET = -(this.WIDTH / 2);
        int itemCount = CommonWordCtrl.getInstance().getCommonwords().size();
        this.HEIGHT = (this.PER_HEIGHT * itemCount) + ((itemCount - 1) * 2) + 12;
        Logger.i(TAG, "CommonWordView width & height is " + width + "&&" + heigth);
        Logger.i(TAG, "CommonWordView PER_HEIGHT is " + this.PER_HEIGHT);
        Logger.i(TAG, "CommonWordView HEIGHT is  " + this.HEIGHT);
    }
}
