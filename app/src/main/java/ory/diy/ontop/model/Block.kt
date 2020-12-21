package ory.diy.ontop.model

import ory.diy.ontop.R

abstract class Block (
    var name: String = "Default name",
    var type: String = "Default type"
){

    companion object {
        const val BLOCK_COLOR_BLUE = R.color.color_on_top_blue
        const val BLOCK_COLOR_GREEN = R.color.color_on_top_green
        const val BLOCK_COLOR_YELLOW = R.color.color_on_top_yellow
        const val BLOCK_COLOR_ORANGE = R.color.color_on_top_orange
        const val BLOCK_COLOR_RED = R.color.color_on_top_red

        const val BLOCK_COLOR_CYAN = R.color.color_on_top_cyan
        const val BLOCK_COLOR_PINK = R.color.color_on_top_pink
        const val BLOCK_COLOR_PURPLE = R.color.color_on_top_purple
    }
}