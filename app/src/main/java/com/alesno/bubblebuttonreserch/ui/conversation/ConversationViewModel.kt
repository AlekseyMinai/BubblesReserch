package com.alesno.bubblebuttonreserch.ui.conversation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.bubblebuttonreserch.FakeDataRepo
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.ConversationViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConversationViewModel : ViewModel() {

    val conversationState: LiveData<ConversationViewState>
        get() = mConversationState

    private val mConversationState = MutableLiveData<ConversationViewState>()

    fun init() {
        FakeDataRepo.getConversation("1")
            .onEach(mConversationState::postValue)
            .launchIn(viewModelScope)
    }

}