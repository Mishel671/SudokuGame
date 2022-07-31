package ru.dzyubamichael.sudokugameapp.presentation.choosegame.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import ru.dzyubamichael.sudokugameapp.R
import ru.dzyubamichael.sudokugameapp.databinding.GameItemBinding
import ru.dzyubamichael.sudokugameapp.domain.GameItem

class GamesListAdapter(
    private val context: Context
) : ListAdapter<GameItem, GameViewHolder>(GamesItemCallback) {

    var onGameClickListener: ((GameItem, ConstraintLayout) -> Unit)? = null
    var onPressedListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        binding.gameImage.setImageBitmap(item.image)
        binding.root.transitionName = item.id.toString()
        binding.tvGameName.text =
            String.format(context.getString(R.string.game_title_item), item.id)

        val animComing =
            AnimatorInflater.loadAnimator(context, R.animator.coming_item_animator) as AnimatorSet
        animComing.setTarget(binding.root)

        val animDelete =
            AnimatorInflater.loadAnimator(context, R.animator.delete_item_animator) as AnimatorSet
        animDelete.setTarget(binding.root)

        var animDeleteCancel = false
        animDelete.addListener(object : AnimListener() {
            override fun onAnimationEnd(p0: Animator?) {
                if (!animDeleteCancel) {
                    onPressedListener?.invoke(item.id)
                }
            }
        })

        var lastDown = 0L
        var lastDuration = 0L
        binding.root.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                if (event == null) return false

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastDown = System.currentTimeMillis()
                    }
                    MotionEvent.ACTION_UP -> {
                        lastDuration = System.currentTimeMillis() - lastDown

                        if (animDelete.isRunning) {
                            animDeleteCancel = true
                            animDelete.cancel()
                            animComing.start()

                        }

                        if (lastDuration < 200 && lastDuration > 0) {
                            onGameClickListener?.invoke(item, binding.root)
                        }
                        lastDown = 0
                        lastDuration = 0
                    }
                }
                val pressed = System.currentTimeMillis()
                Log.d("MainLog", "lastDown: $lastDown, lastDuration: $lastDuration, pressed: ${pressed - lastDown}, animRunnin = ${animDelete.isRunning}" )
                if (lastDown != 0L && lastDuration == 0L && pressed - lastDown > 200 && !animDelete.isRunning) {
                    lastDown = 0
                    animDelete.start()
                    animDeleteCancel = false
                }
                return true
            }
        })
    }

}