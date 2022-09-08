package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.activity.PostTextActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostAdapter
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        val adapter = PostAdapter(viewModel)

        binding.listView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }

        viewModel.textToShare.observe(this) { postText ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.share_caption))
            startActivity(shareIntent)
        }

        val postTextActivityResultLauncher = registerForActivityResult(PostTextActivity.ResultContract) { postContent ->
            if (!postContent.isNullOrBlank()) {
                viewModel.savePostContent(postContent)
            }
        }

        viewModel.editEvent.observe(this) {
            postTextActivityResultLauncher.launch(viewModel.postToEdit.postText)
        }

        binding.addButton.setOnClickListener {
            viewModel.onAddClicked()
        }
    }
}
