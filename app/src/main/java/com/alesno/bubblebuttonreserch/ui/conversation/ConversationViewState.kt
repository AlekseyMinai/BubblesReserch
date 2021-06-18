package com.alesno.bubblebuttonreserch.ui.conversation

data class ConversationViewState(
    val messages: List<MessageViewState>,
    val participant: Participant
)

data class MessageViewState(val text: String, val date: String, val type: Type) {
    enum class Type {
        INCOMING, OUTGOING
    }
}

data class Participant(val name: String, val avatarUrl: String)