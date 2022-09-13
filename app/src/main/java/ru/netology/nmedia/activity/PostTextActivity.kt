package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
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
        showKeyboard(binding.content)

        binding.okButton.setOnClickListener {
            val postText = binding.content.text

            if (postText.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                intent.putExtra(POST_TEXT, postText.toString())
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

    override fun onBackPressed() {
        val postText = findViewById<EditText>(R.id.content).text
        if (!postText.isNullOrBlank()) {
            intent.putExtra(DRAFT_TEXT, postText.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else super.onBackPressed()
    }

    object ResultContract : ActivityResultContract<String?, Pair<Int, String?>>() {
        override fun createIntent(context: Context, input: String?): Intent {
            val intent = Intent(context, PostTextActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, input.toString())
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Pair<Int, String?> {
           if ((resultCode == Activity.RESULT_OK) &&
               (intent?.getStringExtra(POST_TEXT).isNullOrBlank())) {
               return Pair(TEXT_TO_DRAFT, intent?.getStringExtra(DRAFT_TEXT))
           } else {
               return Pair(TEXT_TO_POST, intent?.getStringExtra(POST_TEXT))
           }
        }
    }

    companion object {
        const val POST_TEXT = "Post text"
        const val DRAFT_TEXT = "Draft text"
        const val TEXT_TO_POST = 0
        const val TEXT_TO_DRAFT = 1
    }
}