package com.example.testioasys2.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.testioasys2.R
import com.example.testioasys2.utils.Constants
import com.google.android.material.composethemeadapter.MdcTheme

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Scaffold(
                    topBar = {
                        MyTopAppBar(
                            enterpriseName = intent.getStringExtra(EXTRA_NAME).orEmpty(),
                            onClickNavigationIcon = { finish() }
                        )
                    },
                    backgroundColor = Color.White
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        AddScrollView(
                            enterprisePhoto = intent.getStringExtra(EXTRA_PHOTO).orEmpty(),
                            enterpriseDescription = intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty(),
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_PHOTO = "EXTRA_PHOTO"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getStartIntent(context: Context, name: String, photo: String, description: String): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_PHOTO, photo)
                putExtra(EXTRA_DESCRIPTION, description)
            }
        }
    }
}

@Composable
fun AddScrollView(enterprisePhoto: String = "", enterpriseDescription: String = "") {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(1F)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AsyncImage(
                model = Constants.PHOTO_BASE_URL
                    .plus(enterprisePhoto),
                contentDescription = stringResource(id = R.string.enterprise_photo_content_description),
                modifier = Modifier
                    .width(318.dp)
                    .height(155.dp)
            )
            Text(
                text = enterpriseDescription,
                fontFamily = FontFamily(
                    Font(R.font.source_sans_pro_regular),
                ),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                color = colorResource(id = R.color.grey),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun MyTopAppBar(enterpriseName: String = "", onClickNavigationIcon: () -> Unit = {}) {
    TopAppBar(title = { Text(text = enterpriseName) },
        contentColor = colorResource(id = android.R.color.white),
        backgroundColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.medium_pink),
                    colorResource(id = R.color.black_pink)
                )
            )
        ),
        navigationIcon = {
            IconButton(onClick = onClickNavigationIcon) {
                Icon(
                    Icons.Default.ArrowBack,
                    stringResource(id = R.string.action_back_content_description)
                )
            }
        })
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MdcTheme {
        Scaffold(
            topBar = {
                MyTopAppBar("Enterprise Name")
            },
            backgroundColor = Color.White,
        ) {
            Box(modifier = Modifier.padding(it)) {
                AddScrollView(enterpriseDescription = "Description")
            }
        }
    }
}