package com.astontraineeship.wheelfortune

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.request.CachePolicy
import coil.size.Scale
import com.astontraineeship.wheelfortune.databinding.ActivityMainBinding
import kotlin.properties.Delegates

private const val URL = "https://placebeard.it/640"
private const val RED = "RED"
private const val ORANGE = "ORANGE"
private const val YELLOW = "YELLOW"
private const val GREEN = "GREEN"
private const val LIGHT_BLUE = "LIGHT BLUE"
private const val BLUE = "BLUE"
private const val PURPLE = "PURPLE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wheelFortuneView: WheelFortuneView
    private lateinit var image: ImageView
    private lateinit var text: CustomTextView
    private lateinit var seekBar: SeekBar
    private lateinit var levelSeekbar: TextView
    private var scale by Delegates.notNull<Int>()
    private var lastAngle = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wheelFortuneView = binding.wheelFortune
        image = binding.image
        text = binding.textView
        seekBar = binding.seekbar
        levelSeekbar = binding.levelSeekbar

        wheelFortuneView.setChangeAngleListener(object : AngleListener {
            override fun angle(angle: Int) {
                lastAngle = angle
                spinView()
            }
        })

        seekBar.progress = wheelFortuneView.getScale()
        levelSeekbar.text = seekBar.progress.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar, p1: Int, p2: Boolean) {
                wheelFortuneView.setScale(seekbar.progress)
                levelSeekbar.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //Nothing
            }

            override fun onStopTrackingTouch(seekbar: SeekBar) {
                wheelFortuneView.setScale(seekbar.progress)
                levelSeekbar.text = seekBar.progress.toString()
            }

        })

        binding.buttonReset.setOnClickListener {
            reset()
        }
    }

    private fun spinView() {

        val angleSegment = 360/7F

        when((360f - lastAngle%360f)) {
            in 0f..angleSegment -> {
                showText(RED, resources.getColor(R.color.red))
                Toast.makeText(this, RED, Toast.LENGTH_LONG).show()
            }
            in angleSegment..angleSegment*2 -> {
                showImage()
                Toast.makeText(this, ORANGE, Toast.LENGTH_LONG).show()
            }
            in angleSegment*2..angleSegment*3 -> {
                showText(YELLOW, resources.getColor(R.color.yellow))
                Toast.makeText(this, YELLOW, Toast.LENGTH_LONG).show()
            }
            in angleSegment*3..angleSegment*4 -> {
                showImage()
                Toast.makeText(this, GREEN, Toast.LENGTH_LONG).show()
            }
            in angleSegment*4..angleSegment*5 -> {
                showText(LIGHT_BLUE, resources.getColor(R.color.light_blue))
                Toast.makeText(this, LIGHT_BLUE, Toast.LENGTH_LONG).show()
            }
            in angleSegment*5..angleSegment*6 -> {
                showImage()
                Toast.makeText(this, BLUE, Toast.LENGTH_LONG).show()
            }
            in angleSegment*6..angleSegment*7 -> {
                showText(PURPLE, resources.getColor(R.color.purple))
                Toast.makeText(this, PURPLE, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showText(colorText: String, colorInt: Int) {
        text.setText(colorText, colorInt)
        image.visibility = ImageView.GONE
        text.visibility = TextView.VISIBLE
    }

    private fun showImage() {
        text.visibility = TextView.GONE
        image.visibility = ImageView.VISIBLE

        image.load(URL) {
            memoryCachePolicy(CachePolicy.DISABLED)
            crossfade(500)
            scale(Scale.FILL)
            build()
        }
    }

    private fun reset() {
        image.visibility = ImageView.GONE
        text.visibility = TextView.GONE
        wheelFortuneView.reset()
        seekBar.progress = 100
    }
}