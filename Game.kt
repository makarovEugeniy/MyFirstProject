package com.bignerdranch.nyethack

import java.lang.Exception
import java.text.BreakIterator

fun main() {
    Game.play()
}


object Game {
    private val player = Player("Madrigal")
    private var currentRoom = TownSquare()

    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )



    init {
        println("Welcome, adventurer.")
        player.castFireball()
    }
    var continueGame = true
    fun play() {
        while (continueGame){
            println(currentRoom.description())
            println(currentRoom.load())
            printPlayerStatus(player)

            print("> Enter your command: ")
            println(GameInput(readLine()).processCommand())
        }
        println("Good luck, ${player.name}")
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) " +
                "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when (command.toLowerCase()) {
            "move" -> move(argument)
            "quit" -> continueGame = false
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds.")
            }

            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom as TownSquare
            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction: $directionInput."
        }
}
