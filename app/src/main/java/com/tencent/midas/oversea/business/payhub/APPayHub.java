package com.tencent.midas.oversea.business.payhub;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.api.request.APMidasMonthRequest;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.channel.APCountryInfo;
import com.tencent.midas.oversea.data.channel.ChannelGoods;
import com.tencent.midas.oversea.data.channel.ChannelItem;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class APPayHub {
    public static final String CHANNEL = "channel";
    public static final int DEFAULTPRODUCTIDINDEX = 16777215;
    public static final String PRODUCTIDLIST = "productid_list";
    private static final String a = APPayHub.class.getSimpleName();
    private static long e = 0;
    public static HashMap<String, a> mChannel = new HashMap<>();
    public static HashMap<String, a> mRetoreChannel = new HashMap<>();
    private APBasePayChannel b;
    private ArrayList<ChannelGoods> c = new ArrayList<>();
    public String count;
    public String countryCode;
    private IAPHttpAnsObserver d = new j(this);
    public String offer_name;
    public String rate;
    public String unit;

    private static class a {
        public String a;
        public String b;
        public int c;

        public a(String str, String str2, int i) {
            this.a = str;
            this.b = str2;
            this.c = i;
        }
    }

    static {
        mChannel.put(UnityPayHelper.GWALLET, new a(UnityPayHelper.GWALLET, "com.tencent.midas.oversea.business.payhub.gw.APGWPay", 100));
        mChannel.put("boku", new a("boku", "com.tencent.midas.oversea.business.payhub.boku.APBokuPay", 101));
        mChannel.put("mol_pin", new a("mol_pin", "com.tencent.midas.oversea.business.payhub.mol.APMolPinPay", 102));
        mChannel.put("mol_hf", new a("mol_hf", "com.tencent.midas.oversea.business.payhub.mol.APMolEasyPay", 103));
        mChannel.put("mol_acct", new a("mol_acct", "com.tencent.midas.oversea.business.payhub.mol.APMolWalletPay", 104));
        mChannel.put("paymentwall", new a("paymentwall", "com.tencent.midas.oversea.business.payhub.paymentwall.PWPay", 105));
        mChannel.put("twmycard", new a("twmycard", "com.tencent.midas.oversea.business.payhub.mycard.APMyCard", 106));
        mChannel.put("zalo", new a("zalo", "com.tencent.midas.oversea.business.payhub.zalo.APZaloPay", 107));
        mChannel.put("fortumo", new a("fortumo", "com.tencent.midas.oversea.business.payhub.fortumo.APFortumoPay", 108));
        mChannel.put("os_link", new a("os_link", "com.tencent.midas.oversea.business.payhub.link.APLinkPay", 109));
        mChannel.put("os_37wan", new a("os_37wan", "com.tencent.midas.oversea.business.payhub.wan.APUjoyPay", 110));
        mChannel.put("os_xsolla", new a("os_xsolla", "com.tencent.midas.oversea.business.payhub.xsolla.APXsollaPay", 111));
        mChannel.put("os_doku", new a("os_doku", "com.tencent.midas.oversea.business.payhub.doku.APDokuPay", 112));
        mChannel.put("os_feiliu_google", new a("os_feiliu_google", "com.tencent.midas.oversea.business.payhub.fl.APChannelFLGWPay", 113));
        mChannel.put("os_feiliu_tstore", new a("os_feiliu_tstore", "com.tencent.midas.oversea.business.payhub.fl.APChannelFLTStorePay", 114));
        mChannel.put("testpay", new a("testpay", "com.tencent.midas.oversea.business.payhub.testpay.APChannelTestPay", 115));
        mChannel.put("os_feiliu_naver", new a("os_feiliu_naver", "com.tencent.midas.oversea.business.payhub.fl.APChannelFLNaverPay", 116));
        mRetoreChannel.put(UnityPayHelper.GWALLET, new a(UnityPayHelper.GWALLET, "com.tencent.midas.oversea.business.payhub.gw.APGWRestore", 100));
        mRetoreChannel.put("os_naver", new a("os_naver", "com.tencent.midas.oversea.business.payhub.naver.APRestore", 100));
        mRetoreChannel.put("os_tstore", new a("os_tstore", "com.tencent.midas.oversea.business.payhub.tstore.APRestore", 100));
        mRetoreChannel.put("os_link", new a("os_link", "com.tencent.midas.oversea.business.payhub.link.APRestore", 100));
    }

    private APPayHub() {
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return str.replace('_', '.').replace("os.", "");
        } catch (Exception e2) {
            return "";
        }
    }

    /* access modifiers changed from: private */
    public void a(ArrayList<ChannelGoods> arrayList) {
        APBasePayChannel b2;
        this.c.clear();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < arrayList.size()) {
                ChannelItem channelItem = arrayList.get(i2).channel;
                String str = channelItem.id;
                boolean z = channelItem.isHasProduct;
                if (!TextUtils.isEmpty(str)) {
                    String payChannel = APPayMananger.singleton().getCurBaseRequest().getPayChannel();
                    if ((TextUtils.isEmpty(payChannel) || payChannel.equals(str)) && (b2 = b(str)) != null && (!b2.hasGoodsList() || z)) {
                        channelItem.mAPBasePayChannel = b2;
                        this.c.add(arrayList.get(i2));
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static boolean a() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - e < 1000) {
            return true;
        }
        e = currentTimeMillis;
        return false;
    }

    private APBasePayChannel b(String str) {
        int i = 0;
        boolean z = false;
        while (i < mChannel.size()) {
            if (mChannel.containsKey(str)) {
                try {
                    z = true;
                    return (APBasePayChannel) Class.forName(mChannel.get(str).b).newInstance();
                } catch (InstantiationException e2) {
                    APLog.i("APPayHub", mChannel.get(str).b + " InstantiationException");
                } catch (IllegalAccessException e3) {
                    APLog.i("APPayHub", mChannel.get(str).b + " IllegalAccessException");
                } catch (ClassNotFoundException e4) {
                    APLog.i("APPayHub", mChannel.get(str).b + " IllegalAccessException");
                }
            } else {
                i++;
            }
        }
        if (!z) {
            try {
                String str2 = "com.tencent.midas.oversea.business.payhub." + a(str) + ".APPay";
                APLog.i("APPayHub", "channelName:" + str2);
                return (APBasePayChannel) Class.forName(str2).newInstance();
            } catch (InstantiationException e5) {
                APLog.i("APPayHub", "InstantiationException Exception");
            } catch (IllegalAccessException e6) {
                APLog.i("APPayHub", " IllegalAccessException Exception");
            } catch (ClassNotFoundException e7) {
                APLog.i("APPayHub", " IllegalAccessException Exception");
            }
        }
        return null;
        APLog.e(a, "local jars not support this channel,please check the  " + str + "  jar file");
        return null;
    }

    public static APPayHub createInstance(APOrder aPOrder) {
        return new APPayHub();
    }

    public static APBaseRestore createRestoreChannel(String str) {
        int i = 0;
        boolean z = false;
        while (i < mRetoreChannel.size()) {
            if (mRetoreChannel.containsKey(str)) {
                try {
                    z = true;
                    return (APBaseRestore) Class.forName(mRetoreChannel.get(str).b).newInstance();
                } catch (InstantiationException e2) {
                    APLog.i("APPayHub", mRetoreChannel.get(str).b + " InstantiationException");
                } catch (IllegalAccessException e3) {
                    APLog.i("APPayHub", mRetoreChannel.get(str).b + " IllegalAccessException");
                } catch (ClassNotFoundException e4) {
                    APLog.i("APPayHub", mRetoreChannel.get(str).b + " IllegalAccessException");
                }
            } else {
                i++;
            }
        }
        if (!z) {
            try {
                String str2 = "com.tencent.midas.oversea.business.payhub." + a(str) + ".APRestore";
                APLog.i("APPayHub", "channelName:" + str2);
                return (APBaseRestore) Class.forName(str2).newInstance();
            } catch (InstantiationException e5) {
                APLog.i("APPayHub", "InstantiationException Exception");
            } catch (IllegalAccessException e6) {
                APLog.i("APPayHub", " IllegalAccessException Exception");
            } catch (ClassNotFoundException e7) {
                APLog.i("APPayHub", " IllegalAccessException Exception");
            }
        }
        return null;
        APLog.e(a, "local jars not support this channel,please check the  " + str + "  jar file");
        return null;
    }

    public static int getPayChannelIndex(String str) {
        if (mChannel.get(str) != null) {
            return mChannel.get(str).c;
        }
        return -1;
    }

    public ChannelItem getChannelItem(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.c.size()) {
                return null;
            }
            if (str.equals(this.c.get(i2).channel.id)) {
                return this.c.get(i2).channel;
            }
            i = i2 + 1;
        }
    }

    public void getChannelList() {
        String str = APPayMananger.singleton().getCurBaseRequest().mType;
        String str2 = APPayMananger.singleton().getCurBaseRequest().saveValue;
        String str3 = APPayMananger.singleton().getCurBaseRequest().currency_type;
        String str4 = APPayMananger.singleton().getCurBaseRequest().country;
        String str5 = "";
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_UNIONMONTH)) {
            str5 = ((APMidasMonthRequest) APPayMananger.singleton().getCurBaseRequest()).serviceCode;
        }
        APNetworkManager.getInstance().overSeanInfo(str, str2, str3, str4, str5, "", this.d);
    }

    public void getChannelListNew() {
        String str = APPayMananger.singleton().getCurBaseRequest().mType;
        String str2 = APPayMananger.singleton().getCurBaseRequest().saveValue;
        String str3 = APPayMananger.singleton().getCurBaseRequest().currency_type;
        String str4 = APPayMananger.singleton().getCurBaseRequest().country;
        String str5 = "";
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_UNIONMONTH)) {
            str5 = ((APMidasMonthRequest) APPayMananger.singleton().getCurBaseRequest()).serviceCode;
        }
        APNetworkManager2.getInstance().getMall(str3, str4, str5, "", this.d);
    }

    public ArrayList<GoodsItem> getCountryGoodsList(String str, String str2) {
        ArrayList<GoodsItem> arrayList = new ArrayList<>();
        Iterator<ChannelGoods> it = this.c.iterator();
        while (it.hasNext()) {
            ChannelGoods next = it.next();
            if (str.equalsIgnoreCase(next.channel.id)) {
                Iterator<GoodsItem> it2 = next.items.iterator();
                while (it2.hasNext()) {
                    GoodsItem next2 = it2.next();
                    if (str2.equalsIgnoreCase(next2.country)) {
                        arrayList.add(next2);
                    }
                }
            }
        }
        return arrayList;
    }

    public ArrayList<APCountryInfo> getCountryList(String str) {
        ArrayList<APCountryInfo> arrayList = new ArrayList<>();
        HashSet hashSet = new HashSet();
        Iterator<ChannelGoods> it = this.c.iterator();
        while (it.hasNext()) {
            ChannelGoods next = it.next();
            if (next.channel.id.equalsIgnoreCase(str)) {
                Iterator<GoodsItem> it2 = next.items.iterator();
                while (it2.hasNext()) {
                    GoodsItem next2 = it2.next();
                    if (!hashSet.contains(next2.country)) {
                        APCountryInfo aPCountryInfo = new APCountryInfo();
                        aPCountryInfo.countryname = next2.country;
                        aPCountryInfo.currency = next2.currency_type;
                        if (TextUtils.isEmpty(aPCountryInfo.countryname)) {
                            aPCountryInfo.countryImage = "";
                        } else {
                            aPCountryInfo.countryImage = APGlobalInfo.COUNTRY_IMG_URL_PREFIX + next2.country.toLowerCase() + ".png";
                        }
                        arrayList.add(aPCountryInfo);
                        hashSet.add(next2.country);
                    }
                }
            }
        }
        return arrayList;
    }

    public APBasePayChannel getCurChannel() {
        return this.b;
    }

    public GoodsItem getGoods(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            return null;
        }
        ArrayList<GoodsItem> countryGoodsList = getCountryGoodsList(str, str2);
        if (countryGoodsList.size() == 0) {
            return null;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= countryGoodsList.size()) {
                return null;
            }
            if (str3.equals(countryGoodsList.get(i2).productid)) {
                return countryGoodsList.get(i2);
            }
            i = i2 + 1;
        }
    }

    public ArrayList<ChannelGoods> getGoodsList() {
        return this.c;
    }

    public ChannelItem getSimpleChannelItem(String str) {
        APBasePayChannel b2;
        if (TextUtils.isEmpty(str) || (b2 = b(str)) == null) {
            return null;
        }
        ChannelItem channelItem = new ChannelItem();
        channelItem.mAPBasePayChannel = b2;
        channelItem.id = str;
        return channelItem;
    }

    public boolean gotoPay(Activity activity, ChannelItem channelItem, GoodsItem goodsItem, String str, String str2, boolean z) {
        if (channelItem == null || channelItem.mAPBasePayChannel == null || a()) {
            return false;
        }
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAY, -1, (String) null, (String) null, channelItem.id);
        APLog.i(a, "cur channel:" + channelItem.toString());
        this.b = channelItem.mAPBasePayChannel;
        this.b.gotoPay(activity, APPayMananger.singleton().getCurHandler(), channelItem, goodsItem, str, str2, z);
        return true;
    }

    public boolean handleActivityResult(int i, int i2, Intent intent) {
        return this.b.handleActivityResult(i, i2, intent);
    }

    public boolean isChannelSupport(String str) {
        boolean containsKey = mChannel.containsKey(str);
        if (containsKey) {
            return true;
        }
        APLog.e("APPayHub", str + " jar file error,please help to check it");
        try {
            String str2 = "com.tencent.midas.oversea.business.payhub." + a(str) + ".APPay";
            APLog.i("APPayHub", "channelName:" + str2);
            Class.forName(str2);
            return true;
        } catch (ClassNotFoundException e2) {
            APLog.e("APPayHub", str + "jar file error,please help to check it");
            return containsKey;
        }
    }

    public void release() {
        this.b = null;
        this.c.clear();
    }

    public void showPayHub() {
    }
}
