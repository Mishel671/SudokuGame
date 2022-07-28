package ru.dzyubamichael.sudokugameapp.domain

import android.graphics.Bitmap
import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import java.nio.ByteBuffer
import java.util.*

@Parcelize
data class GameItem(
    val image: Bitmap,
    val gameData: Array<IntArray>,
    var id: Int = UNDEFINED_ID
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }

    fun equals(item: GameItem): Boolean {
        if (id != item.id) return false
        if(!image.compare(item.image)) return false
        if(gameData.size == item.gameData.size){
            for (i in 0..gameData.size -1){
                val list1 = gameData[i]
                val list2 = item.gameData[i]
                if(list1[0] != list2[0] || list1[1] != list2[1]){
                    return false
                }
            }
        } else {
            return false
        }
        return true
    }

    fun Bitmap.compare(bitmap2: Bitmap): Boolean {
        val buffer1: ByteBuffer = ByteBuffer.allocate(this.height * this.rowBytes)
        this.copyPixelsToBuffer(buffer1)
        val buffer2: ByteBuffer = ByteBuffer.allocate(bitmap2.height * bitmap2.rowBytes)
        bitmap2.copyPixelsToBuffer(buffer2)
        return Arrays.equals(buffer1.array(), buffer2.array())
    }

}