package ory.diy.ontop.model

import ory.diy.ontop.R

class NotePlane(val title: String, val text: String, val color: Int = NOTE_COLOR_DEFAULT) : IPlane {


    companion object {
        const val NOTE_COLOR_DEFAULT = R.color.color_panel_background
        const val NOTE_COLOR_BLUE = R.color.color_on_top_blue
        const val NOTE_COLOR_GREEN = R.color.color_on_top_green
        const val NOTE_COLOR_YELLOW = R.color.color_on_top_yellow
        const val NOTE_COLOR_ORANGE = R.color.color_on_top_orange
        const val NOTE_COLOR_RED = R.color.color_on_top_red
    }
}