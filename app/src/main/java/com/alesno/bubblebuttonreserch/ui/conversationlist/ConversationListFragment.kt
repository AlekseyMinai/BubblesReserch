package com.alesno.bubblebuttonreserch.ui.conversationlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.FragmentConversationListBinding
import com.alesno.bubblebuttonreserch.ui.conversation.ConversationFragment
import com.alesno.bubblebuttonreserch.viewBindings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConversationListFragment : Fragment(R.layout.fragment_conversation_list) {

    private val mBinding = viewBindings { FragmentConversationListBinding.bind(it) }
    private val mViewModel by viewModels<ConversationListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.value.conversationList.adapter = mViewModel.adapter
        mViewModel.init()
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        mViewModel.openConversation
            .onEach(::openConversationFragment)
            .launchIn(lifecycleScope)
    }

    private fun openConversationFragment(conversationId: String) {
        if (childFragmentManager.findFragmentByTag(CONVERSATION_FRAGMENT_TAG) != null) {
            return
        }
        childFragmentManager.commit {
            add(
                R.id.fragmentConversationContainer,
                ConversationFragment.newInstance(conversationId, false),
                CONVERSATION_FRAGMENT_TAG
            ).addToBackStack(CONVERSATION_FRAGMENT_TAG)
        }
    }

    companion object {
        private const val CONVERSATION_FRAGMENT_TAG = "conversation_fragment"

        fun newInstance() = ConversationListFragment()

    }

}