package com.google.android.gms.appinvite;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.api.VKApiConst;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import org.apache.http.HttpHost;

public final class AppInviteInvitation {
    private static final String[] zzajT = {"jpg", ContentType.SUBTYPE_JPEG, ContentType.SUBTYPE_PNG};

    public static final class IntentBuilder {
        public static final int MAX_CALL_TO_ACTION_TEXT_LENGTH = 20;
        public static final int MAX_EMAIL_HTML_CONTENT = 512000;
        public static final int MAX_MESSAGE_LENGTH = 100;
        public static final int MIN_CALL_TO_ACTION_TEXT_LENGTH = 2;
        private final Intent mIntent = new Intent("com.google.android.gms.appinvite.ACTION_APP_INVITE");
        private String zzajU;
        private String zzajV;

        @Retention(RetentionPolicy.SOURCE)
        public @interface PlatformMode {
            public static final int PROJECT_PLATFORM_ANDROID = 2;
            public static final int PROJECT_PLATFORM_IOS = 1;
        }

        public IntentBuilder(@NonNull CharSequence charSequence) {
            zzbo.zzu(charSequence);
            this.mIntent.putExtra("com.google.android.gms.appinvite.TITLE", charSequence);
            this.mIntent.setPackage("com.google.android.gms");
        }

        public final Intent build() {
            if (!TextUtils.isEmpty(this.zzajU)) {
                zzbo.zzh(this.zzajV, "Email html content must be set when email subject is set.");
                zzbo.zzb(this.mIntent.getData() == null, (Object) "Custom image must not be set when email html content is set.");
                zzbo.zzb(TextUtils.isEmpty(this.mIntent.getCharSequenceExtra("com.google.android.gms.appinvite.BUTTON_TEXT")), (Object) "Call to action text must not be set when email html content is set.");
                this.mIntent.putExtra("com.google.android.gms.appinvite.EMAIL_SUBJECT", this.zzajU);
                this.mIntent.putExtra("com.google.android.gms.appinvite.EMAIL_CONTENT", this.zzajV);
            } else if (!TextUtils.isEmpty(this.zzajV)) {
                throw new IllegalArgumentException("Email subject must be set when email html content is set.");
            }
            return this.mIntent;
        }

        public final IntentBuilder setAccount(Account account) {
            if (account == null || !"com.google".equals(account.type)) {
                this.mIntent.removeExtra("com.google.android.gms.appinvite.ACCOUNT_NAME");
            } else {
                this.mIntent.putExtra("com.google.android.gms.appinvite.ACCOUNT_NAME", account);
            }
            return this;
        }

        public final IntentBuilder setAdditionalReferralParameters(Map<String, String> map) {
            if (map != null) {
                this.mIntent.putExtra("com.google.android.gms.appinvite.REFERRAL_PARAMETERS_URI", AppInviteInvitation.zzl(map));
            } else {
                this.mIntent.removeExtra("com.google.android.gms.appinvite.REFERRAL_PARAMETERS_URI");
            }
            return this;
        }

        public final IntentBuilder setAndroidMinimumVersionCode(int i) {
            this.mIntent.putExtra("com.google.android.gms.appinvite.appMinimumVersionCode", i);
            return this;
        }

        public final IntentBuilder setCallToActionText(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() < 2 || charSequence.length() > 20) {
                throw new IllegalArgumentException(String.format("Text must be between %d and %d chars in length.", new Object[]{2, 20}));
            }
            this.mIntent.putExtra("com.google.android.gms.appinvite.BUTTON_TEXT", charSequence);
            return this;
        }

        public final IntentBuilder setCustomImage(Uri uri) {
            boolean z = false;
            zzbo.zzu(uri);
            zzbo.zzb(uri.isAbsolute(), (Object) "Image uri is not an absolute uri. Did you forget to add a scheme to the Uri?");
            String lowerCase = uri.getScheme().toLowerCase();
            boolean z2 = lowerCase.equals("android.resource") || lowerCase.equals("content") || lowerCase.equals(TransferTable.COLUMN_FILE);
            zzbo.zzb(z2 || lowerCase.equals(HttpHost.DEFAULT_SCHEME_NAME) || lowerCase.equals(VKApiConst.HTTPS), (Object) "Image uri must be a content URI with scheme \"android.resource\", \"content\" or \"file\", or a network url with scheme \"http\" or \"https\".");
            if (!z2) {
                String uri2 = uri.toString();
                String substring = uri2.substring(uri2.lastIndexOf(Constants.URL_PATH_DELIMITER) + 1, uri2.length());
                String lowerCase2 = substring == null ? null : substring.lastIndexOf(".") == -1 ? null : substring.substring(substring.lastIndexOf(".") + 1, substring.length()).toLowerCase();
                if (TextUtils.isEmpty(lowerCase2) || AppInviteInvitation.zzbK(lowerCase2)) {
                    z = true;
                }
                zzbo.zzb(z, (Object) String.valueOf(lowerCase2).concat(" images are not supported. Only jpg, jpeg, or png images are supported."));
            }
            this.mIntent.setData(uri.buildUpon().scheme(lowerCase).build());
            if (z2) {
                this.mIntent.addFlags(1);
            }
            return this;
        }

        public final IntentBuilder setDeepLink(Uri uri) {
            if (uri != null) {
                this.mIntent.putExtra("com.google.android.gms.appinvite.DEEP_LINK_URL", uri);
            } else {
                this.mIntent.removeExtra("com.google.android.gms.appinvite.DEEP_LINK_URL");
            }
            return this;
        }

        public final IntentBuilder setEmailHtmlContent(String str) {
            if (str == null || str.getBytes().length <= 512000) {
                this.zzajV = str;
                return this;
            }
            throw new IllegalArgumentException(String.format("Email html content must be %d bytes or less.", new Object[]{Integer.valueOf(MAX_EMAIL_HTML_CONTENT)}));
        }

        public final IntentBuilder setEmailSubject(String str) {
            this.zzajU = str;
            return this;
        }

        public final IntentBuilder setGoogleAnalyticsTrackingId(String str) {
            this.mIntent.putExtra("com.google.android.gms.appinvite.GOOGLE_ANALYTICS_TRACKING_ID", str);
            return this;
        }

        public final IntentBuilder setMessage(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() <= 100) {
                this.mIntent.putExtra("com.google.android.gms.appinvite.MESSAGE", charSequence);
                return this;
            }
            throw new IllegalArgumentException(String.format("Message must be %d chars or less.", new Object[]{100}));
        }

        public final IntentBuilder setOtherPlatformsTargetApplication(int i, String str) throws IllegalArgumentException {
            switch (i) {
                case 1:
                    this.mIntent.putExtra("com.google.android.gms.appinvite.iosTargetApplication", str);
                    break;
                case 2:
                    this.mIntent.putExtra("com.google.android.gms.appinvite.androidTargetApplication", str);
                    break;
                default:
                    throw new IllegalArgumentException("targetPlatform must be either PROJECT_PLATFORM_IOS or PROJECT_PLATFORM_ANDROID.");
            }
            return this;
        }
    }

    private AppInviteInvitation() {
    }

    public static String[] getInvitationIds(int i, @NonNull Intent intent) {
        if (i == -1) {
            return intent.getStringArrayExtra("com.google.android.gms.appinvite.RESULT_INVITATION_IDS");
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static boolean zzbK(String str) {
        for (String equals : zzajT) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static Bundle zzl(Map<String, String> map) {
        Bundle bundle = new Bundle();
        for (String next : map.keySet()) {
            bundle.putString(next, map.get(next));
        }
        return bundle;
    }
}
