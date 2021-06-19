package com.alesno.bubblebuttonreserch.ui.conversation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.FragmentConversationBinding
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.ConversationViewState
import com.alesno.bubblebuttonreserch.utils.uiLazy
import com.alesno.bubblebuttonreserch.viewBindings
import com.bumptech.glide.Glide

class ConversationFragment : Fragment(R.layout.fragment_conversation) {

    private val mBinding by viewBindings { FragmentConversationBinding.bind(it) }
    private val mViewModel by viewModels<ConversationViewModel>()
    private val mAdapter by uiLazy { ConversationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.messageList.adapter = mAdapter
        initToolbar()
        mViewModel.init()
        subscribeToViewModel()
    }

    private fun initToolbar() = with(mBinding) {
        val isFromBubble = arguments?.getBoolean(TAG_FROM_BUBBLE) ?: false
        if (isFromBubble) {
            appBar.visibility = View.GONE
        }
    }

    private fun subscribeToViewModel() {
        mViewModel.conversationState.observe(viewLifecycleOwner, ::render)
    }

    private fun render(conversationViewState: ConversationViewState) = with(mBinding) {
        val context = context ?: return@with
        mAdapter.submitList(conversationViewState.messages)
        Glide.with(context).load(conversationViewState.participant.avatarUrl)
            .circleCrop()
            .into(participantAvatar)
        participantName.text = conversationViewState.participant.name
    }

    companion object {

        private const val TAG_ID = "id"
        private const val TAG_FROM_BUBBLE = "from_bubble"

        fun newInstance(id: String, fromBubble: Boolean) =
            ConversationFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_ID, id)
                    putBoolean(TAG_FROM_BUBBLE, fromBubble)
                }
            }

    }

}