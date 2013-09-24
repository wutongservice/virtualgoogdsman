package com.borqs.market.account;

public class AccountSession {

    public static boolean isLogin = false;
    public static String account_id;
    public static String account_session;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setLogin(boolean isLogin) {
        AccountSession.isLogin = isLogin;
    }

    public static String getAccount_id() {
        return account_id;
    }

    public static void setAccount_id(String account_id) {
        AccountSession.account_id = account_id;
    }

    public static String getAccount_session() {
        return account_session;
    }

    public static void setAccount_session(String account_session) {
        AccountSession.account_session = account_session;
    }
    
    
}
