package com.example.tbcexercises.presentation.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream

fun Bitmap.compressBitmap(quality: Int = 80): Bitmap {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    val byteArray = stream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Bitmap.rotateImageIfRequired(photoPath: String): Bitmap {
    val ei = ExifInterface(photoPath)
    val orientation = ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )

    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(270f)
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipBitmap(horizontal = true, vertical = false)
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipBitmap(horizontal = false, vertical = true)
        else -> this
    }
}

fun Bitmap.rotateBitmap(degrees: Float): Bitmap {
    val matrix = Matrix().apply {
        postRotate(degrees)
    }
    return Bitmap.createBitmap(
        this,
        0,
        0,
        this.width,
        this.height,
        matrix,
        true
    )
}

fun Bitmap.flipBitmap(horizontal: Boolean, vertical: Boolean): Bitmap {
    val matrix = Matrix().apply {
        if (horizontal) postScale(-1f, 1f, width / 2f, 0f)
        if (vertical) postScale(1f, -1f, 0f, height / 2f)
    }
    return Bitmap.createBitmap(
        this,
        0,
        0,
        width,
        height,
        matrix,
        true
    )
}