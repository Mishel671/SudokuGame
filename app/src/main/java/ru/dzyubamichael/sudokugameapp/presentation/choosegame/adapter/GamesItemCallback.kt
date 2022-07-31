package ru.dzyubamichael.sudokugameapp.presentation.choosegame.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import ru.dzyubamichael.sudokugameapp.domain.GameItem

object GamesItemCallback: DiffUtil.ItemCallback<GameItem>() {
    override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
        return oldItem.equals(newItem)
    }
}