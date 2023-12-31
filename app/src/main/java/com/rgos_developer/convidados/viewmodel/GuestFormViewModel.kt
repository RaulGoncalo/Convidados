package com.rgos_developer.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rgos_developer.convidados.model.GuestModel
import com.rgos_developer.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private var _saveGuest = MutableLiveData<String>()
    val saveGuest: LiveData<String> = _saveGuest

    fun save(guest: GuestModel) {
        if (guest.id == 0) {
            if(repository.insert(guest)){
                _saveGuest.value = "Convidado criado com sucesso!"
            }else{
                _saveGuest.value = "Falha"
            }

        } else {
            if(repository.update(guest)){
                _saveGuest.value = "Convidado atualizado com sucesso!"
            }else{
                _saveGuest.value = "Falha"
            }
        }
    }

    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }
}