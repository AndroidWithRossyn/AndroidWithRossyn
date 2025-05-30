package com.androidwithrossyn.helloworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidwithrossyn.helloworld.databinding.ActivityMainBinding
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin

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
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied.
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

        val markdown = assets.open("README.md").bufferedReader().use { it.readText() }
        val markwon = Markwon.builder(this)
            .usePlugin(HtmlPlugin.create())
            .build()
        markwon.setMarkdown(binding.descriptionText, markdown)
    }
}