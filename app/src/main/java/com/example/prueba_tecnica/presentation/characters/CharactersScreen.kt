package com.example.prueba_tecnica.presentation.characters

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.data.model.Character
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = hiltViewModel(),
    navController: NavController
){
    val stateCharacter by viewModel.charactersState.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val refreshing by viewModel.isRefreshing.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state = rememberPullToRefreshState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    var stateFilterEnabled by remember { mutableStateOf(false) }
    var deathFilterEnabled by remember { mutableStateOf(false) }
    val characters = remember(stateCharacter) {
        when (stateCharacter) {
            is CharactersState.Success -> (stateCharacter as CharactersState.Success).data.results
            else -> emptyList()
        }
    }
    val filteredList = remember(searchQuery, characters, stateFilterEnabled, deathFilterEnabled) {
        if (searchQuery.isBlank()) emptyList()
        else characters
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .take(10)
            .filter { if (stateFilterEnabled)(it.status == "Alive") else true }
            .filter { if (deathFilterEnabled)(it.status == "Dead") else true }
    }
    var isBottomVisible by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                            expanded = true
                        },
                        onSearch = {
                            expanded = false
                        },
                        active = expanded,
                        onActiveChange = {
                            expanded = it
                        },
                        placeholder = { Text("Buscar por nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        colors = SearchBarDefaults.colors(
                            Color.Transparent
                        ),
                        leadingIcon = {
                            Row(
                            ) {
                                if (expanded == true){
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back",
                                        modifier = Modifier.clickable{
                                            expanded = false
                                        }
                                            .padding(end = 5.dp)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Buscar"
                                    )
                                }else{
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Buscar"
                                    )
                                }
                            }
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Cancelar",
                                    modifier = Modifier
                                        .clickable {
                                            searchQuery = ""
                                        }
                                )
                            }else{
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Filter",
                                    modifier = Modifier
                                        .clickable {
                                            isBottomVisible = true
                                        }
                                        .size(35.dp),
                                )
                            }
                        }
                    ){
                        filteredList.forEach { suggestion ->
                            ListItem(
                                headlineContent = { Text(suggestion.name) },
                                modifier = Modifier
                                    .clickable {
                                        searchQuery = suggestion.name
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                            )
                        }
                    }
                }, scrollBehavior = scrollBehavior,
            )
        }
    ) {innerPadding ->
        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = {viewModel.refreshAll()},
            modifier = Modifier.padding(innerPadding),
            state = state,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = refreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = state
                )
            }
        ){
            CharacterBodyScreen(viewModel, scrollBehavior, searchQuery, stateFilterEnabled, deathFilterEnabled, navController)
        }
    }
    if (isBottomVisible){
        BottomSheetFilterAquaji(stateFilterEnabled,onToggle = {newValue -> stateFilterEnabled = newValue}, onDismissRequest = {isBottomVisible = false}, deathFilterEnabled, onDead = {newValue -> deathFilterEnabled = newValue})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterBodyScreen(viewModel: CharactersViewModel = hiltViewModel(), scrollBehavior: TopAppBarScrollBehavior, searchQuery: String, stateFilterEnabled: Boolean, deathFilterEnabled: Boolean, navController: NavController){
    val state by viewModel.charactersState.collectAsState()
    val listState = rememberLazyListState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 5
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) viewModel.loadNextPage()
    }
    LaunchedEffect(searchQuery, stateFilterEnabled, deathFilterEnabled) {
        listState.scrollToItem(0)
    }

    when (val s = state) {
        is CharactersState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CharactersState.Success -> {
            val characters = s.data.results
            val filteredList = remember(searchQuery, characters, stateFilterEnabled, deathFilterEnabled) {
                val base = if (searchQuery.isBlank()) characters
                else characters
                    .filter { it.name.contains(searchQuery, ignoreCase = true) }
                base
                    .filter { if (stateFilterEnabled) (it.status == "Alive") else true }
                    .filter { if (deathFilterEnabled)(it.status == "Dead") else true }
                    .distinctBy { it.id }
            }
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items = filteredList, key = { it.id }) { character ->
                    ItemCard(character, navController)
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
        is CharactersState.Error -> {
            Column(
                Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(s.message, color = Color.Red)
                Spacer(Modifier.height(8.dp))
                Button(onClick = { viewModel.loadCharacters(1) }) { Text("Reintentar") }
            }
        }
    }
}

@Composable
fun ItemCard(character: Character, navController: NavController){
    var isInformationVisible by remember { mutableStateOf(false) }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = {
            navController.navigate(
                "detailCharacter/${character.id}"
            )
        },
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(start = 5.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = "Character image",
                        modifier = Modifier
                            .size(100.dp)
                            .weight(1f),
                        contentScale = ContentScale.Inside
                    )
                    Column(
                        modifier = Modifier.weight(2f).fillMaxWidth().padding(start = 15.dp),
                    ) {
                        Text(
                            text = "${character.name}",
                            fontWeight = FontWeight(600),
                            fontSize = 16.sp,
                            modifier = Modifier
                        )
                        Text(
                            text = "${character.species}",
                            fontWeight = FontWeight(600),
                            fontSize = 14.sp,
                            modifier = Modifier
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Default.Circle,
                                tint = if (character.status == "Alive") colorResource(R.color.green_connected) else colorResource(R.color.red_disconnected),
                                contentDescription = ""
                            )
                            Text(
                                text = if (character.status == "Alive") "Alive" else "Dead",
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                modifier = Modifier.padding(start = 2.dp)
                            )
                        }
                    }
                }
            }
            IconButton(onClick = {
                isInformationVisible = true
            }){
                Icon(
                    modifier = Modifier.size(25.dp).weight(2f),
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilterAquaji(checked: Boolean, onToggle: (Boolean) -> Unit, onDismissRequest: () -> Unit, dead: Boolean, onDead: (Boolean) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest()},
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        if (isLoading){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.width(50.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }else{
            Column(
            )   {
                Text(
                    text = "Filtros",
                    fontSize = 30.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vivo",
                        fontSize = 22.sp,
                        fontWeight = FontWeight(500),
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(start = 20.dp),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = checked   ,
                        onCheckedChange = {isChecked ->
                            isLoading = true
                            coroutineScope.launch {
                                delay(800)
                                onToggle(isChecked)
                                isLoading = false
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Muerto",
                        fontSize = 22.sp,
                        fontWeight = FontWeight(500),
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(start = 20.dp),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = dead   ,
                        onCheckedChange = {isChecked ->
                            isLoading = true
                            coroutineScope.launch {
                                delay(800)
                                onDead(isChecked)
                                isLoading = false
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
