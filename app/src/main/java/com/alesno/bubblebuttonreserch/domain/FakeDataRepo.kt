package com.alesno.bubblebuttonreserch.domain

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

    fun getParticipant(id: String) =
        mConversationsState.value[id]?.participant

    fun getParticipantsCount() = mConversationsState.value.size

    private fun createFakeData(): Map<String, ConversationViewState> {
        val conversations = mutableMapOf<String, ConversationViewState>()
        conversations["0"] = ConversationViewState(
            participant = Participant(
                name = "Недовольный кот",
                avatarUrl = "https://cdn25.img.ria.ru/images/156087/28/1560872802_0:778:1536:1642_600x0_80_0_0_606c2d47b6d37951adc9eaf750de22f0.jpg"
            ),
            messages = mutableListOf(
                MessageViewState("Привет!", "18.06.21", MessageViewState.Type.INCOMING),
                MessageViewState("Привет))", "18.06.21", MessageViewState.Type.OUTGOING),
                MessageViewState("Как дела?", "18.06.21", MessageViewState.Type.OUTGOING),
                MessageViewState("Хорошо, как у тебя?", "18.06.21", MessageViewState.Type.INCOMING),
            )
        )
        conversations["1"] = ConversationViewState(
            participant = Participant(
                name = "Просто патрик",
                avatarUrl = "data:image/webp;base64,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"
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