package com.contextwallet.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Singleton
class DebugLogger @Inject constructor() {
    private val _logs = MutableStateFlow<List<String>>(emptyList())
    val logs = _logs.asStateFlow()
    
    private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.US)

    fun log(tag: String, message: String) {
        val timestamp = dateFormat.format(Date())
        val logEntry = "$timestamp [$tag] $message"
        val currentLogs = _logs.value.toMutableList()
        currentLogs.add(0, logEntry) // Newest first
        if (currentLogs.size > 1000) {
            currentLogs.removeAt(currentLogs.lastIndex)
        }
        _logs.value = currentLogs
    }
    
    fun clear() {
        _logs.value = emptyList()
    }
}
