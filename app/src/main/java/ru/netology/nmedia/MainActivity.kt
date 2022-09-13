package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.activity.PostTextActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostAdapter
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private var draftText = String()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = PostViewModel(application)
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
            if (!postContent.second.isNullOrBlank()) {
                when (postContent.first) {
                    PostTextActivity.TEXT_TO_POST -> {
                        viewModel.savePostContent(postContent.second.toString())
                        draftText = ""
                    }
                    PostTextActivity.TEXT_TO_DRAFT ->
                        if (viewModel.postToEdit.id == 0L) {
                            draftText = postContent.second.toString()
                        } else draftText = ""
                }
            }
        }

        viewModel.editEvent.observe(this) {
            if (viewModel.postToEdit.id == 0L) {
                postTextActivityResultLauncher.launch(draftText)
            } else {
                postTextActivityResultLauncher.launch(viewModel.postToEdit.postText)
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.videoEvent.observe(this) { urlVideo ->
            val address = Uri.parse(urlVideo)
            val intent = Intent(Intent.ACTION_VIEW, address)
            startActivity(intent)
        }
    }
}
