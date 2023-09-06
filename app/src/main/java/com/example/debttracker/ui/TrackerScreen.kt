package com.example.debttracker.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.debttracker.R
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(
    trackerViewModel: TrackerViewModel = viewModel(),
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()
) {
    val trackerUiState by trackerViewModel.uiState.collectAsState()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer

            )
        )
    }
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.people),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PersonItem(
                        name = trackerUiState.name1,
                        debt = trackerUiState.currentDebt1,
                        onNameChanged = { trackerViewModel.updateName(1, it) },
                        onDebtAdded = { trackerViewModel.addAmount(1, it) },
                        onDebtSubtracted = { trackerViewModel.subtractAmount(1, it) },
                        modifier = Modifier.padding(4.dp)
                    )
                    PersonItem(
                        name = trackerUiState.name2,
                        debt = trackerUiState.currentDebt2,
                        onNameChanged = { trackerViewModel.updateName(2, it) },
                        onDebtAdded = { trackerViewModel.addAmount(2, it)},
                        onDebtSubtracted = { trackerViewModel.subtractAmount(2, it)},
                        modifier = Modifier.padding(4.dp)
                    )
                    PersonItem(
                        name = trackerUiState.name3,
                        debt = trackerUiState.currentDebt3,
                        onNameChanged = { trackerViewModel.updateName(3, it) },
                        onDebtAdded = { trackerViewModel.addAmount(3, it)},
                        onDebtSubtracted = { trackerViewModel.subtractAmount(3, it)},
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PersonItem(
    name: String,
    debt: Double,
    onNameChanged: (String) -> Unit,
    onDebtAdded: (Double) -> Unit,
    onDebtSubtracted: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        var amountInput by remember { mutableStateOf("")}
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
        ) {
            EditNameField(
                value = name,
                onValueChange = onNameChanged
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = NumberFormat.getCurrencyInstance().format(debt),
                color = when {
                    debt >= 0 -> Color(0xFF007a56)
                    else -> Color(0xFFd0201f)
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(160.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                EditDebtField(
                    value = amountInput,
                    onValueChange = {
                        amountInput = it
                    },
                )
                Spacer(modifier = Modifier.padding(4.dp))
                PersonItemButton(
                    add = true,
                    onClick = {
                        onDebtAdded(amountInput.toDouble())
                        amountInput = ""
                    }
                )
                Spacer(modifier = Modifier.padding(4.dp))
                PersonItemButton(
                    add = false,
                    onClick = {
                        if (amountInput != "") {
                            onDebtSubtracted(amountInput.toDouble())
                            amountInput = ""
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PersonItemButton(
    add: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        content = {
            if (add) {
                Text(
                    text = "+"
                )
            } else {
                Text(
                    text = "-"
                )
            }
        },
        modifier = Modifier.width(56.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDebtField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "$0.00",
                fontSize = 12.sp
            ) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .width(80.dp)
            .height(60.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Name",
                fontSize = 12.sp,
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .width(160.dp)
            .height(60.dp)
    )
}