package com.example.githubexplorer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubexplorer.data.DetailRepository
import com.example.githubexplorer.data.GitHubRepository
import com.example.githubexplorer.data.RepositoryInfo
import com.example.githubexplorer.data.StarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: GitHubRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val _info = MutableStateFlow<RepositoryInfo?>(null)
    val info: StateFlow<RepositoryInfo?> = _info.asStateFlow()

    private val _star = MutableStateFlow<StarData?>(null)
    val star: StateFlow<StarData?> = _star.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private fun detailRequest(owner: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getRepositoryDetails(owner, name)
                .catch { e ->
                    _error.value = e.message
                }
                .collect { repositories ->
                    _info.value = repositories
                }
            _isLoading.value = false
        }
    }

    fun starRequest() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.starRepository(info.value?.id ?: "")
                .catch { e ->
                    _error.value = e.message
                }
                .collect { data ->
                    _star.value = data
                }
            _isLoading.value = false
        }
    }

    fun makeRequest() {
        with(detailRepository) {
            detailRequest(getOwner(), getName())
        }
    }
}

