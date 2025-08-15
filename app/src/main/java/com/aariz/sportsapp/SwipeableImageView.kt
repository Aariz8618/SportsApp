package com.aariz.sportsapp

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView

class SwipeableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private val gestureDetector: GestureDetector
    private val start = PointF()
    var onSwipeListener: OnSwipeListener? = null

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                start.set(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                val deltaX = event.x - start.x
                val deltaY = event.y - start.y
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > 100) {
                    if (deltaX > 0) {
                        onSwipeListener?.onSwipeRight()
                    } else {
                        onSwipeListener?.onSwipeLeft()
                    }
                }
            }
        }
        return true
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            // Handle single tap if needed
            return true
        }
    }

    interface OnSwipeListener {
        fun onSwipeLeft()
        fun onSwipeRight()
    }
} 