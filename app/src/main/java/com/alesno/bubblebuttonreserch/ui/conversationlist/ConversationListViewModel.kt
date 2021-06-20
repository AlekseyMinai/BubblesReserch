package com.alesno.bubblebuttonreserch.ui.conversationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesno.bubblebuttonreserch.domain.FakeDataRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ConversationListViewModel : ViewModel() {

    val adapter = ConversationListAdapter(::openConversation)
    val openConversation: SharedFlow<String>
        get() = mOpenConversation.asSharedFlow()

    private val mOpenConversation = MutableSharedFlow<String>()

    fun init() {
        FakeDataRepo.getConversationList()
            .onEach { adapter.submitList(it) }
            .launchIn(viewModelScope)
    }

    private fun openConversation(conversationId: String) {
        viewModelScope.launch {
            mOpenConversation.emit(conversationId)
        }
    }

}