package com.garena.pay.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.Result;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.ImageLoader;
import com.beetalk.sdk.helper.LocaleHelper;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.msdk.R;
import com.garena.pay.android.exception.ErrorException;
import com.garena.pay.android.helper.QueryString;
import com.garena.pay.android.helper.Utils;
import com.garena.pay.android.view.GGBasePopupView;
import com.garena.pay.android.view.WebDialog;
import com.garena.pay.android.view.WebViewJSInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;

public class GarenaWebPayRequestHandler extends GGPayRequestHandler {
    private transient WebDialog dialog;
    /* access modifiers changed from: private */
    public transient GGBasePopupView popupView;
    /* access modifiers changed from: private */
    public int requestCode = 0;

    protected GarenaWebPayRequestHandler(GGPayClient client) {
        super(client);
    }

    public boolean startPay(GGPayRequest request) {
        BBLogger.d("start payment %s", request.getRequestId().toString());
        this.requestCode = request.getRequestCode().intValue();
        WebDialog.OnCompleteListener listener = new WebDialog.OnCompleteListener() {
            public void onComplete(Bundle values, ErrorException error) {
                BBLogger.d("web pay complete", new Object[0]);
                GarenaWebPayRequestHandler.this.onWebDialogComplete(values, error);
            }
        };
        String url = buildPaymentChannelUrl(SDKConstants.getRootPayAPIUrl(), request);
        if (this.dialog == null) {
            this.dialog = createWebDialog(url, 16973840);
        }
        this.dialog.setOnCompleteListener(listener);
        this.dialog.show();
        return this.dialog.isShowing();
    }

    /* access modifiers changed from: protected */
    public WebDialog createWebDialog(String url, int theme) {
        WebDialog webDialog = new WebDialog((Context) this.client.getActivity(), url, theme);
        this.client.setWebBridge(new WebViewJSInterface(webDialog.getWebView(), this.client.getWebCmdHandler()));
        return webDialog;
    }

    private String buildPaymentChannelUrl(String rootApi, GGPayRequest request) {
        HashMap<String, String> map = new HashMap<>();
        map.put("app_id", request.getClientPaymentRequest().getAppId());
        map.put("platform", request.getClientPaymentRequest().getPlatform().toString());
        map.put("open_id", request.getClientPaymentRequest().getBuyerId());
        map.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(2));
        map.put("app_server_id", request.getClientPaymentRequest().getAppServerId().toString());
        map.put("app_role_id", request.getClientPaymentRequest().getRoleId().toString());
        if (request.getChosenDenomination() != null) {
            if (request.isUseDefaultItemList()) {
                map.put(SDKConstants.WEB_PAY.EXTRA_AMOUNT, request.getChosenDenomination().getAppPoints().toString());
            } else {
                map.put(FirebaseAnalytics.Param.ITEM_ID, request.getChosenDenomination().getItemId());
            }
        }
        map.put("access_token", request.getClientPaymentRequest().getToken());
        map.put("channel", getChannelId());
        if (request.getClientPaymentRequest().getLocale() != null) {
            map.put("locale", LocaleHelper.getLocaleStringForServer(request.getClientPaymentRequest().getLocale()));
        }
        if (request.getClientPaymentRequest().getRebateId().longValue() > 0) {
            map.put(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID, String.valueOf(request.getClientPaymentRequest().getRebateId()));
        }
        QueryString queryString = new QueryString(map);
        BBLogger.d("Request Params Data %s", map);
        return rootApi + "?" + queryString.toString();
    }

    /* access modifiers changed from: protected */
    public void onWebDialogComplete(Bundle values, ErrorException error) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        if (values != null) {
            View view = this.client.getActivity().getLayoutInflater().inflate(R.layout.msdk_confirmation_popup, new LinearLayout(this.client.getActivity()));
            this.popupView = new GGBasePopupView(view, false, false);
            view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    GarenaWebPayRequestHandler.this.popupView.dismiss();
                    GGBasePopupView unused = GarenaWebPayRequestHandler.this.popupView = null;
                }
            });
            final Intent data = new Intent();
            int result = 0;
            if (values.containsKey("error")) {
                String errMsg = values.getString("error");
                int errCode = values.getInt(SDKConstants.WEB_PAY.EXTRA_ERROR_CODE);
                data.putExtra("error", errMsg);
                data.putExtra(SDKConstants.WEB_PAY.EXTRA_ERROR_CODE, errCode);
                if (errCode == GGErrorCode.PAYMENT_USER_CANCELLED.getCode().intValue()) {
                    this.client.onActivityResult(this.requestCode, 0, data);
                    return;
                }
                toggleErrorPanelVisibility(view, 4);
                ((TextView) view.findViewById(R.id.error_text)).setText(this.client.getActivity().getResources().getString(R.string.payment_result_err, new Object[]{errMsg}));
                this.popupView.setListener(new GGBasePopupView.DismissListener() {
                    public void onDismissed() {
                        GarenaWebPayRequestHandler.this.client.onActivityResult(GarenaWebPayRequestHandler.this.requestCode, 0, data);
                    }
                });
            } else {
                toggleErrorPanelVisibility(view, 0);
                ((TextView) view.findViewById(R.id.txt_app_point_amount)).setText(this.client.getActivity().getResources().getString(R.string.payment_result_amount, new Object[]{values.getString(SDKConstants.WEB_PAY.EXTRA_AMOUNT)}));
                ImageLoader.load(values.getString(SDKConstants.WEB_PAY.EXTRA_ICON)).into((ImageView) view.findViewById(R.id.icon_app_point_amount));
                data.putExtra("item_name", values.getString("item_name"));
                data.putExtra(SDKConstants.WEB_PAY.EXTRA_AMOUNT, values.getString(SDKConstants.WEB_PAY.EXTRA_AMOUNT));
                data.putExtra(SDKConstants.WEB_PAY.EXTRA_ICON, values.getString(SDKConstants.WEB_PAY.EXTRA_ICON));
                data.putExtra(SDKConstants.WEB_PAY.EXTRA_TXN_ID, values.getString(SDKConstants.WEB_PAY.EXTRA_TXN_ID));
                data.putExtra(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID, values.getString(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID));
                data.putExtra(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS, values.getString(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS));
                result = -1;
                this.popupView.setListener(new GGBasePopupView.DismissListener() {
                    public void onDismissed() {
                        GarenaWebPayRequestHandler.this.client.onActivityResult(GarenaWebPayRequestHandler.this.requestCode, -1, data);
                    }
                });
            }
            try {
                this.popupView.showAtCenter(this.client.getActivity().findViewById(R.id.main_layout));
            } catch (Exception e) {
                BBLogger.e(e);
                this.client.onActivityResult(this.requestCode, result, data);
            }
        }
    }

    private void toggleErrorPanelVisibility(View view, int visibility) {
        if (visibility == 4) {
            view.findViewById(R.id.success_panel).setVisibility(4);
            view.findViewById(R.id.error_panel).setVisibility(0);
            return;
        }
        view.findViewById(R.id.success_panel).setVisibility(0);
        view.findViewById(R.id.error_panel).setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public boolean onActivityResult(int requestCode2, int resultCode, Intent data, GGPayRequest pendingRequest) {
        if (resultCode == 0) {
            if (data.getExtras().containsKey("error")) {
                this.client.onHandlerResult(Result.createErrorResult(pendingRequest, GGErrorCode.getErrorFromCode(data.getIntExtra(SDKConstants.WEB_PAY.EXTRA_ERROR_CODE, GGErrorCode.UNKNOWN_ERROR.getCode().intValue())), data.getStringExtra("error")));
                return true;
            }
            this.client.onHandlerResult(Result.createErrorResult(pendingRequest, GGErrorCode.UNKNOWN_ERROR, GGErrorCode.UNKNOWN_ERROR.getStringValue()));
            return true;
        } else if (resultCode != -1) {
            return true;
        } else {
            this.client.onHandlerResult(Result.createSuccessResult(pendingRequest, Utils.convertBundleToMap(data.getExtras())));
            return true;
        }
    }

    public void onDestroy() {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }
}
