package com.cmejia.kotlinapp.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModels : ViewModel() {
    var itemSelected : MutableLiveData<Int> = MutableLiveData()
    var actionStatus : MutableLiveData<DialogState>

    enum class DialogState {
        UNPRESSED,
        DELETE_ITEM
    }

    init {
        actionStatus = MutableLiveData(DialogState.UNPRESSED)
    }

    fun setAction(state : DialogState) {
        actionStatus.value = state
    }
}