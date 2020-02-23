package com.google.android.gms.auth.api.signin.internal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.tencent.imsdk.config.ConfigDBHelper;

@KeepName
public class SignInHubActivity extends FragmentActivity {
    /* access modifiers changed from: private */
    public int zzamA;
    /* access modifiers changed from: private */
    public Intent zzamB;
    private zzy zzamw;
    private boolean zzamx = false;
    private SignInConfiguration zzamy;
    private boolean zzamz;

    class zza implements LoaderManager.LoaderCallbacks<Void> {
        private zza() {
        }

        public final Loader<Void> onCreateLoader(int i, Bundle bundle) {
            return new zzb(SignInHubActivity.this, GoogleApiClient.zzpk());
        }

        public final /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
            SignInHubActivity.this.setResult(SignInHubActivity.this.zzamA, SignInHubActivity.this.zzamB);
            SignInHubActivity.this.finish();
        }

        public final void onLoaderReset(Loader<Void> loader) {
        }
    }

    private final void zzU(int i) {
        Status status = new Status(i);
        Intent intent = new Intent();
        intent.putExtra("googleSignInStatus", status);
        setResult(0, intent);
        finish();
    }

    private final void zzmM() {
        getSupportLoaderManager().initLoader(0, (Bundle) null, new zza());
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!this.zzamx) {
            setResult(0);
            switch (i) {
                case 40962:
                    if (intent != null) {
                        SignInAccount signInAccount = (SignInAccount) intent.getParcelableExtra(GoogleSignInApi.EXTRA_SIGN_IN_ACCOUNT);
                        if (signInAccount != null && signInAccount.zzmD() != null) {
                            GoogleSignInAccount zzmD = signInAccount.zzmD();
                            this.zzamw.zza(zzmD, this.zzamy.zzmL());
                            intent.removeExtra(GoogleSignInApi.EXTRA_SIGN_IN_ACCOUNT);
                            intent.putExtra("googleSignInAccount", zzmD);
                            this.zzamz = true;
                            this.zzamA = i2;
                            this.zzamB = intent;
                            zzmM();
                            return;
                        } else if (intent.hasExtra("errorCode")) {
                            zzU(intent.getIntExtra("errorCode", 8));
                            return;
                        }
                    }
                    zzU(8);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzamw = zzy.zzaj(this);
        Intent intent = getIntent();
        if (!"com.google.android.gms.auth.GOOGLE_SIGN_IN".equals(intent.getAction())) {
            String valueOf = String.valueOf(intent.getAction());
            Log.e("AuthSignInClient", valueOf.length() != 0 ? "Unknown action: ".concat(valueOf) : new String("Unknown action: "));
            finish();
        }
        this.zzamy = (SignInConfiguration) intent.getParcelableExtra(ConfigDBHelper.DATABASE_TABLE_NAME);
        if (this.zzamy == null) {
            Log.e("AuthSignInClient", "Activity started with invalid configuration.");
            setResult(0);
            finish();
        } else if (bundle == null) {
            Intent intent2 = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
            intent2.setPackage("com.google.android.gms");
            intent2.putExtra(ConfigDBHelper.DATABASE_TABLE_NAME, this.zzamy);
            try {
                startActivityForResult(intent2, 40962);
            } catch (ActivityNotFoundException e) {
                this.zzamx = true;
                Log.w("AuthSignInClient", "Could not launch sign in Intent. Google Play Service is probably being updated...");
                zzU(17);
            }
        } else {
            this.zzamz = bundle.getBoolean("signingInGoogleApiClients");
            if (this.zzamz) {
                this.zzamA = bundle.getInt("signInResultCode");
                this.zzamB = (Intent) bundle.getParcelable("signInResultData");
                zzmM();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("signingInGoogleApiClients", this.zzamz);
        if (this.zzamz) {
            bundle.putInt("signInResultCode", this.zzamA);
            bundle.putParcelable("signInResultData", this.zzamB);
        }
    }
}
