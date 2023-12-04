package com.example.cookbook.presentation.Category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cookbook.domain.model.Category


@Composable
fun categoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    var scaffoldState = rememberScaffoldState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val data = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.loadCategory()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Hello,Food lovers!",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                IconButton(onClick = {

                }) {


                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "sort"
                    )

                }

            }

        }
    ) { it->


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            item {
                categoryHeader(textState)
            }
            item {
                categoryGrid(data.categoryList, navController)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if(data.isLoading) {
                CircularProgressIndicator()
            } else if(data.error != null) {
                Text(
                    text = data.error!!,
                    color = MaterialTheme.colors.error
                )
            }
        }


    }




}

@Composable
fun categoryList(
    data: CategoryInfoState,
    navController: NavController
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        )
        {


            items(data.categoryList.size) { item ->

                categoryListItems(
                    navController = navController,
                    category = data.categoryList.get(item)
                )
            }

        }
    }
}

@Composable
fun categoryHeader(textState: MutableState<TextFieldValue>) {


    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        // text = "Make your own food,\r\nstay at Home!" ,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("Make your own food,\r\nstay at  ")
            }
            withStyle(style = SpanStyle(color = Color(0xFFfcba03))) {
                append("Home")
            }
        },
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )

    SearchView(state = textState)

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = "Categories",
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun categoryGrid(
    categoryList: List<Category>,
    navController: NavController
) {
    val columns = 2
    val rows = categoryList.size / columns + if (categoryList.size % columns == 0) 0 else 1

    for (rowIndex in 0 until rows) {
        Row(Modifier.fillMaxWidth()) {
            for (columnIndex in 0 until columns) {
                val index = rowIndex * columns + columnIndex
                if (index < categoryList.size) {
                    val category = categoryList[index]
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .clickable {

                            }
                    ) {
                        categoryListItems(
                            navController = navController,
                            category = category
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
