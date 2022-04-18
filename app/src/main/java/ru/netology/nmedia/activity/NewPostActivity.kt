package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.dto.Post

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editField.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.editField.text)
        }
    }

    private fun onOkButtonClicked(text: Editable) {
        val intent = Intent()
        if (text.isBlank()) {
            setResult(Activity.RESULT_CANCELED, intent)
        } else {
            val newPostContent = text.toString()
            intent.putExtra(NEW_POST_CONTENT_KEY, newPostContent)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    object ResultContract : ActivityResultContract<Unit, String?>() {

        override fun createIntent(context: Context, input: Unit) =
            Intent(context, NewPostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            intent.takeIf { resultCode == Activity.RESULT_OK }?.getStringExtra(NEW_POST_CONTENT_KEY)
    }

    private companion object {
        const val NEW_POST_CONTENT_KEY = "newPostContent"
    }
}