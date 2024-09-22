package com.mad.assignme.data

data class Course(
    val id: Int = -1,
    val name: String,
    val code: String,
    val description: String,
    val semester: Int,
    val year: String,
)