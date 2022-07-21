package com.github.youssefwadie.payroll.security;

public class SecurityConstants {
    public static final String JWT_KEY = "bKesqacWqN5k52v2CxA3QWhagXwzN3L9hmWREJxxnFzb7p555bEEUM2cZLWWS9bR";
    public static final String JWT_COOKIE_NAME = "access_token";

    public static final int TOKEN_LIFETIME = 1000 * 60 * 60 * 24 * 7;      // one week
}
