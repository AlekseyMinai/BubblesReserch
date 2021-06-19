package com.alesno.bubblebuttonreserch.ui.conversation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.bubblebuttonreserch.databinding.ItemIncomingConversationBinding
import com.alesno.bubblebuttonreserch.databinding.ItemOutgoingConversationBinding
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.MessageViewState

class ConversationAdapter :
    ListAdapter<MessageViewState, ConversationAdapter.MessageViewHolder>(ConversationDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        when (viewType) {
            INCOMING_VIEW_TYPE -> IncomingViewHolder(
                ItemIncomingConversationBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> OutgoingViewHolder(
                ItemOutgoingConversationBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position).type) {
            MessageViewState.Type.INCOMING -> INCOMING_VIEW_TYPE
            MessageViewState.Type.OUTGOING -> OUTGOING_VIEW_TYPE
        }

    class IncomingViewHolder(
        private val binding: ItemIncomingConversationBinding
    ) : MessageViewHolder(binding.root) {

        override fun bind(message: MessageViewState) = with(binding) {
            incomingText.text = message.text
        }

    }

    class OutgoingViewHolder(
        private val binding: ItemOutgoingConversationBinding
    ) : MessageViewHolder(binding.root) {

        override fun bind(message: MessageViewState) = with(binding) {
            outgoingText.text = message.text
        }

    }

    abstract class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(message: MessageViewState)
    }

    companion object {
        private const val INCOMING_VIEW_TYPE = 0
        private const val OUTGOING_VIEW_TYPE = 1
    }

}

object ConversationDiffCallback : DiffUtil.ItemCallback<MessageViewState>() {

    override fun areItemsTheSame(
        oldItem: MessageViewState,
        newItem: MessageViewState
    ): Boolean = true

    override fun areContentsTheSame(
        oldItem: MessageViewState,
        newItem: MessageViewState
    ): Boolean = true

}