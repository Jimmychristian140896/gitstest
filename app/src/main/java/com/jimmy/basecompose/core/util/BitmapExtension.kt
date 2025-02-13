package com.jimmy.tasky.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.jimmy.basecompose.R
import com.jimmy.basecompose.core.ui.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.concurrent.write

fun Bitmap.getByteArray(compress: Boolean = false): ByteArray {
    val stream = ByteArrayOutputStream()

    val compressionQuality = if (compress) 100 else 25
    compress(Bitmap.CompressFormat.JPEG, compressionQuality, stream)
    return stream.toByteArray()
}

fun ByteArray.isGreaterThan1MB() = size > 1024 * 1024

suspend fun Context.getPhotoByteArray(url: String): ByteArray? {
    return withContext(Dispatchers.IO) {
        val imageStream: InputStream? = contentResolver.openInputStream(Uri.parse(url))
        val bitmap = BitmapFactory.decodeStream(imageStream)

        var byteArray = bitmap.getByteArray()
        if (byteArray.isGreaterThan1MB()) {
            byteArray = bitmap.getByteArray(compress = true)
        }

        if (byteArray.isGreaterThan1MB()) {
            // return null for byte arrays greater than 1MB even after compression
            showToast(R.string.photo_too_large)
            null
        } else {
            byteArray
        }
    }
}

suspend fun Context.saveByteArrayToInternalStorage(byteArray: ByteArray, fileName: String) {
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
        fileOutputStream.write(byteArray)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        fileOutputStream?.close()
    }
}


fun Context.saveByteArrayToImageFile(byteArray: ByteArray, file: File): Boolean {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    var outputStream: FileOutputStream? = null
    return try {
        outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream?.flush()
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    } finally {
        outputStream?.close()
    }
}