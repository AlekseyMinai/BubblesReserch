package com.alesno.bubblebuttonreserch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.ActivityBubblesBinding
import com.alesno.bubblebuttonreserch.ui.conversation.ConversationFragment
import com.alesno.bubblebuttonreserch.viewBindings

class BubbleActivity : AppCompatActivity(R.layout.activity_bubbles) {

    private val mBinding by viewBindings(ActivityBubblesBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val conversationId = intent.data?.lastPathSegment ?: return
        openConversationFragment(conversationId)
    }

    private fun openConversationFragment(conversationId: String) {
        if (supportFragmentManager.findFragmentByTag(CONVERSATION_FRAGMENT_TAG) != null) {
            return
        }
        supportFragmentManager.commit {
            add(
                R.id.fragmentContainer,
                ConversationFragment.newInstance(conversationId, true),
                CONVERSATION_FRAGMENT_TAG
            ).addToBackStack(CONVERSATION_FRAGMENT_TAG)
        }
    }

    companion object {
        private const val CONVERSATION_FRAGMENT_TAG = "conversation_fragment"
    }

}