package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.PostTextActivityBinding
import ru.netology.nmedia.showKeyboard

class PostTextActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = PostTextActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (!text.isNullOrBlank()) {
            binding.content.setText(text.toString())
        }
        binding.content.requestFocus()
        showKeyboard(binding.root)

        binding.okButton.setOnClickListener {
            val postText = binding.content.text

            if (postText.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                intent.putExtra(Intent.EXTRA_TEXT, postText.toString())
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

    object ResultContract : ActivityResultContract<String?, String?>() {
        override fun createIntent(context: Context, input: String?): Intent {
            val intent = Intent(context, PostTextActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, input.toString())
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
           if (resultCode == Activity.RESULT_OK) {
               return intent?.getStringExtra(Intent.EXTRA_TEXT)
           } else {
               return null
           }
        }
    }
}