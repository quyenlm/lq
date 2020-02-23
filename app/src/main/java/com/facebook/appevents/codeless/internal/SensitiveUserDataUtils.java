package com.facebook.appevents.codeless.internal;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

public class SensitiveUserDataUtils {
    public static boolean isSensitiveUserData(View view) {
        if (!(view instanceof TextView)) {
            return false;
        }
        TextView textView = (TextView) view;
        if (isPassword(textView) || isCreditCard(textView) || isPersonName(textView) || isPostalAddress(textView) || isPhoneNumber(textView) || isEmail(textView)) {
            return true;
        }
        return false;
    }

    private static boolean isPassword(TextView view) {
        if (view.getInputType() == 128) {
            return true;
        }
        return view.getTransformationMethod() instanceof PasswordTransformationMethod;
    }

    private static boolean isEmail(TextView view) {
        if (view.getInputType() == 32) {
            return true;
        }
        String text = ViewHierarchy.getTextOfView(view);
        if (text == null || text.length() == 0) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    private static boolean isPersonName(TextView view) {
        return view.getInputType() == 96;
    }

    private static boolean isPostalAddress(TextView view) {
        return view.getInputType() == 112;
    }

    private static boolean isPhoneNumber(TextView view) {
        return view.getInputType() == 3;
    }

    private static boolean isCreditCard(TextView view) {
        boolean z = true;
        String ccNumber = ViewHierarchy.getTextOfView(view).replaceAll("\\s", "");
        int length = ccNumber.length();
        if (length < 12 || length > 19) {
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        for (int i = length - 1; i >= 0; i--) {
            char digit = ccNumber.charAt(i);
            if (digit < '0' || digit > '9') {
                return false;
            }
            int n = digit - '0';
            if (alternate && (n = n * 2) > 9) {
                n = (n % 10) + 1;
            }
            sum += n;
            if (!alternate) {
                alternate = true;
            } else {
                alternate = false;
            }
        }
        if (sum % 10 != 0) {
            z = false;
        }
        return z;
    }
}
