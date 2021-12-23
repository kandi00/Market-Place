package com.example.marketplace.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.example.marketplace.repository.Repository

class ListViewModelFactory(private val context: Context, private val repository: Repository) : Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(context, repository) as T
    }
}