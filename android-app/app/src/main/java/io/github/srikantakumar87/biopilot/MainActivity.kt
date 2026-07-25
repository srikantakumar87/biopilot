package io.github.srikantakumar87.biopilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.srikantakumar87.biopilot.ui.theme.BiopilotTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BiopilotTheme {
                BioPilotApp()
            }
        }
    }
}