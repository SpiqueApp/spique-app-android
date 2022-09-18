package com.aaron.spique.ui.phraselist.ui.viewmodel

import android.content.Context
import androidx.annotation.ColorInt
import com.aaron.spique.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ColorMapper {
    @ColorInt
    fun getColorFromString(color: String): Int
}

class ColorMapperColorResources @Inject constructor(
    @ApplicationContext val context: Context
) : ColorMapper {

    @ColorInt
    override fun getColorFromString(color: String): Int {
        return context.getColor(
            when (color) {
                "orange" -> R.color.tile_orange
                "blue" -> R.color.tile_blue
                "red" -> R.color.tile_red
                "green" -> R.color.tile_green
                "yellow" -> R.color.tile_yellow
                "purple" -> R.color.tile_purple
                "pink" -> R.color.tile_pink
                "gray" -> R.color.tile_gray
                else -> R.color.white
            }
        )
    }
}