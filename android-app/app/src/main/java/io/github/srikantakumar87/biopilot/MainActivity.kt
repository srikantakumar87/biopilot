package io.github.srikantakumar87.biopilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.srikantakumar87.biopilot.core.theme.BiopilotTheme
import io.github.srikantakumar87.biopilot.navigation.BioPilotApp


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