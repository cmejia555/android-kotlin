package com.cmejia.kotlinapp.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmejia.kotlinapp.entities.Car


class DetailsViewModels : ViewModel() {
    var itemSelected : MutableLiveData<Car> = MutableLiveData()
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