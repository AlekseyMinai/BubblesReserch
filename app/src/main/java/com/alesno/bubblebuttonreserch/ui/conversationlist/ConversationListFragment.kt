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
import com.alesno.bubblebuttonreserch.ui.setting.SettingsFragment
import com.alesno.bubblebuttonreserch.viewBindings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConversationListFragment : Fragment(R.layout.fragment_conversation_list) {

    private val mBinding by viewBindings { FragmentConversationListBinding.bind(it) }
    private val mViewModel by viewModels<ConversationListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.conversationList.adapter = mViewModel.adapter
        mViewModel.init()
        initToolbar()
        subscribeToViewModel()
    }

    fun onBackPressed(): Boolean {
        // FIXME: 6/20/2021 Придумать что-то получше с навигацией по фрагментам, может переписать на navigation architecture component
        val conversationFragment = childFragmentManager.findFragmentByTag(CONVERSATION_FRAGMENT_TAG)
        if (conversationFragment != null) {
            childFragmentManager.popBackStack()
            return true
        }
        val settingsFragment = childFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT_TAG)
        if (settingsFragment != null) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }

    private fun initToolbar() = with(mBinding) {
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.setting -> openFragment(
                    tag = SETTINGS_FRAGMENT_TAG,
                    fragment = SettingsFragment.newInstance()
                )
            }

            return@setOnMenuItemClickListener false
        }
    }

    private fun subscribeToViewModel() {
        mViewModel.openConversation
            .onEach {
                val fragment = ConversationFragment.newInstance(it, false)
                openFragment(CONVERSATION_FRAGMENT_TAG, fragment)
            }
            .launchIn(lifecycleScope)
    }

    private fun openFragment(tag: String, fragment: Fragment) {
        if (childFragmentManager.findFragmentByTag(tag) != null) {
            return
        }
        childFragmentManager.commit {
            add(R.id.fragmentConversationContainer, fragment, tag).addToBackStack(tag)
        }
    }

    companion object {
        private const val CONVERSATION_FRAGMENT_TAG = "conversation_fragment"
        private const val SETTINGS_FRAGMENT_TAG = "settings_fragment"

        fun newInstance() = ConversationListFragment()

    }

}