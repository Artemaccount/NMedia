package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.NewPostFragmentBinding
import ru.netology.nmedia.dto.Post

class EditPostFragment : Fragment() {

    private val initialPost by lazy<Post> {
        requireArguments().getParcelable(EDITING_POST_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = NewPostFragmentBinding.inflate(inflater, container, false).also { binding ->
        binding.editField.setText(initialPost.content)
        binding.videoUrl.setText(initialPost.video)
        binding.editField.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.editField.text, binding.videoUrl.text)
        }
    }.root

    private fun onOkButtonClicked(text: Editable, videoUrl: Editable) {
        if (text.isNotBlank()) {
            val editedPost =
                initialPost.copy(content = text.toString(), video = videoUrl.toString())
            val result = Bundle(2).apply {
                putParcelable(EDITING_POST_KEY, editedPost)
            }
            parentFragmentManager.setFragmentResult(RESULT_KEY, result)
            findNavController().navigate(R.id.feedFragment)

        }
//        findNavController().popBackStack()
        //findNavController().navigate(R.id.toFeedFragment)

    }

    companion object {
        const val EDITING_POST_KEY = "content"
        const val VIDEO_URL_KEY = "videoUrlContent"
        const val RESULT_KEY = "result"
    }
}
