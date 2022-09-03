package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostAdapter
import ru.netology.nmedia.viewmodel.PostViewModel
import java.util.*

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

        fun endContextEdit(view:View) {
            viewModel.setEditPostEmpty()
            binding.content.clearFocus()
            hideKeyboard(view)
        }

        binding.applyPostButton.setOnClickListener {
            with(binding.content) {
                if (text.isNotBlank()) {
                    if (viewModel.isNewPost()) {
                        viewModel.new(
                            Post(
                                0,
                                "'Me",
                                Date(),
                                text.toString(),
                                false,
                                0,
                                0,
                                0
                            )
                        )
                    } else {
                        viewModel.edit(
                            viewModel.editPost.value!!.id,
                            text.toString()
                        )
                    }
                }
                endContextEdit(this)
            }
        }

        binding.discardPostButton.setOnClickListener {
            endContextEdit(binding.content)
        }

        viewModel.editPost.observe(this) {
            with (binding.content) {
                setText(it.postText)
                if (text.isNotBlank()) {
                    requestFocus()
                    showKeyboard(this)
                }
            }
        }

        binding.content.setOnFocusChangeListener { _, _ ->
            with(binding) {
                if (content.isFocused) {
                    discardPostButton.visibility = VISIBLE
                } else {
                    discardPostButton.visibility = GONE
                }
            }
        }
    }

}
