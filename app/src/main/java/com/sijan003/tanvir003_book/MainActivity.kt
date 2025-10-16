package com.sijan003.tanvir003_book

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sijan003.tanvir003_book.ui.theme.Sijan003_BookTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sijan003_BookTheme {
                // Call our new AppNavigator, which handles all screen routing
                AppNavigator()
            }
        }
    }
}

// ------------------- NAVIGATION SETUP -------------------

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home" // The first screen to show
    ) {
        // Route for the Home Screen
        composable(route = "home") {
            HomeScreen(navController = navController)
        }

        // Route for the Detail Screen
        composable(route = "detail") {
            DetailScreen(navController = navController)
        }
    }
}


// ------------------- SCREENS -------------------

/**
 * This is your original TestLayout, now refactored as the HomeScreen.
 * It takes a NavController to handle navigation actions.
 */
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    // Scaffold is useful for top bars, etc., but we can place your layout directly inside
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .background(Color.Green),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(6) {
                    //display a dice
                    Image(
                        painter = painterResource(image_ids[it]),
                        contentDescription = "Dice ${it + 1}",
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Click dice ${it + 1}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .background(Color.Gray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Column 2")
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Cyan),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    // Navigate to the "detail" route when clicked
                    navController.navigate("detail")
                }) {
                    Text("Go to Detail Screen")
                }
            }
        }
    }
}

/**
 * A new screen to navigate to.
 */
@Composable
fun DetailScreen(navController: NavController) {
    // 1. Create a state variable to hold the text from the TextField.
    //    'remember' ensures the state is not lost when the screen redraws.
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow) // Keeping the background for clarity
            .padding(16.dp), // Add some padding for better layout
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is the Detail Screen", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))

        // 2. Add the TextField composable
        TextField(
            value = text, // The text to display in the field
            onValueChange = { newText ->
                text = newText // Update the state variable on every keystroke
            },
            label = { Text("Enter your name") }, // A helpful placeholder label
            singleLine = true, // Optional: Makes it a single-line input field
            modifier = Modifier.fillMaxWidth() // Optional: Makes it span the screen width
        )

        Spacer(modifier = Modifier.height(20.dp))

        // This button can now use the text from the TextField
        Button(onClick = {
            // Show a toast with the entered name
            if (text.isNotBlank()) {
                Toast.makeText(context, "Hello, $text!", Toast.LENGTH_SHORT).show()
            }
            // Go back to the previous screen
            navController.popBackStack()
        }) {
            Text("Go Back")
        }
    }
}

// This list of image resources remains the same
private val image_ids = listOf(
    R.drawable.dice_1,
    R.drawable.dice_2,
    R.drawable.dice_3,
    R.drawable.dice_4,
    R.drawable.dice_5,
    R.drawable.dice_6
)

// You can keep previews for individual screens to test them in isolation
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Sijan003_BookTheme {
        // You can't truly preview navigation, so we pass a dummy NavController
        // or just preview the layout itself.
        HomeScreen(navController = rememberNavController())
    }
}