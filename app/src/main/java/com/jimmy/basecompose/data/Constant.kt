package com.jimmy.basecompose.data

import com.jimmy.basecompose.BuildConfig

object Constant {
    public const val AUTH0_CLIENT_ID = "XlI3IWWyvksUwT7fNfaxP010Kli3EU2V"
    public const val AUTH0_DOMAIN_ID = "dev-cdsjhbb2scqzroqi.us.auth0.com"
    public const val AUTH0_CLIENT_SECRET = "UJaW2HTAnbQx2MLMLAZbtX4ZfCo66j383tfGaai2ZE2lAgl85uilB1N7nOccxUVr"
    public const val AUTH0_CALLBACK_URL = "demo://${AUTH0_DOMAIN_ID}/android/${BuildConfig.APPLICATION_ID}/callback"
    public const val AUTH0_LOGOUT_URL = "demo://${AUTH0_DOMAIN_ID}/android/${BuildConfig.APPLICATION_ID}/callback"
}