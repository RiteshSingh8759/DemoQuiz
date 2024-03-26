package com.example.ktquizz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.ktquizz.firebaseauth.screens.AuthScreen
import com.example.ktquizz.firestoredb.screen.FirestoreScreen
import com.example.ktquizz.screen.RealtimeScreen
import com.example.ktquizz.ui.theme.KTQuizzTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KTQuizzTheme {
                // A surface container using the 'background' color from the theme
               val isInsert = remember {mutableStateOf(false)}
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                isInsert.value = true
                            })
                            {
                                Icon(Icons.Default.Add , contentDescription ="")
                            }
                        }
                    )
                    {
                    // RealtimeScreen(isInsert)
                      //FirestoreScreen(isInsert)
                       // AuthScreen()
                        Image(
                            painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Justin_Bieber_in_2015.jpg/800px-Justin_Bieber_in_2015.jpg"),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}
