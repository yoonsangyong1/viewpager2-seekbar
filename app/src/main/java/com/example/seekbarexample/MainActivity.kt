package com.example.seekbarexample

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.*
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2


class MainActivity : AppCompatActivity() {

    var currentOffset = 0
    private var itemCount = 5
    lateinit var sk: SeekBar
    var maxOffset = 0
    var seekBarTouch = false
    var progressCalculate = 40
    var indicatorSize = 0
    var padding = 14.dpTopx
    var smallSizeIndicator: Drawable? = null
    var bigSizeIndicator: Drawable? = null

    fun Activity.displayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= 30) {
            display?.apply {
                getRealMetrics(displayMetrics)
            }
        } else {
            // getMetrics() method was deprecated in api level 30
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        return displayMetrics
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val displayMetrics = displayMetrics()
        indicatorSize = (displayMetrics.widthPixels - 30.dpTopx) / itemCount
        maxOffset = displayMetrics.widthPixels
        smallSizeIndicator = customView(Color.BLACK, Color.BLACK, 2, indicatorSize)
        bigSizeIndicator = customView(Color.BLACK, Color.BLACK, 4, indicatorSize + 2.dpTopx)
        sk = findViewById(R.id.seekBar)
        sk.thumb = smallSizeIndicator
        sk.max = (itemCount - 1) * progressCalculate
        sk.setPadding(indicatorSize / 2 + padding, 0, indicatorSize / 2 + padding, 0)
        val vp = findViewById<ViewPager2>(R.id.vp)
        val adapter = VpAdapter()
        vp.adapter = adapter
        adapter.setItem()
        vp.setCurrentItem(itemCount*500, false)

        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (!seekBarTouch) {
                    currentOffset = ((maxOffset / progressCalculate) * ((position%itemCount) * progressCalculate))
                    sk.progress = ((positionOffset*progressCalculate)+((position%itemCount)*progressCalculate)).toInt()
                    Log.d("hoorrr==", (position%itemCount).toString())
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == 0) {
                    sk.thumb = smallSizeIndicator
                } else {
                    sk.thumb = bigSizeIndicator
                }
            }
        })

        sk.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

                if (vp.isFakeDragging) {
                    val offset = ((maxOffset / progressCalculate) * progress)
                    val dragby = -1 * (offset - currentOffset)
                    vp.fakeDragBy(dragby.toFloat())
                    currentOffset = offset
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                seekBarTouch = true
                vp.beginFakeDrag()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                seekBarTouch = false
                vp.endFakeDrag()
            }
        })
    }

    fun customView(
        backgroundColor: Int,
        borderColor: Int,
        stroke: Int,
        indicatorWidth: Int
    ): Drawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.LINE
        shape.cornerRadii = floatArrayOf(8f, 8f, 8f, 8f, 0f, 0f, 0f, 0f)
        shape.setColor(backgroundColor)
        shape.setStroke(stroke.dpTopx, borderColor)
        shape.setSize(indicatorWidth, 13.dpTopx)
        return shape
    }

    val Int.dpTopx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}