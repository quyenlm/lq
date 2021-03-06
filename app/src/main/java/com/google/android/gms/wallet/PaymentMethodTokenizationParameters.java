package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;

public final class PaymentMethodTokenizationParameters extends zza {
    public static final Parcelable.Creator<PaymentMethodTokenizationParameters> CREATOR = new zzy();
    int zzbPI;
    Bundle zzbPK = new Bundle();

    public final class Builder {
        private Builder() {
        }

        public final Builder addParameter(String str, String str2) {
            zzbo.zzh(str, "Tokenization parameter name must not be empty");
            zzbo.zzh(str2, "Tokenization parameter value must not be empty");
            PaymentMethodTokenizationParameters.this.zzbPK.putString(str, str2);
            return this;
        }

        public final PaymentMethodTokenizationParameters build() {
            return PaymentMethodTokenizationParameters.this;
        }

        public final Builder setPaymentMethodTokenizationType(int i) {
            PaymentMethodTokenizationParameters.this.zzbPI = i;
            return this;
        }
    }

    private PaymentMethodTokenizationParameters() {
    }

    PaymentMethodTokenizationParameters(int i, Bundle bundle) {
        this.zzbPI = i;
        this.zzbPK = bundle;
    }

    public static Builder newBuilder() {
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = new PaymentMethodTokenizationParameters();
        paymentMethodTokenizationParameters.getClass();
        return new Builder();
    }

    public final Bundle getParameters() {
        return new Bundle(this.zzbPK);
    }

    public final int getPaymentMethodTokenizationType() {
        return this.zzbPI;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzbPI);
        zzd.zza(parcel, 3, this.zzbPK, false);
        zzd.zzI(parcel, zze);
    }
}
