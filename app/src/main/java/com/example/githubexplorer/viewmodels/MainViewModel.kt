package com.example.githubexplorer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubexplorer.data.DetailRepository
import com.example.githubexplorer.data.GitHubRepository
import com.example.githubexplorer.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GitHubRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val _repositories = MutableStateFlow<List<Repository>>(emptyList())
    val repositories: StateFlow<List<Repository>> = _repositories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getRepositories()
    }

    fun getRepositories(cursor: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getRepositories(cursor)
                .catch { e ->
                    _error.value = e.message
                }
                .collect { repositories ->
                    _repositories.value = repositories
                }
            _isLoading.value = false
        }
    }

    fun saveData(owner: String, name: String) {
        detailRepository.saveOwnerAndName(owner, name)
    }
}
