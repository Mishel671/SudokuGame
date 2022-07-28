package ru.dzyubamichael.sudokugameapp.data.utils

import android.graphics.Bitmap

import java.io.ByteArrayOutputStream


object CompressBitmap {
    fun getCompressedBitmapData(bitmap: Bitmap, maxFileSize: Int, maxDimensions: Int): ByteArray {
        val resizedBitmap: Bitmap
        resizedBitmap = if (bitmap.width > maxDimensions || bitmap.height > maxDimensions) {
            getResizedBitmap(
                bitmap,
                maxDimensions
            )
        } else {
            bitmap
        }
        var bitmapData = getByteArray(resizedBitmap)
        while (bitmapData.size > maxFileSize) {
            bitmapData = getByteArray(resizedBitmap)
        }
        return bitmapData
    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(
            image,
            width,
            height,
            true
        )
    }

    private fun getByteArray(bitmap: Bitmap): ByteArray {
        val bos = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            80,
            bos
        )
        return bos.toByteArray()
    }
}