package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.common.R;
import com.facebook.login.LoginClient;

public class LoginFragment extends Fragment {
    static final String EXTRA_REQUEST = "request";
    private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
    static final String REQUEST_KEY = "com.facebook.LoginFragment:Request";
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";
    private static final String TAG = "LoginFragment";
    private String callingPackage;
    private LoginClient loginClient;
    private LoginClient.Request request;

    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.loginClient = (LoginClient) savedInstanceState.getParcelable(SAVED_LOGIN_CLIENT);
            this.loginClient.setFragment(this);
        } else {
            this.loginClient = createLoginClient();
        }
        this.loginClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
            public void onCompleted(LoginClient.Result outcome) {
                LoginFragment.this.onLoginClientCompleted(outcome);
            }
        });
        Activity activity = getActivity();
        if (activity != null) {
            initializeCallingPackage(activity);
            Intent intent = activity.getIntent();
            if (intent != null && (bundle = intent.getBundleExtra(REQUEST_KEY)) != null) {
                this.request = (LoginClient.Request) bundle.getParcelable("request");
            }
        }
    }

    /* access modifiers changed from: protected */
    public LoginClient createLoginClient() {
        return new LoginClient((Fragment) this);
    }

    public void onDestroy() {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.com_facebook_login_fragment, container, false);
        final View progressBar = view.findViewById(R.id.com_facebook_login_fragment_progress_bar);
        this.loginClient.setBackgroundProcessingListener(new LoginClient.BackgroundProcessingListener() {
            public void onBackgroundProcessingStarted() {
                progressBar.setVisibility(0);
            }

            public void onBackgroundProcessingStopped() {
                progressBar.setVisibility(8);
            }
        });
        return view;
    }

    /* access modifiers changed from: private */
    public void onLoginClientCompleted(LoginClient.Result outcome) {
        this.request = null;
        int resultCode = outcome.code == LoginClient.Result.Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, outcome);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        if (isAdded()) {
            getActivity().setResult(resultCode, resultIntent);
            getActivity().finish();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e(TAG, NULL_CALLING_PKG_ERROR_MSG);
            getActivity().finish();
            return;
        }
        this.loginClient.startOrContinueAuth(this.request);
    }

    public void onPause() {
        View progressBar;
        super.onPause();
        if (getView() == null) {
            progressBar = null;
        } else {
            progressBar = getView().findViewById(R.id.com_facebook_login_fragment_progress_bar);
        }
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loginClient.onActivityResult(requestCode, resultCode, data);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LOGIN_CLIENT, this.loginClient);
    }

    private void initializeCallingPackage(Activity activity) {
        ComponentName componentName = activity.getCallingActivity();
        if (componentName != null) {
            this.callingPackage = componentName.getPackageName();
        }
    }

    /* access modifiers changed from: package-private */
    public LoginClient getLoginClient() {
        return this.loginClient;
    }
}
