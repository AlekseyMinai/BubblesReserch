package com.alesno.bubblebuttonreserch.ui.conversation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.FragmentConversationBinding
import com.alesno.bubblebuttonreserch.viewBindings

class ConversationFragment : Fragment(R.layout.fragment_conversation) {

    private val mBinding by viewBindings { FragmentConversationBinding.bind(it) }
    private val mViewModel by viewModels<ConversationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.messageList.adapter = mViewModel.adapter
        mViewModel.init()
    }

    companion object {

        private const val TAG_ID = "id"

        fun newInstance(id: String) =
            ConversationFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_ID, id)
                }
            }

    }

}