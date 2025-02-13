package com.jimmy.basecompose.core.data

sealed interface DataError : Error {
    enum class HttpError : DataError {
        REDIRECT, //3xx
        UNAUTHORIZED, //401
        REQUEST_TIMEOUT, //408
        CONFLICT_LOGIN, //409
        CONFLICT_SIGN_UP, //409
        PAYLOAD_TOO_LARGE, //413
        CLIENT_REQUEST, //4xx
        SERVER_RESPONSE, //5xx
        UNKNOWN,
        FORBIDDEN,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        NO_INTERNET,
        CONNECTION_TIMEOUT,
        SERIALIZATION
    }

    enum class LocalError : DataError {
        USER_IS_LOGGED_OUT,
        DISK_FULL,
        NOT_FOUND,
        CANNOT_FETCH_USERS,
        NO_INTERNET_CONNECTION
    }
}
