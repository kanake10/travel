package com.example.travel.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.core.Resource
import com.example.travel.models.Result
import com.example.travel.repo.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repository: PropertyRepository
) :ViewModel(){
    private val _property = mutableStateOf(PropertyState(isLoading = true))
    val property: State<PropertyState> = _property

    init {
        getProperty()
    }
    private fun getProperty() {
        viewModelScope.launch {
            when (val result = repository.getListings()) {
                is Resource.Error -> {
                    _property.value = PropertyState(isLoading = false, error = result.message)
                }
                is Resource.Success -> {
                    val filteredListing = result.data?: ArrayList()
                    _property.value = PropertyState(isLoading = false, property = ArrayList(filteredListing))
                }
                else -> {}
            }
        }
    }
}

data class PropertyState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val property: ArrayList<Result> = ArrayList()
)