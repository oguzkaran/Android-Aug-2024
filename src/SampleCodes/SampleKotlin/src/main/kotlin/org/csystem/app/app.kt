package org.csystem.app

fun main() {
    move(Direction.RIGHT)
    move(Direction.BOTTOM)
    move(Direction.TOP)
    //...
}

enum class Direction {
    RIGHT, TOP, LEFT, BOTTOM
}

fun move(direction: Direction) {

}
