package com.ybelik.news.screen.settings

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ybelik.news.R
import com.ybelik.news.screen.settings.SettingsIntent.ChangeInterval
import com.ybelik.news.screen.settings.SettingsIntent.ChangeLanguage
import com.ybelik.news.screen.settings.SettingsIntent.ToggleIsWifiOnly
import com.ybelik.news.screen.settings.SettingsIntent.ToggleNotificationsEnabled

const val TAG = "SettingsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onGoBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            viewModel.handleIntent(
                ToggleNotificationsEnabled(
                    name = SwitchableSettingsName.NOTIFICATIONS,
                    isEnabled = false
                )
            )
        }
    )

    SideEffect {
        Log.d(TAG, "SettingsScreen: recomposition")
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = modifier,
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            onGoBackClick()
                        },
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.go_back_icon)
                    )
                })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = innerPadding
        ) {
            items(
                items = state.settingsList,
                key = { it.name }
            ) { settingsItem ->
                when (settingsItem) {
                    is SettingsUIModel.SwitchSettings -> {
                        SettingCard(
                            title = settingsItem.title,
                            subtitle = settingsItem.subtitle,
                            content = {
                                Switch(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    checked = settingsItem.isChecked,
                                    onCheckedChange = { isChecked ->
                                        when (settingsItem.name) {
                                            SwitchableSettingsName.WIFI -> viewModel.handleIntent(
                                                ToggleIsWifiOnly(
                                                    name = SwitchableSettingsName.WIFI,
                                                    isWifiOnly = isChecked
                                                )
                                            )

                                            SwitchableSettingsName.NOTIFICATIONS -> {
                                                if (isChecked) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                        permissionsLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                                    }
                                                } else {
                                                    viewModel.handleIntent(
                                                        ToggleNotificationsEnabled(
                                                            name = SwitchableSettingsName.NOTIFICATIONS,
                                                            isEnabled = false
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        )
                    }

                    is SettingsUIModel.MenuSettings -> {
                        SettingCard(
                            title = settingsItem.title,
                            subtitle = settingsItem.subtitle,
                            content = {
                                DropDownMenu(
                                    selectedItem = settingsItem.selectedItem,
                                    items = settingsItem.items,
                                    onItemSelected = {
                                        when (settingsItem.name) {
                                            SelectableSettingsName.LANGUAGE -> viewModel.handleIntent(
                                                ChangeLanguage(
                                                    name = SelectableSettingsName.LANGUAGE,
                                                    language = it
                                                )
                                            )

                                            SelectableSettingsName.INTERVAL -> viewModel.handleIntent(
                                                ChangeInterval(
                                                    name = SelectableSettingsName.INTERVAL,
                                                    interval = it
                                                )
                                            )
                                        }
                                    }
                                )
                            },

                            )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SelectSettingsCardPreview() {
    SettingCard(
        title = "Title",
        subtitle = "Description",
        content = {
            DropDownMenu(
                selectedItem = "English",
                items = listOf("Russian", "German", "French"),
                onItemSelected = {},
            )
        }
    )
}

@Preview
@Composable
fun MenuPreview() {
    DropDownMenu(
        selectedItem = "English",
        items = listOf("Russian", "German", "French"),
        onItemSelected = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    selectedItem: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
) {
    SideEffect {
        Log.d(TAG, "DropdownMenu: recomposition with current selection = $selectedItem")
    }

    var expanded by remember { mutableStateOf(false) }
    val textFieldState = remember(selectedItem) {
        TextFieldState(selectedItem)
    }
    ExposedDropdownMenuBox(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        }

    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            state = textFieldState,
            readOnly = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        Log.d(TAG, "onMenuItemClick: option = $option")

                        onItemSelected(option)
                        expanded = !expanded
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.SecondaryEditable),
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun SettingCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = subtitle,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        content()
        Spacer(modifier = Modifier.height(24.dp))
    }
}


