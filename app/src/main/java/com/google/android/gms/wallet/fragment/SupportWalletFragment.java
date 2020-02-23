package com.google.android.gms.wallet.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.dynamic.zzr;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.ge;
import com.google.android.gms.internal.gz;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class SupportWalletFragment extends Fragment {
    /* access modifiers changed from: private */
    public boolean mCreated = false;
    /* access modifiers changed from: private */
    public final Fragment zzaSE = this;
    /* access modifiers changed from: private */
    public zzb zzbPZ;
    /* access modifiers changed from: private */
    public final zzr zzbQa = zzr.zza(this);
    private final zzc zzbQb = new zzc();
    /* access modifiers changed from: private */
    public zza zzbQc = new zza(this);
    /* access modifiers changed from: private */
    public WalletFragmentOptions zzbQd;
    /* access modifiers changed from: private */
    public WalletFragmentInitParams zzbQe;
    /* access modifiers changed from: private */
    public MaskedWalletRequest zzbQf;
    /* access modifiers changed from: private */
    public MaskedWallet zzbQg;
    /* access modifiers changed from: private */
    public Boolean zzbQh;

    public interface OnStateChangedListener {
        void onStateChanged(SupportWalletFragment supportWalletFragment, int i, int i2, Bundle bundle);
    }

    static class zza extends ge {
        private OnStateChangedListener zzbQi;
        private final SupportWalletFragment zzbQj;

        zza(SupportWalletFragment supportWalletFragment) {
            this.zzbQj = supportWalletFragment;
        }

        public final void zza(int i, int i2, Bundle bundle) {
            if (this.zzbQi != null) {
                this.zzbQi.onStateChanged(this.zzbQj, i, i2, bundle);
            }
        }

        public final void zza(OnStateChangedListener onStateChangedListener) {
            this.zzbQi = onStateChangedListener;
        }
    }

    static class zzb implements LifecycleDelegate {
        private final ga zzbQk;

        private zzb(ga gaVar) {
            this.zzbQk = gaVar;
        }

        /* access modifiers changed from: private */
        public final int getState() {
            try {
                return this.zzbQk.getState();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* access modifiers changed from: private */
        public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
            try {
                this.zzbQk.initialize(walletFragmentInitParams);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* access modifiers changed from: private */
        public final void onActivityResult(int i, int i2, Intent intent) {
            try {
                this.zzbQk.onActivityResult(i, i2, intent);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* access modifiers changed from: private */
        public final void setEnabled(boolean z) {
            try {
                this.zzbQk.setEnabled(z);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* access modifiers changed from: private */
        public final void updateMaskedWallet(MaskedWallet maskedWallet) {
            try {
                this.zzbQk.updateMaskedWallet(maskedWallet);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        /* access modifiers changed from: private */
        public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            try {
                this.zzbQk.updateMaskedWalletRequest(maskedWalletRequest);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onCreate(Bundle bundle) {
            try {
                this.zzbQk.onCreate(bundle);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) zzn.zzE(this.zzbQk.onCreateView(zzn.zzw(layoutInflater), zzn.zzw(viewGroup), bundle));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onDestroy() {
        }

        public final void onDestroyView() {
        }

        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.zzbQk.zza(zzn.zzw(activity), (WalletFragmentOptions) bundle.getParcelable("extraWalletFragmentOptions"), bundle2);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onLowMemory() {
        }

        public final void onPause() {
            try {
                this.zzbQk.onPause();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onResume() {
            try {
                this.zzbQk.onResume();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onSaveInstanceState(Bundle bundle) {
            try {
                this.zzbQk.onSaveInstanceState(bundle);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onStart() {
            try {
                this.zzbQk.onStart();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public final void onStop() {
            try {
                this.zzbQk.onStop();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class zzc extends com.google.android.gms.dynamic.zza<zzb> implements View.OnClickListener {
        private zzc() {
        }

        public final void onClick(View view) {
            FragmentActivity activity = SupportWalletFragment.this.zzaSE.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity), activity, -1);
        }

        /* access modifiers changed from: protected */
        public final void zza(FrameLayout frameLayout) {
            WalletFragmentStyle fragmentStyle;
            int i = -1;
            int i2 = -2;
            Button button = new Button(SupportWalletFragment.this.zzaSE.getActivity());
            button.setText(R.string.wallet_buy_button_place_holder);
            if (!(SupportWalletFragment.this.zzbQd == null || (fragmentStyle = SupportWalletFragment.this.zzbQd.getFragmentStyle()) == null)) {
                DisplayMetrics displayMetrics = SupportWalletFragment.this.zzaSE.getResources().getDisplayMetrics();
                i = fragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                i2 = fragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
            }
            button.setLayoutParams(new ViewGroup.LayoutParams(i, i2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        /* access modifiers changed from: protected */
        public final void zza(zzo<zzb> zzo) {
            FragmentActivity activity = SupportWalletFragment.this.zzaSE.getActivity();
            if (SupportWalletFragment.this.zzbPZ == null && SupportWalletFragment.this.mCreated && activity != null) {
                try {
                    zzb unused = SupportWalletFragment.this.zzbPZ = new zzb(gz.zza(activity, SupportWalletFragment.this.zzbQa, SupportWalletFragment.this.zzbQd, SupportWalletFragment.this.zzbQc));
                    WalletFragmentOptions unused2 = SupportWalletFragment.this.zzbQd = null;
                    zzo.zza(SupportWalletFragment.this.zzbPZ);
                    if (SupportWalletFragment.this.zzbQe != null) {
                        SupportWalletFragment.this.zzbPZ.initialize(SupportWalletFragment.this.zzbQe);
                        WalletFragmentInitParams unused3 = SupportWalletFragment.this.zzbQe = null;
                    }
                    if (SupportWalletFragment.this.zzbQf != null) {
                        SupportWalletFragment.this.zzbPZ.updateMaskedWalletRequest(SupportWalletFragment.this.zzbQf);
                        MaskedWalletRequest unused4 = SupportWalletFragment.this.zzbQf = null;
                    }
                    if (SupportWalletFragment.this.zzbQg != null) {
                        SupportWalletFragment.this.zzbPZ.updateMaskedWallet(SupportWalletFragment.this.zzbQg);
                        MaskedWallet unused5 = SupportWalletFragment.this.zzbQg = null;
                    }
                    if (SupportWalletFragment.this.zzbQh != null) {
                        SupportWalletFragment.this.zzbPZ.setEnabled(SupportWalletFragment.this.zzbQh.booleanValue());
                        Boolean unused6 = SupportWalletFragment.this.zzbQh = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }
    }

    public static SupportWalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        SupportWalletFragment supportWalletFragment = new SupportWalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", walletFragmentOptions);
        supportWalletFragment.zzaSE.setArguments(bundle);
        return supportWalletFragment;
    }

    public final int getState() {
        if (this.zzbPZ != null) {
            return this.zzbPZ.getState();
        }
        return 0;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzbPZ != null) {
            this.zzbPZ.initialize(walletFragmentInitParams);
            this.zzbQe = null;
        } else if (this.zzbQe == null) {
            this.zzbQe = walletFragmentInitParams;
            if (this.zzbQf != null) {
                Log.w("SupportWalletFragment", "updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzbQg != null) {
                Log.w("SupportWalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        } else {
            Log.w("SupportWalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.zzbPZ != null) {
            this.zzbPZ.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        WalletFragmentOptions walletFragmentOptions;
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzbQe != null) {
                    Log.w("SupportWalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.zzbQe = walletFragmentInitParams;
            }
            if (this.zzbQf == null) {
                this.zzbQf = (MaskedWalletRequest) bundle.getParcelable("maskedWalletRequest");
            }
            if (this.zzbQg == null) {
                this.zzbQg = (MaskedWallet) bundle.getParcelable("maskedWallet");
            }
            if (bundle.containsKey("walletFragmentOptions")) {
                this.zzbQd = (WalletFragmentOptions) bundle.getParcelable("walletFragmentOptions");
            }
            if (bundle.containsKey("enabled")) {
                this.zzbQh = Boolean.valueOf(bundle.getBoolean("enabled"));
            }
        } else if (!(this.zzaSE.getArguments() == null || (walletFragmentOptions = (WalletFragmentOptions) this.zzaSE.getArguments().getParcelable("extraWalletFragmentOptions")) == null)) {
            walletFragmentOptions.zzby(this.zzaSE.getActivity());
            this.zzbQd = walletFragmentOptions;
        }
        this.mCreated = true;
        this.zzbQb.onCreate(bundle);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzbQb.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    public final void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.zzbQd == null) {
            this.zzbQd = WalletFragmentOptions.zza((Context) activity, attributeSet);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("attrKeyWalletFragmentOptions", this.zzbQd);
        this.zzbQb.onInflate(activity, bundle2, bundle);
    }

    public final void onPause() {
        super.onPause();
        this.zzbQb.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.zzbQb.onResume();
        FragmentManager supportFragmentManager = this.zzaSE.getActivity().getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (findFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(findFragmentByTag).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzaSE.getActivity()), this.zzaSE.getActivity(), -1);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzbQb.onSaveInstanceState(bundle);
        if (this.zzbQe != null) {
            bundle.putParcelable("walletFragmentInitParams", this.zzbQe);
            this.zzbQe = null;
        }
        if (this.zzbQf != null) {
            bundle.putParcelable("maskedWalletRequest", this.zzbQf);
            this.zzbQf = null;
        }
        if (this.zzbQg != null) {
            bundle.putParcelable("maskedWallet", this.zzbQg);
            this.zzbQg = null;
        }
        if (this.zzbQd != null) {
            bundle.putParcelable("walletFragmentOptions", this.zzbQd);
            this.zzbQd = null;
        }
        if (this.zzbQh != null) {
            bundle.putBoolean("enabled", this.zzbQh.booleanValue());
            this.zzbQh = null;
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzbQb.onStart();
    }

    public final void onStop() {
        super.onStop();
        this.zzbQb.onStop();
    }

    public final void setEnabled(boolean z) {
        if (this.zzbPZ != null) {
            this.zzbPZ.setEnabled(z);
            this.zzbQh = null;
            return;
        }
        this.zzbQh = Boolean.valueOf(z);
    }

    public final void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.zzbQc.zza(onStateChangedListener);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzbPZ != null) {
            this.zzbPZ.updateMaskedWallet(maskedWallet);
            this.zzbQg = null;
            return;
        }
        this.zzbQg = maskedWallet;
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.zzbPZ != null) {
            this.zzbPZ.updateMaskedWalletRequest(maskedWalletRequest);
            this.zzbQf = null;
            return;
        }
        this.zzbQf = maskedWalletRequest;
    }
}
