package com.aariz.sportsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aariz.sportsapp.repository.PlayerRepository

class PlayerViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(PlayerRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
