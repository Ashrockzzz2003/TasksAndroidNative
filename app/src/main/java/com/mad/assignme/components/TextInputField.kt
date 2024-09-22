package com.mad.assignme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TextInputField(
    label: String,
    value: String,
    leadingIcon: ImageVector,
    leadingIconDescription: String,
    onValueChange: (String) -> Unit,
    customHeight: Int = 64,
    showIcon: Boolean = true
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (showIcon)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = leadingIconDescription
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(customHeight.dp)
            )
        else
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(customHeight.dp)
            )

    }
}