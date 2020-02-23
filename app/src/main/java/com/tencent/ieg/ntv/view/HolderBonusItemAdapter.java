package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.model.NTVDefine;
import java.util.ArrayList;

public class HolderBonusItemAdapter extends ArrayAdapter<HolderBonusItemInfo> {
    public HolderBonusItemAdapter(@NonNull Context context, ArrayList<HolderBonusItemInfo> bonus) {
        super(context, 0, bonus);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new HolderBonusItemView(getContext());
        }
        HolderBonusItemInfo holderBonusItemInfo = (HolderBonusItemInfo) getItem(position);
        HolderBonusItemView holderBonusItemView = (HolderBonusItemView) convertView;
        holderBonusItemView.setOtherDataForUse(holderBonusItemInfo.itemid, holderBonusItemInfo.status);
        holderBonusItemView.setBonusIcon(holderBonusItemInfo.iconUrl);
        holderBonusItemView.setBonusSubTitle(holderBonusItemInfo.itemName);
        holderBonusItemView.setBonusTitle(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_DESC).replace("{0}", String.valueOf(holderBonusItemInfo.getMinute())));
        if (position == getCount() - 1) {
            holderBonusItemView.setDividerVisibility(8);
        } else {
            holderBonusItemView.setDividerVisibility(0);
        }
        holderBonusItemView.setBonusBtnStatus(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_BTNRECEIVE), holderBonusItemInfo.status);
        return convertView;
    }
}
