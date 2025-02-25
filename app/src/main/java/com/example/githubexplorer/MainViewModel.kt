package com.example.githubexplorer

import androidx.lifecycle.ViewModel
import com.example.githubexplorer.data.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

}
