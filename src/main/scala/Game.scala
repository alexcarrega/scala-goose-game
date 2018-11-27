package com.alexcarrega.game.goose

import scala.util.Random

import Display.{styleHighlight => hl}

object Game {
   val START: Int = 0
   val GOAL: Int = 63
   val THE_BRIDGE: Int = 6
   val THE_BRIDGE_JUMP: Int = 12
   val THE_GOOSE: Vector[Int] = Vector(5, 9, 14, 18, 23, 27)

   val START_LABEL: String = hl("Start")
   val THE_BRIDGE_LABEL: String = s"${hl("The")} ${hl("Bridge")}"
   val THE_GOOSE_LABEL: String = s"${hl("The")} ${hl("Goose")}"
   val GOAL_LABEL: String = hl(GOAL)
   val WIN_LABEL: String = hl("Wins!!")

   private val _random = Random

   def label(playerPos: Int): String = {
      if (playerPos == START) {
         return START_LABEL
      } else if (playerPos == THE_BRIDGE) {
         return THE_BRIDGE_LABEL
      } else if (THE_GOOSE.contains(playerPos)) {
         return s"${hl(playerPos)}, $THE_GOOSE_LABEL"
      } else {
         return hl(playerPos)
      }
   }

   def action(playerName: String, roll1: Int, roll2: Int): String = {
      val playerLabel: String = hl(playerName)
      val playerPosOrig: Int = Players.get(playerName)
      var playerPos: Int = playerPosOrig
      var output: String = s"$playerLabel rolls ${hl(roll1)}, ${hl(roll2)}. $playerLabel moves from ${label(playerPos)} to "
      playerPos += roll1 + roll2
      var continueTurn: Boolean = true
      while (continueTurn) {
         if (playerPos == THE_BRIDGE) {
            output += s"$THE_BRIDGE_LABEL. $playerLabel jumps to "
            playerPos = THE_BRIDGE_JUMP
         } else if (THE_GOOSE.contains(playerPos)) {
            output += s"${label(playerPos)}. $playerLabel moves again and goes to "
            playerPos += roll1 + roll2
         } else if (playerPos == GOAL) {
            output += s"$GOAL_LABEL. $playerLabel $WIN_LABEL"
            continueTurn = false
         } else if (playerPos > GOAL) {
            playerPos = GOAL - (playerPos - GOAL)
            output += s"$GOAL_LABEL. $playerLabel bounces! $playerLabel returns to "
         } else {
            output += label(playerPos)
            continueTurn = false
         }
      }
      Players.move(playerName, playerPos)
      for (otherPlayerName <- Players.all if (otherPlayerName != playerName && Players.get(otherPlayerName) == playerPos)) {
         output += s". On ${label(playerPos)} there is ${hl(otherPlayerName)}, who returns to ${label(playerPosOrig)}"
         Players.move(otherPlayerName, playerPosOrig)
         return output
      }
      return output
   }

   def randomRoll: Int = {
      return _random.nextInt(6) + 1
   }
}
