package com.ybelik.news.screen.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    LazyRow {
        state.settingsList.forEach { settingsItem ->
            when (settingsItem) {
                is SettingsUIModel.MenuSettings -> {

                }

                is SettingsUIModel.SwitchSettings -> {
//                    SwitchSettingsCard(
//                        title = settingsItem.title,
//                        description = settingsItem.description,
//                        isChecked = settingsItem.isChecked,
//                        onCheckedStateChanged = {
//                            // TODO неясно какой коллбек отправлять
////                            viewModel.handleIntent(SettingsIntent.ToggleIsWifiOnly)
//                        }
//                    )
                }
            }
        }
    }
}

@Composable
fun SwitchSettingsCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedStateChanged: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
            text = description,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Switch(
            modifier = Modifier.padding(horizontal = 16.dp),
            checked = isChecked,
            onCheckedChange = {
                onCheckedStateChanged(it)
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
@Preview
fun SettingsCardPreview() {
    SwitchSettingsCard(
        title = "Title",
        description = "Description",
        isChecked = true,
        onCheckedStateChanged = {

        }
    )
}

@Composable
@Preview
fun SelectSettingsCardPreview() {
    SelectSettingsCard(
        title = "Title",
        description = "Description",
        currentSelection = "English",
        menuList = listOf("Russian", "German", "French"),
        onExpandedChange = {},
        onSelectionChange = {},
        expanded = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectSettingsCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    currentSelection: String,
    menuList: List<String>,
    onSelectionChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
            text = description,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableSettingsMenu(
            currentSelection = currentSelection,
            menuList = menuList,
            onExpandedChange = {
                // onSelectionChange
            },
            expanded = expanded,
            onSelectionChange = {
                // onExpandedChange
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableSettingsMenu(
    modifier: Modifier = Modifier,
    currentSelection: String,
    menuList: List<String>,
    onSelectionChange: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        val textFieldState = rememberTextFieldState()
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. An editable text field has
            // the anchor type `PrimaryEditable`.
            modifier =
                Modifier
                    .width(280.dp)
                    .menuAnchor(ExposedDropdownMenuAnchorType.SecondaryEditable),
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text(text = currentSelection) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    // If the text field is editable, it is recommended to make the
                    // trailing icon a `menuAnchor` of type `SecondaryEditable`. This
                    // provides a better experience for certain accessibility services
                    // to choose a menu option without typing.
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.SecondaryEditable),
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
    }
}

@Preview
@Composable
fun MenuPreview() {
    DropdownMenu(
        currentSelection = "English",
        options = listOf("Russin", "German", "French"),
        onExpandedChange = {},
        onSelectionChange = {},
        expanded = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    currentSelection: String,
    options: List<String>,
    onSelectionChange: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState(options[0])
    var checkedIndex: Int? by remember { mutableStateOf(null) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            state = textFieldState,
            readOnly = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
//            containerColor = MenuDefaults.groupStandardContainerColor,
//            shape = MenuDefaults.shape,
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
//                    shapes = MenuDefaults.itemShape(index, optionCount),
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
//                    selected = index == checkedIndex,
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd(option)
                        checkedIndex = index
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                            // If the text field is editable, it is recommended to make the
                            // trailing icon a `menuAnchor` of type `SecondaryEditable`. This
                            // provides a better experience for certain accessibility services
                            // to choose a menu option without typing.
                            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.SecondaryEditable),
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}


