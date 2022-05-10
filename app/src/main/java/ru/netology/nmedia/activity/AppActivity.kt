package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnSuccessListener
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.createArguments


class AppActivity : AppCompatActivity(R.layout.app_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        val intent = intent ?: return

        if (intent.action != Intent.ACTION_SEND) return
        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (text.isNullOrBlank()) return
        intent.removeExtra(Intent.EXTRA_TEXT)
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.globalToNewPostFragment,
            createArguments(text)
        )
    }
}