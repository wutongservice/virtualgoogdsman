package com.borqs.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.borqs.market.account.AccountListener;
import com.borqs.market.account.AccountLoader;
import com.borqs.market.account.AccountLoaderFactory;
import com.borqs.market.account.AccountObserver;
import com.borqs.market.account.AccountSession;
import com.borqs.market.utils.ImageCacheManager;

public class BasicActivity extends FragmentActivity implements AccountListener{
    
    @SuppressWarnings("unused")
    private static final String TAG = "BasicActivity";
    protected final FragmentActivity thiz = this;
    private AccountLoader accountLoader;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        AccountObserver.registerAccountListener(this.getClass().getName(),this);
        if(!AccountSession.isLogin) {
            accountLoader = AccountLoaderFactory.createAccountLoader(this);
            accountLoader.loadAccount();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountObserver.unRegisterAccountListener(this.getClass().getName());
        ImageCacheManager.ContextCache.revokeAllImageView(this);
        accountLoader = null;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(accountLoader != null) {
            accountLoader.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    public void login() {
        if(accountLoader == null) {
            accountLoader = AccountLoaderFactory.createAccountLoader(this);
        }
        accountLoader.login();
    }

    @Override
    public void onLogin() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onLogout() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onCancelLogin() {
        // TODO Auto-generated method stub
        
    }
}
