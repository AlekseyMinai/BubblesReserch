package com.alesno.bubblebuttonreserch.ui.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.bubblebuttonreserch.FakeDataRepo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConversationViewModel : ViewModel() {

    val adapter = ConversationAdapter()

    fun init() {
        FakeDataRepo.getConversation("1")
            .onEach { adapter.submitList(it?.messages) }
            .launchIn(viewModelScope)
    }

}