package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import ru.netology.nmedia.databinding.ActivityFullscreenBinding

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = ActivityFullscreenBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_fullscreen)
    }

}