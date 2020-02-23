package com.tencent.midas.oversea.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APActivityResult;
import com.tencent.midas.oversea.comm.APBaseActivity;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.channel.ChannelGoods;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class APMallActivity extends APBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    /* access modifiers changed from: private */
    public ListView a;
    /* access modifiers changed from: private */
    public ArrayList<ChannelGoods> b;

    private ArrayList<ChannelGoods> a() {
        if (APPayMananger.singleton().getCurPayHub() == null) {
            APLog.e("APMallActivity", "CurPayHub is null");
            return null;
        }
        ArrayList<ChannelGoods> goodsList = APPayMananger.singleton().getCurPayHub().getGoodsList();
        if (goodsList == null || goodsList.size() <= 0) {
            return null;
        }
        return goodsList;
    }

    private ArrayList<String> a(ArrayList<ChannelGoods> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            Iterator<ChannelGoods> it = arrayList.iterator();
            while (it.hasNext()) {
                String str = it.next().channel.id;
                if (!TextUtils.isEmpty(str)) {
                    arrayList2.add(str);
                }
            }
            if (arrayList2.size() > 0) {
                return arrayList2;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        Fragment newInstance;
        boolean z = true;
        if (this.b != null && !TextUtils.isEmpty(str)) {
            if (str.equals("mol_pin")) {
                newInstance = APRegionMolpinFragment.newInstance(str, APPayMananger.singleton().getCurBaseRequest().country, APPayMananger.singleton().getCurBaseRequest().currency_type);
            } else if (str.equals("fortumo")) {
                newInstance = APRegionFortumoFragment.newInstance(str, APPayMananger.singleton().getCurBaseRequest().country, APPayMananger.singleton().getCurBaseRequest().currency_type);
            } else {
                if (this.b.size() != 1) {
                    z = false;
                }
                newInstance = APRegionGridContentFragment.newInstance(str, z);
            }
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(APCommMethod.getId(this, "unipay_id_mainContent"), newInstance);
            beginTransaction.commit();
        }
    }

    private void b() {
        findViewById(APCommMethod.getId(this, "unipay_id_close")).setOnClickListener(this);
        findViewById(APCommMethod.getId(this, "unipay_id_back")).setOnClickListener(this);
        a(this.b.get(0).channel.id);
        c();
    }

    private void c() {
        this.a = (ListView) APFindViewUtils.findViewById((Activity) this, "unipay_id_channelList");
        ArrayList<String> a2 = a(this.b);
        if (a2 != null && a2.size() == 1) {
            this.a.setVisibility(8);
        }
        if (a2 != null) {
            APChannelsListAdapter aPChannelsListAdapter = new APChannelsListAdapter(this, a2);
            this.a.setAdapter(aPChannelsListAdapter);
            this.a.setOnItemClickListener(new c(this, aPChannelsListAdapter));
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Log.d("", "onActivityResult(" + i + "," + i2 + "," + intent);
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        obtainMessage.obj = new APActivityResult(i, i2, intent);
        obtainMessage.what = 57;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }

    public void onBackPressed() {
        finish();
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_MAIN_BACK, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, (String) null);
        APPayMananger.singleton().getCurHandler().sendEmptyMessage(23);
    }

    public void onClick(View view) {
        if (view.getId() == APCommMethod.getId(this, "unipay_id_close")) {
            finish();
            APPayMananger.singleton().getCurHandler().sendEmptyMessage(23);
            APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_MAIN_BACK, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, (String) null);
        } else if (view.getId() == APCommMethod.getId(this, "unipay_id_back")) {
            finish();
            APPayMananger.singleton().getCurHandler().sendEmptyMessage(23);
            APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_MAIN_BACK, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, (String) null);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(APCommMethod.getLayoutId(this, "unipay_abroad_qudao"));
        if (APPayMananger.singleton().getCurHandler() == null) {
            finish();
            return;
        }
        this.b = a();
        if (this.b == null || this.b.size() == 0) {
            APLog.e("APMallActivity", "goods is null");
            finish();
            Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
            obtainMessage.obj = "goods is empty,no channel support";
            obtainMessage.what = 4;
            APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
            return;
        }
        b();
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_MAIN_SHOW, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, (String) null);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }
}
