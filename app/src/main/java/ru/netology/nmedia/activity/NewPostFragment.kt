package ru.netology.nmedia.activity


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.NewPostFragmentBinding


class NewPostFragment : Fragment() {

    private val initialText by lazy {
        arguments?.getString(INITIAL_TEXT_KEY, "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = NewPostFragmentBinding.inflate(inflater, container, false).also { binding ->
        binding.editField.setText(initialText)
        binding.editField.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.editField.text, binding.videoUrl.text)
        }
    }.root

    private fun onOkButtonClicked(text: Editable, videoUrl: Editable) {
        if (text.isNotBlank()) {
            val result = Bundle(1).apply {
                putString(NEW_POST_CONTENT_KEY, text.toString())
                putString(VIDEO_URL_KEY, videoUrl.toString())
            }
            parentFragmentManager.setFragmentResult(REQUEST_KEY, result)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val NEW_POST_CONTENT_KEY = "newPostContent"
        const val VIDEO_URL_KEY = "videoUrlContent"
        const val REQUEST_KEY = "newPost"
        const val INITIAL_TEXT_KEY = "initialText"

        fun createArguments(initialText: String) = Bundle(1).apply {
            putString(INITIAL_TEXT_KEY, initialText)
        }
    }
}