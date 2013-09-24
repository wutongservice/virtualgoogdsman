package com.borqs.market.account;


public interface AccountListener {
    public void onLogin();
    public void onLogout();
	public void onCancelLogin();	
}
