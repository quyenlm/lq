package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public final class jz {
    private static SparseArray<Pair<String, String>> zzbWG;

    static {
        SparseArray<Pair<String, String>> sparseArray = new SparseArray<>();
        zzbWG = sparseArray;
        sparseArray.put(17000, new Pair("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
        zzbWG.put(17002, new Pair("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
        zzbWG.put(17004, new Pair("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
        zzbWG.put(17008, new Pair("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
        zzbWG.put(17009, new Pair("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
        zzbWG.put(17024, new Pair("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
        zzbWG.put(17014, new Pair("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
        zzbWG.put(17012, new Pair("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
        zzbWG.put(17007, new Pair("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
        zzbWG.put(17025, new Pair("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
        zzbWG.put(17005, new Pair("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
        zzbWG.put(17021, new Pair("ERROR_USER_TOKEN_EXPIRED", "The user's credential is no longer valid. The user must sign in again."));
        zzbWG.put(17011, new Pair("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
        zzbWG.put(17017, new Pair("ERROR_INVALID_USER_TOKEN", "The user's credential is no longer valid. The user must sign in again."));
        zzbWG.put(17006, new Pair("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
        zzbWG.put(17026, new Pair("ERROR_WEAK_PASSWORD", "The given password is invalid."));
        zzbWG.put(17029, new Pair("ERROR_EXPIRED_ACTION_CODE", "The out of band code has expired."));
        zzbWG.put(17030, new Pair("ERROR_INVALID_ACTION_CODE", "The out of band code is invalid. This can happen if the code is malformed, expired, or has already been used."));
        zzbWG.put(17031, new Pair("ERROR_INVALID_MESSAGE_PAYLOAD", "The email template corresponding to this action contains invalid characters in its message. Please fix by going to the Auth email templates section in the Firebase Console."));
        zzbWG.put(17033, new Pair("ERROR_INVALID_RECIPIENT_EMAIL", "The email corresponding to this action failed to send as the provided recipient email address is invalid."));
        zzbWG.put(17032, new Pair("ERROR_INVALID_SENDER", "The email template corresponding to this action contains an invalid sender email or name. Please fix by going to the Auth email templates section in the Firebase Console."));
        zzbWG.put(17034, new Pair("ERROR_MISSING_EMAIL", "An email address must be provided."));
        zzbWG.put(17035, new Pair("ERROR_MISSING_PASSWORD", "A password must be provided."));
        zzbWG.put(17041, new Pair("ERROR_MISSING_PHONE_NUMBER", "To send verification codes, provide a phone number for the recipient."));
        zzbWG.put(17042, new Pair("ERROR_INVALID_PHONE_NUMBER", "The format of the phone number provided is incorrect. Please enter the phone number in a format that can be parsed into E.164 format. E.164 phone numbers are written in the format [+][country code][subscriber number including area code]."));
        zzbWG.put(17043, new Pair("ERROR_MISSING_VERIFICATION_CODE", "The Phone Auth Credential was created with an empty sms verification Code"));
        zzbWG.put(17044, new Pair("ERROR_INVALID_VERIFICATION_CODE", "The sms verification code used to create the phone auth credential is invalid. Please resend the verification code sms and be sure use the verification code provided by the user."));
        zzbWG.put(17045, new Pair("ERROR_MISSING_VERIFICATION_ID", "The Phone Auth Credential was created with an empty verification ID"));
        zzbWG.put(17046, new Pair("ERROR_INVALID_VERIFICATION_ID", "The verification ID used to create the phone auth credential is invalid."));
        zzbWG.put(17049, new Pair("ERROR_RETRY_PHONE_AUTH", "An error occurred during authentication using the PhoneAuthCredential. Please retry authentication."));
        zzbWG.put(17051, new Pair("ERROR_SESSION_EXPIRED", "The sms code has expired. Please re-send the verification code to try again."));
        zzbWG.put(17052, new Pair("ERROR_QUOTA_EXCEEDED", "The sms quota for this project has been exceeded."));
        zzbWG.put(17028, new Pair("ERROR_APP_NOT_AUTHORIZED", "This app is not authorized to use Firebase Authentication. Please verifythat the correct package name and SHA-1 are configured in the Firebase Console."));
        zzbWG.put(17053, new Pair("ERROR_API_NOT_AVAILABLE", "The API that you are calling is not available on devices without Google Play Services."));
    }

    public static FirebaseException zzK(Status status) {
        int statusCode = status.getStatusCode();
        Pair pair = zzbWG.get(statusCode);
        String zza = zza(pair != null ? (String) pair.second : "An internal error happened", status);
        switch (statusCode) {
            case 17000:
                return new FirebaseAuthInvalidCredentialsException(zzbT(statusCode), zza);
            case 17002:
            case 17004:
            case 17008:
            case 17009:
            case 17024:
                return new FirebaseAuthInvalidCredentialsException(zzbT(statusCode), zza);
            case 17005:
            case 17011:
            case 17017:
            case 17021:
                return new FirebaseAuthInvalidUserException(zzbT(statusCode), zza);
            case 17006:
                return new FirebaseAuthException(zzbT(statusCode), zza);
            case 17007:
            case 17012:
            case 17025:
                return new FirebaseAuthUserCollisionException(zzbT(statusCode), zza);
            case 17010:
                return new FirebaseTooManyRequestsException(zza("We have blocked all requests from this device due to unusual activity. Try again later.", status));
            case 17014:
                return new FirebaseAuthRecentLoginRequiredException(zzbT(statusCode), zza);
            case 17015:
                return new FirebaseException(zza("User has already been linked to the given provider.", status));
            case 17016:
                return new FirebaseException(zza("User was not linked to an account with the given provider.", status));
            case 17020:
                return new FirebaseNetworkException(zza("A network error (such as timeout, interrupted connection or unreachable host) has occurred.", status));
            case 17026:
                return new FirebaseAuthWeakPasswordException(zzbT(statusCode), zza, status.getStatusMessage());
            case 17028:
                return new FirebaseAuthException(zzbT(statusCode), zza);
            case 17029:
            case 17030:
                return new FirebaseAuthActionCodeException(zzbT(statusCode), zza);
            case 17031:
            case 17032:
            case 17033:
                return new FirebaseAuthEmailException(zzbT(statusCode), zza);
            case 17034:
            case 17035:
            case 17041:
            case 17042:
            case 17043:
            case 17044:
            case 17045:
            case 17046:
            case 17049:
            case 17051:
                return new FirebaseAuthInvalidCredentialsException(zzbT(statusCode), zza);
            case 17052:
                return new FirebaseTooManyRequestsException(zza);
            case 17053:
                return new FirebaseApiNotAvailableException(zza);
            case 17495:
                return new aaf(zza("Please sign in before trying to get a token.", status));
            case 17499:
                return new FirebaseException(zza("An internal error has occurred.", status));
            default:
                return new FirebaseException("An internal error has occurred.");
        }
    }

    private static String zza(String str, Status status) {
        if (TextUtils.isEmpty(status.getStatusMessage())) {
            return str;
        }
        return String.format(String.valueOf(str).concat(" [ %s ]"), new Object[]{status.getStatusMessage()});
    }

    private static String zzbT(int i) {
        Pair pair = zzbWG.get(i);
        return pair != null ? (String) pair.first : "INTERNAL_ERROR";
    }
}
