package com.realityexpander.signup

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.realityexpander.signup.ui.theme.SignupTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignupTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Signup()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview()
@Composable
fun SignupPreview() {
//    SignupTheme {
        Signup()
//    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Signup() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Enter email

        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        var imageUri = remember { mutableStateOf<Uri?>(null) } // UPDATE
        val context = LocalContext.current
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri.value = uri

            // Show image
            if (imageUri.value != null) {
                val image = context.contentResolver.openInputStream(imageUri.value!!)

                if (image != null) {
                    bitmap = ImageDecoder
                        .createSource(context.contentResolver, imageUri.value!!)
                        .let { ImageDecoder.decodeBitmap(it) }
                }
                image?.close()
            }
        }

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
        )

        // Enter password
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
        )

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                "image/jpeg",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
                ,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                )
        }

        // Show & pick avatar
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text("Pick Avatar")
        }

        Button(
            onClick = {
                // do something

            }
        ) {
            Text(text = "Signup")
        }
    }
}










































