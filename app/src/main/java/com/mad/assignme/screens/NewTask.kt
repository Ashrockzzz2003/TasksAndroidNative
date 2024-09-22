package com.mad.assignme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mad.assignme.components.PickDate
import com.mad.assignme.components.PickTime
import com.mad.assignme.components.TextInputField
import com.mad.assignme.data.Task
import java.util.Calendar

/*
*
* data class Task (
    val id: Int,
    val name: String,
    val description: String,
    val start: String, // milliseconds since epoch.
    val end: String, // milliseconds since epoch.
    val courseId: Int,
    val isCompleted: Boolean
)
* */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NewTask(
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        ShowDialog(
            onDismissRequest = { showDialog.value = false }
        )
    }

    val currentTime = Calendar.getInstance()

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    val startDate = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.timeInMillis
    )
    val startTime = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    val endDate = rememberDatePickerState()
    val endTime = rememberTimePickerState()



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            LargeTopAppBar(
                title = { Text(text = "New Task") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Go Back."
                    )
                },
                actions = {
                    IconButton(onClick = { showDialog.value = true }) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = "Info"
                        )
                    }
                }
            )
        }
        
        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }

        item {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TextInputField(value = taskName, onValueChange = { taskName = it }, label = "Task Name", leadingIcon = Icons.Rounded.CheckCircle, leadingIconDescription = "Task Name")
                Spacer(modifier = Modifier.padding(2.dp))
                TextInputField(value = taskDescription, onValueChange = { taskDescription = it }, label = "Something to remember about the task...", leadingIcon = Icons.AutoMirrored.Rounded.List, leadingIconDescription = "Description", customHeight = 128, showIcon = false)
                Spacer(modifier = Modifier.padding(2.dp))
                PickDate(datePickerState = startDate, label = "Start Date")
                Spacer(modifier = Modifier.padding(2.dp))
                PickTime(timePickerState = startTime, label = "Start Time")
                Spacer(modifier = Modifier.padding(2.dp))
                PickDate(datePickerState = endDate, label = "End Date")
                Spacer(modifier = Modifier.padding(2.dp))
                PickTime(timePickerState = endTime, label = "End Time")
                Spacer(modifier = Modifier.padding(16.dp))
                FilledTonalIconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        createNewTask(
                            taskName,
                            taskDescription,
                            startDate.selectedDateMillis,
                            "${startTime.hour}:${startTime.minute}",
                            endDate.selectedDateMillis,
                            "${startTime.hour}:${startTime.minute}"
                        )
                    }
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Create Task"
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = "Create Task")
                    }
                }
            }
        }
    }
}

@Composable
fun ShowDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(Icons.Rounded.Info, contentDescription = "Learn More")
        },
        title = {
            Text(text = "Learn More")
        },
        text = {
            Text(text = "Enter the task name, description, start date and end date to create a new task.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

fun createNewTask(
    taskName: String,
    taskDescription: String,
    startDate: Long?,
    startTime: String,
    endDate: Long?,
    endTime: String
) : Boolean {

    if (taskName.isEmpty() || taskDescription.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || startDate == null || endDate == null) {
        // TODO: Display validation message.
        println("[ERROR]: Validation failed.")
        return false
    }


    val task = Task(
        name = taskName,
        description = taskDescription,
        startDate = startDate,
        startTime = startTime,
        endDate = endDate,
        endTime = endTime
    )

    println(task)

    // Save task to database.

    return true
}