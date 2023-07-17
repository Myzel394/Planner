package app.myzel394.planner

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.myzel394.planner.database.AppDatabase
import app.myzel394.planner.ui.Navigation
import app.myzel394.planner.ui.screens.OverviewScreen
import app.myzel394.planner.ui.theme.PlannerTheme
import com.google.android.material.color.DynamicColors

class MainActivity : AppCompatActivity() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DynamicColors.applyToActivityIfAvailable(this);

        setContent {
            PlannerTheme {
                Navigation(database)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlannerTheme {
        Greeting("Android")
    }
}