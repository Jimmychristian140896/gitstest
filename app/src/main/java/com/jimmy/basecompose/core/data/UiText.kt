package com.jimmy.basecompose.core.data

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jimmy.basecompose.R
import com.jimmy.basecompose.core.ui.showToast

sealed interface UiText {
    data class DynamicString(val value: String): UiText
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = id, *args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.HttpError.REDIRECT -> UiText.StringResource(R.string.error_redirect)
       DataError.HttpError.UNAUTHORIZED -> UiText.StringResource(R.string.error_unauthorized)
       DataError.HttpError.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timed_out)
       DataError.HttpError.CONFLICT_LOGIN -> UiText.StringResource(R.string.error_conflict_login)
       DataError.HttpError.CONFLICT_SIGN_UP -> UiText.StringResource(R.string.error_conflict_sign_up)
       DataError.HttpError.PAYLOAD_TOO_LARGE -> UiText.StringResource(R.string.error_payload_too_large)
       DataError.HttpError.CLIENT_REQUEST -> UiText.StringResource(R.string.error_client)
       DataError.HttpError.SERVER_RESPONSE -> UiText.StringResource(R.string.error_server)
       DataError.HttpError.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
       DataError.LocalError.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
       DataError.LocalError.NOT_FOUND -> UiText.StringResource(R.string.error_not_found)
       DataError.LocalError.USER_IS_LOGGED_OUT -> UiText.StringResource(R.string.error_user_is_logged_out)
       DataError.LocalError.CANNOT_FETCH_USERS -> UiText.StringResource(R.string.error_cannot_fetch_users)
       DataError.LocalError.NO_INTERNET_CONNECTION -> UiText.StringResource(R.string.error_no_internet_connection)
        DataError.HttpError.FORBIDDEN -> UiText.StringResource(R.string.api_error_forbidden)
        DataError.HttpError.NOT_FOUND -> UiText.StringResource(R.string.api_error_not_found)
        DataError.HttpError.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.api_error_too_many_requests)
        DataError.HttpError.SERVER_ERROR -> UiText.StringResource(R.string.api_error_server_error)
        DataError.HttpError.NO_INTERNET -> UiText.StringResource(R.string.api_error_no_internet)
        DataError.HttpError.CONNECTION_TIMEOUT -> UiText.StringResource(R.string.api_error_connection_timeout)
        DataError.HttpError.SERIALIZATION -> UiText.StringResource(R.string.api_error_serialization)
    }
}

fun DataError.showToast(context: Context) {
    asUiText().asString(context).showToast(context)
}

//fun HttpResponse.isSuccess(): Boolean = status.value in 200..299