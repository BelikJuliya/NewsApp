package com.ybelik.news.screen.subscriiption

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscriptionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsAppBar(
                onRefreshClick = {
                    viewModel.handleIntent(SubscriptionsIntent.RefreshData)
                },
                onClearClick = {
                    viewModel.handleIntent(SubscriptionsIntent.ClearArticles)
                },
                onSettingsClick = {
                    // TODO navigate to settings screen
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    onRefreshClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = "My news",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onRefreshClick()
                    },
                imageVector = Icons.Default.Refresh,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Refresh icon"
            )
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onClearClick()
                    },
                imageVector = Icons.Default.Clear,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Clear icon"
            )
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onSettingsClick()
                    },
                imageVector = Icons.Default.Settings,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Clear icon"
            )
        }
    )
}

@Composable
@Preview
fun AppBarPreview() {
    NewsAppBar()
}