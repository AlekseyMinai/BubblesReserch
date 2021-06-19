package com.alesno.bubblebuttonreserch

import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.ConversationViewState
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.MessageViewState
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.Participant
import com.alesno.bubblebuttonreserch.ui.conversationlist.viewstate.ConversationInfoViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

object FakeDataRepo {

    private val mConversationsState = MutableStateFlow(createFakeData())

    fun getConversation(id: String) =
        mConversationsState.map { it[id] }

    fun getConversationList() =
        mConversationsState.map {
            it.map { pair ->
                ConversationInfoViewState(
                    conversationId = pair.key,
                    participant = pair.value.participant
                )
            }
        }

    private fun createFakeData(): Map<String, ConversationViewState> {
        val conversations = mutableMapOf<String, ConversationViewState>()
        conversations["1"] = ConversationViewState(
            participant = Participant(
                name = "James Hetfield",
                avatarUrl = "data:image/jpeg;base64,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"
            ),
            messages = mutableListOf(
                MessageViewState("Привет!", "18.06.21", MessageViewState.Type.INCOMING),
                MessageViewState("Привет))", "18.06.21", MessageViewState.Type.OUTGOING),
                MessageViewState("Как дела?", "18.06.21", MessageViewState.Type.OUTGOING),
                MessageViewState("Хорошо, как у тебя?", "18.06.21", MessageViewState.Type.INCOMING),
            )
        )
        conversations["2"] = ConversationViewState(
            participant = Participant(
                name = "Paul Bruce Dickinson",
                avatarUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fok.ru%2Frocksta%2Ftopic%2F66997626656035&psig=AOvVaw3AHltKMCiGjVXXTSBcduKU&ust=1624183425604000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCODzjPW4o_ECFQAAAAAdAAAAABAD"
            ),
            messages = mutableListOf(
                MessageViewState("Привет, Брюс!", "19.06.21", MessageViewState.Type.OUTGOING),
                MessageViewState("Когда новый альбом?", "19.06.21", MessageViewState.Type.OUTGOING),
                MessageViewState("Уже скоро", "19.06.21", MessageViewState.Type.INCOMING),
            )
        )
        return conversations
    }

}