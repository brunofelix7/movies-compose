package dev.brunofelix.movies.core.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainNavViewModel @Inject constructor(): ViewModel() {

    val topLevelTabs = listOf(
        MainNavKey.Movies,
        MainNavKey.TvShows,
        MainNavKey.Search,
        MainNavKey.Favorites
    )

    private val startTab = MainNavKey.Movies

    private val _currentTab = MutableStateFlow<MainNavKey>(startTab)
    val currentTab: StateFlow<MainNavKey> = _currentTab

    private val _tabStacks = MutableStateFlow(
        topLevelTabs.associateWith { tab -> listOf(tab) }
    )

    val backStack: StateFlow<List<MainNavKey>> = combine(_currentTab, _tabStacks) { tab, stacks ->
        stacks[tab] ?: listOf(tab)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf(startTab)
    )

    fun navigateTo(route: MainNavKey) {
        if (route in topLevelTabs) {
            _currentTab.value = route
        } else {
            _tabStacks.update { stacks ->
                val tab = _currentTab.value
                val newStack = stacks[tab].orEmpty() + route
                stacks.toMutableMap().apply { put(tab, newStack) }
            }
        }
    }

    fun popBackStack() {
        val tab = _currentTab.value
        val currentStack = _tabStacks.value[tab].orEmpty()
        if (currentStack.size > 1) {
            _tabStacks.update { stacks ->
                val newStack = currentStack.dropLast(1)
                stacks.toMutableMap().apply { put(tab, newStack) }
            }
        }
    }
}