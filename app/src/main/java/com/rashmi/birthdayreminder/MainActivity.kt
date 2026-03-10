package com.rashmi.birthdayreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rashmi.birthdayreminder.compose.mainactivity.TopAppBar
import com.rashmi.birthdayreminder.models.BirthdayData
import com.rashmi.birthdayreminder.ui.theme.BirthdayReminderTheme
import com.rashmi.birthdayreminder.ui.theme.Black
import com.rashmi.birthdayreminder.ui.theme.Typography
import com.rashmi.birthdayreminder.ui.util.DateVisualTransformation
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayReminderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar() }
                ) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    vm: MainViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }

    Column {
        AddBirthday(
            modifier = modifier,
            bdayData = uiState,
            onNameChange = vm::onNameChange,
            onDobChange = vm::onDateTextChange,
            addBirthday = vm::addBirthday,
            showDatePicker = { showDatePicker = it }
        )
        BirthdayList()

        if (showDatePicker) {
            BirthdatePickerDialog(
                dismissDatePicker = { showDatePicker = false },
                onDatePicked = vm::onDatePicked
            )
        }
    }
}

@Composable
fun BirthdatePickerDialog(
    dismissDatePicker: () -> Unit,
    onDatePicked: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )
    DatePickerDialog(
        onDismissRequest = dismissDatePicker,
        confirmButton = {
            TextButton(
                onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDatePicked.invoke(date)
                    }
                    dismissDatePicker.invoke()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismissDatePicker
            ) {
                Text("Cancel")
            }
        }

    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
fun AddBirthday(
    modifier: Modifier = Modifier,
    bdayData: BirthdayData,
    addBirthday: () -> Unit,
    onNameChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    showDatePicker: (Boolean) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.ds_30dp))
    ) {
        Text(
            text = stringResource(R.string.add_bday),
            style = Typography.titleLarge
        )
        OutlinedTextField(
            value = bdayData.name,
            onValueChange = { onNameChange.invoke(it) },
            label = {
                Text(text = stringResource(R.string.name))
            },
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = bdayData.dateDigits,
            onValueChange = {
                onDobChange.invoke(it)
            },
            visualTransformation = DateVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(text = stringResource(R.string.dob))
            },
            trailingIcon = {
                IconButton(
                    onClick = { showDatePicker.invoke(true) },
                    enabled = true
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Pick Birthday Date"
                    )
                }
            }
        )

        FilledIconButton(
            onClick = addBirthday,
            enabled = bdayData.name.isNotBlank() && bdayData.dateDigits.length == 8,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(text = stringResource(R.string.add_bday))
        }
    }
}

@Composable
fun BirthdayList(modifier: Modifier = Modifier) {
    Column {
        HorizontalDivider(
            thickness = 2.dp,
            color = Black,
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
        )
        Text(
            text = stringResource(R.string.upcoming_bday),
            style = Typography.titleLarge
        )

        LazyColumn {
            item { BirthdayItem() }
        }
    }
}

@Composable
fun BirthdayItem(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Rashmi",
                style = Typography.titleMedium
            )
            Text(
                text = "20th April",
                style = Typography.bodyMedium
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BirthdayItemPreview() {
    BirthdayReminderTheme {
        BirthdayItem()
    }
}

@Preview(showBackground = true)
@Composable
private fun BdayListPreview() {
    BirthdayReminderTheme {
        BirthdayList()
    }
}

@Preview(showBackground = true)
@Composable
fun AddBirthdayPreview() {
    BirthdayReminderTheme {
        AddBirthday(
            bdayData = BirthdayData("", date = LocalDate.now()),
            onNameChange = {},
            onDobChange = {},
            addBirthday = {},
            showDatePicker = {}
        )
    }
}