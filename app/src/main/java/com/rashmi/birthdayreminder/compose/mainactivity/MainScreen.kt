package com.rashmi.birthdayreminder.compose.mainactivity

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rashmi.birthdayreminder.R
import com.rashmi.birthdayreminder.ui.theme.BirthdayReminderTheme

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = dimensionResource(R.dimen.ds_56dp))
            .padding(start = dimensionResource(R.dimen.ds_30dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarPreview() {
    BirthdayReminderTheme {
        TopAppBar()
    }
}