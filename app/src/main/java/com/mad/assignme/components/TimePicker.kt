package com.mad.assignme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickTime(
    label: String,
    timePickerState: TimePickerState,
) {
    // set time to current time
    var showTimePicker by remember { mutableStateOf(false) }
    val selectedTime = timePickerState.let {
        val hour = if (it.hour < 10) "0${it.hour}" else it.hour
        val minute = if (it.minute < 10) "0${it.minute}" else it.minute
        "$hour:$minute"
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedTime,
            onValueChange = { },
            label = { Text(label) },
            shape = RoundedCornerShape(16.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showTimePicker = !showTimePicker }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = "Pick time"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showTimePicker) {
            Popup(
                onDismissRequest = { showTimePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    TimePickerDialog(
                        onDismiss = { showTimePicker = false },
                        onConfirm = { showTimePicker = false },
                    ) {
                        TimePicker(state = timePickerState)
                    }
                }
            }
        }
    }
}