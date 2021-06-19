package com.alesno.bubblebuttonreserch.ui.conversationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.ItemConversationListBinding
import com.alesno.bubblebuttonreserch.ui.conversationlist.ConversationListAdapter.ConversationListViewHolder
import com.alesno.bubblebuttonreserch.ui.conversationlist.viewstate.ConversationInfoViewState
import com.bumptech.glide.Glide

class ConversationListAdapter(
    private val onOpenConversation: (conversationId: String) -> Unit
) : ListAdapter<ConversationInfoViewState, ConversationListViewHolder>(ConversationListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationListViewHolder =
        ConversationListViewHolder(
            ItemConversationListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ConversationListViewHolder, position: Int) {
        val conversationInfo = getItem(position)
        with(holder.binding) {
            Glide.with(root.context)
                .load(conversationInfo.participant.avatarUrl)
                .error(ContextCompat.getDrawable(root.context, R.drawable.patric))
                .circleCrop()
                .into(participantAvatar)
            participantName.text = conversationInfo.participant.name
            root.setOnClickListener { onOpenConversation(conversationInfo.conversationId) }
        }
    }

    inner class ConversationListViewHolder(
        val binding: ItemConversationListBinding
    ) : RecyclerView.ViewHolder(binding.root)

}

object ConversationListDiffCallback : DiffUtil.ItemCallback<ConversationInfoViewState>() {

    override fun areItemsTheSame(
        oldItem: ConversationInfoViewState,
        newItem: ConversationInfoViewState
    ): Boolean {
        return true
    }

    override fun areContentsTheSame(
        oldItem: ConversationInfoViewState,
        newItem: ConversationInfoViewState
    ): Boolean {
        return true
    }

}