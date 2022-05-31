package com.lynpo.view.shape


/**
 * [DuiConfigs]
 * DUI 配置信息
 **
 * Create by fujw on 2022/5/30, 星期一.
 */
object DuiConfigs {
    enum class Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN,
    }
    enum class Orientation {
        HORIZONTAL,
        VERTICAL,
    }
}

fun DuiConfigs.Direction.directionHorizontal() = this in arrayOf(DuiConfigs.Direction.LEFT, DuiConfigs.Direction.RIGHT)
fun DuiConfigs.Direction.directionVertical() = this in arrayOf(DuiConfigs.Direction.UP, DuiConfigs.Direction.DOWN)