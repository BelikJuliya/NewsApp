package com.ybelik.news.screen.subscriiption

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ybelik.domain.model.Article
import com.ybelik.news.DateFormatter
import com.ybelik.news.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscriptionsViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit
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
                    onNavigateToSettings()
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            item {
                InputTopic(
                    onTextChanged = { topic ->
                        viewModel.handleIntent(SubscriptionsIntent.InputTopic(topic))
                    },
                    text = state.value.query
                )
            }

            item {
                ActionButton(
                    onButtonClick = {
                        viewModel.handleIntent(SubscriptionsIntent.ClickSubscribe)
                    },
                    text = stringResource(R.string.add_subscription),
                    isEnabled = state.value.subscribeBtnEnabled
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.subscriptions, state.value.subscriptions.size),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
            }
            item {
                FilterChips(
                    subscriptions = state.value.subscriptions,
                    onChipClick = { topic ->
                        viewModel.handleIntent(SubscriptionsIntent.ToggleTopicSelection(topic = topic))
                    },
                    onDeleteChipClick = { topic ->
                        viewModel.handleIntent(SubscriptionsIntent.RemoveSubscription(topic = topic))
                    }
                )
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.articles, state.value.articles.size),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp
                )
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
            state.value.articles.forEach { article ->
                item {
                    Article(
                        onReadClick = {
                            // TODO navigate to webview
                        },
                        onShareClick = {
                            // TODO share
                        },
                        article = article
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    modifier: Modifier = Modifier,
    onRefreshClick: () -> Unit,
    onClearClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.subscriptions_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(16.dp)
                    .clickable {
                        onRefreshClick()
                    },
                imageVector = Icons.Default.Refresh,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.refresh_icon)
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(16.dp)
                    .clickable {
                        onClearClick()
                    },
                imageVector = Icons.Default.Clear,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.clear_icon)
            )
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onSettingsClick()
                    },
                imageVector = Icons.Default.Settings,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.settings_icon)
            )
        }
    )
}

@Composable
@Preview
fun AppBarPreview() {
    NewsAppBar(
        onSettingsClick = {},
        onRefreshClick = {},
        onClearClick = {}
    )
}

@Composable
@Preview
fun InputTopicPreview() {
    InputTopic(
        onTextChanged = {

        },
        text = "Kotlin"
    )
}

@Composable
fun InputTopic(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    text: String
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = text,
        onValueChange = onTextChanged,
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.what_interests_you),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
@Preview
fun ActionButtonPreview() {
    ActionButton(
        onButtonClick = {},
        text = "Add subscription",
        isEnabled = true
    )
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    text: String,
    isEnabled: Boolean
) {
    Button(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onClick = {
            onButtonClick()
        },
        enabled = isEnabled
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_subscription_btn),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun ChipsPreview() {
    FilterChips(
        subscriptions = mapOf("Kotlin" to true, "Android" to false),
        onChipClick = { _ ->

        },
        onDeleteChipClick = {

        }
    )
}

@Composable
fun FilterChips(
    modifier: Modifier = Modifier,
    subscriptions: Map<String, Boolean>,
    onChipClick: (String) -> Unit,
    onDeleteChipClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        subscriptions.entries.forEach { entry ->
            FilterChip(
                selected = entry.value,
                onClick = {
                    onChipClick(entry.key)
                },
                label = {
                    Text(text = entry.key)
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(FilterChipDefaults.IconSize)
                            .clickable {
                                onDeleteChipClick(entry.key)
                            },
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_chip)
                    )
                }
            )
        }
    }
}

@Composable
@Preview
fun ArticlePreview() {
    Article(
        onReadClick = {},
        onShareClick = {},
        article = Article(
            title = "Title example",
            description = "Мальдивы – тропическое государство в Индийском океане, расположенное на 26 кольцевидных атоллах, которые состоят из более чем тысячи коралловых островов. Оно славится своими пляжами, голубыми лагунами и огромными рифами. В столице страны Мале стоит посетить оживленный рыбный рынок, рестораны и магазины на главной дороге Меджеде-Магу, а также мечеть Хукуру-Миский (Пятничная мечеть), фундамент и стены которой украшены резьбой по белому кораллу",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8qkJ26vqazrlIUy2jzVmoV9H5DWbrEx2n8A&s",
            source = "Biblio globus",
            publishedAt = System.currentTimeMillis(),
            url = "https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://ru.wikipedia.org/wiki/%25D0%259C%25D0%25B0%25D0%25BB%25D1%258C%25D0%25B4%25D0%25B8%25D0%25B2%25D1%258B&ved=2ahUKEwi31baF4pmSAxUjUkEAHSl2EGsQFnoECEwQAQ&usg=AOvVaw0kK16kfXW3-lfSawzabRK2"
        )
    )
}

@Composable
fun Article(
    modifier: Modifier = Modifier,
    onReadClick: () -> Unit,
    onShareClick: () -> Unit,
    article: Article
) {
    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            model = article.imageUrl,
            contentDescription = stringResource(R.string.article_image),
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = article.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = article.description,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = article.source,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = DateFormatter.formatDateToString(article.publishedAt),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
                    .weight(1f),
                onClick = {
                    onReadClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = stringResource(R.string.read_the_article),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Read"
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .weight(1f),
                onClick = {
                    onReadClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share_the_article),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(R.string.share)
                )
            }
        }
    }
}
