package com.mad.assignme.data

data class Task (
    val id: Int = -1,
    var name: String,
    var description: String,
    val startDate: Long,
    val startTime: String,
    val endDate: Long,
    val endTime: String,
    val courseId: Int = -1,
    val isCompleted: Boolean = false,
)