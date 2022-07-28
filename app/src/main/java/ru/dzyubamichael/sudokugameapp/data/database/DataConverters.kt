package ru.dzyubamichael.sudokugameapp.data.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.dzyubamichael.sudokugameapp.data.utils.CompressBitmap

class DataConverters {

    @TypeConverter
    fun convertListToString(list: Array<IntArray>): String {
        val gson = Gson()
        val type = object : TypeToken<Array<IntArray>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): Array<IntArray> {
        val gson = Gson()
        val type = object : TypeToken<Array<IntArray>>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        return CompressBitmap.getCompressedBitmapData(bitmap, 1000000, 500)
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}