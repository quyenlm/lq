package com.subao.common;

import MTT.EFvrECode;

/* compiled from: ErrorCode */
public class b {
    public static boolean a(int i) {
        switch (i) {
            case 2003:
            case 2004:
            case 2005:
            case 2007:
            case 2008:
            case 2009:
            case 2010:
            case 2011:
            case 2012:
            case EFvrECode._ERR_FVR_NONSUPPORT /*2013*/:
            case EFvrECode._ERR_FVR_IMGCVT_EXCEPTION /*2014*/:
            case EFvrECode._ERR_FVR_IMGCVT_ERR /*2015*/:
            case EFvrECode._ERR_FVR_UNIID_EXCEPTION /*2016*/:
            case EFvrECode._ERR_FVR_URLINFO_EXCEPTION /*2017*/:
                return true;
            default:
                return i >= 2100 && i <= 2200;
        }
    }
}
