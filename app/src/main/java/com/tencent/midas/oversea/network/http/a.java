package com.tencent.midas.oversea.network.http;

class a implements Runnable {
    final /* synthetic */ APBaseHttpAns a;

    a(APBaseHttpAns aPBaseHttpAns) {
        this.a = aPBaseHttpAns;
    }

    public void run() {
        this.a.httpClient.requestAgain();
    }
}
