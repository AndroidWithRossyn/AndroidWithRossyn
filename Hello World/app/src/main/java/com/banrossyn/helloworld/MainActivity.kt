package com.banrossyn.helloworld

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.banrossyn.helloworld.databinding.ActivityMainBinding

/**
 * MainActivity is the entry point of the application.
 * It sets up the main layout and handles window insets for edge-to-edge display.
 * @author Rohitraj Khorwal
 * @since v1.0.0
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.helloWorld.apply {

        }
    }

    override fun onResume() {
        super.onResume()
        startBlipZoomAnimation()
    }

    override fun onPause() {
        super.onPause()
        stopBlipZoomAnimation()
    }


    private var pulseAnimator: AnimatorSet? = null
    private val pulseDuration = 300L
    private fun startBlipZoomAnimation() {
        val scaleUpX = ObjectAnimator.ofFloat(binding.helloWorld, "scaleX", 1f, 1.2f)
            .setDuration(pulseDuration)
        val scaleUpY = ObjectAnimator.ofFloat(binding.helloWorld, "scaleY", 1f, 1.2f)
            .setDuration(pulseDuration)
        val scaleDownX = ObjectAnimator.ofFloat(binding.helloWorld, "scaleX", 1.2f, 1f)
            .setDuration(pulseDuration)
        val scaleDownY = ObjectAnimator.ofFloat(binding.helloWorld, "scaleY", 1.2f, 1f)
            .setDuration(pulseDuration)

        val scaleUp = AnimatorSet().apply { playTogether(scaleUpX, scaleUpY) }
        val scaleDown = AnimatorSet().apply { playTogether(scaleDownX, scaleDownY) }

        pulseAnimator = AnimatorSet().apply {
            playSequentially(scaleUp, scaleDown)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (this@apply == pulseAnimator) startBlipZoomAnimation()
                }
            })
            start()
        }
    }

    private fun stopBlipZoomAnimation() {
        pulseAnimator?.cancel()
        pulseAnimator = null
    }

}