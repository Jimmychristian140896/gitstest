package com.jimmy.tasky.core.util

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator

fun NavController.navigateUpWithResult(results: List<Pair<String, String>>) {
    val savedStateHandle = previousBackStackEntry
        ?.savedStateHandle
    results.forEach {
        savedStateHandle?.set(it.first, it.second)
    }
    popBackStack()
}

fun NavController.navigateUpWithResult(result: Pair<String, String>) {
    navigateUpWithResult(listOf(result))
}

fun NavController.getNavResult(key: String):  String? {
    val result = currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>(key)
    return result
}