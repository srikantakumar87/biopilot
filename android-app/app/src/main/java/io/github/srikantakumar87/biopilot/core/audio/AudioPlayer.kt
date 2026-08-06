package io.github.srikantakumar87.biopilot.core.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(

    @ApplicationContext
    private val context: Context

) {

    fun play(@RawRes sound: Int) {

        MediaPlayer.create(context, sound)?.apply {

            setOnCompletionListener {

                release()
            }

            start()
        }
    }
}