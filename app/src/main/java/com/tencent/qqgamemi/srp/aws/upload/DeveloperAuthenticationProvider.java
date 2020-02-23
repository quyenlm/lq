package com.tencent.qqgamemi.srp.aws.upload;

import com.amazonaws.auth.AWSAbstractCognitoDeveloperIdentityProvider;
import com.amazonaws.regions.Regions;
import com.tencent.qqgamemi.srp.aws.util.GlobalConfig;

public class DeveloperAuthenticationProvider extends AWSAbstractCognitoDeveloperIdentityProvider {
    private String TAG = "DeveloperAuthenProvider";
    private String m_identityId = null;

    public DeveloperAuthenticationProvider(String accountId, String identityPoolId, Regions region) {
        super(accountId, identityPoolId, region);
    }

    public String getProviderName() {
        return GlobalConfig.AWS_PROVIDER_NAME;
    }

    public void refresh(String token, String identityID) {
        this.m_identityId = identityID;
        setToken((String) null);
        update(identityID, token);
    }

    public String getIdentityId() {
        return this.m_identityId;
    }
}
