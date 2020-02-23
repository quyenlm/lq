package com.tencent.midas.oversea.data.mp;

import android.text.TextUtils;
import java.util.ArrayList;

public class APMPSendItem {
    public static final int SEND_POINT_FLAG = 2;
    public static final int SEND_SECTION_FLAG = 1;
    private boolean a = true;
    public int sectionOrPointFlag = 1;
    public APMPGamesItem sendGames;
    public ArrayList<APMPGoodsItem> sendGoodsList = null;

    public void clear() {
        this.sendGames = null;
        this.sendGoodsList = null;
    }

    public boolean getIsHasGoodsPic() {
        if (this.sendGoodsList != null) {
            int i = 0;
            while (true) {
                if (i >= this.sendGoodsList.size()) {
                    break;
                } else if (TextUtils.isEmpty(this.sendGoodsList.get(i).url)) {
                    this.a = false;
                    break;
                } else {
                    i++;
                }
            }
        } else {
            this.a = false;
        }
        return this.a;
    }

    public boolean getIsHasSend() {
        boolean z;
        boolean isHasSend = this.sendGames != null ? this.sendGames.getIsHasSend() : false;
        if (this.sendGoodsList != null) {
            int i = 0;
            while (true) {
                if (i >= this.sendGoodsList.size()) {
                    break;
                } else if (this.sendGoodsList.get(i).getIsHasSend()) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
        }
        z = false;
        return isHasSend || z;
    }

    public boolean getIsHasSendGoods() {
        return this.sendGoodsList != null && this.sendGoodsList.size() > 0;
    }

    public int getRealSendGamesNum(int i) {
        if (this.sendGames != null) {
            return this.sendGames.getRealSendGamesNum(i);
        }
        return 0;
    }
}
