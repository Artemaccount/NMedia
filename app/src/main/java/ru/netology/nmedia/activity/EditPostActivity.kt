package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = intent.getStringExtra(CONTENT_KEY)
        binding.editField.setText(text)
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
            intent.putExtra(CONTENT_KEY, newPostContent)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    object ResultContract : ActivityResultContract<String, String?>() {

        override fun createIntent(context: Context, input: String) =
            Intent(context, EditPostActivity::class.java).putExtra(CONTENT_KEY, input)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            intent.takeIf { resultCode == Activity.RESULT_OK }?.getStringExtra(CONTENT_KEY)


    }

    private companion object {
        const val CONTENT_KEY = "content"
    }
}
