package com.alexcarrega.game.goose

import sbt.complete.DefaultParsers._
import sbt.complete.Parser

import Display.{styleHighlight => hl, NOT}

import Game.label

sealed trait Command {
  def run: Unit
}

object Help extends Command {
  override def run: Unit =
    Display.text(
      """
         |  Commands:

         |    ${hl("help")} [command...]              Provides help for a given command.
         |    ${hl("exit")}                           Exits application.
         |    ${hl("add player")} <name>              Add new player
         |    ${hl("move")} <player> [roll1] [roll2]  Make a move of a player

         |""".stripMargin
   )
}

object Exit extends Command {
   override def run: Unit = {
      Display.text("Bye!!!")
   }
}

case class AddPlayer(name: String) extends Command {
   override def run: Unit = {
      val playerLabel = hl(name);
      if (Players.contains(name)) {
         Display.warn(s"$playerLabel: already existing player")
      } else {
         Players.add(name)
         Display.info(s"players: ${Players.all.map(hl).mkString(", ")}")
      }
   }
}

case class Move(playerName: String, roll1: Option[Int] = None, roll2: Option[Int] = None) extends Command {
   override def run: Unit = {
      val playerLabel = hl(playerName)
      if (Players.contains(playerName)) {
         val optRoll1: Option[Int] = _checkRoll(roll1, "roll1")
         val optRoll2: Option[Int] = _checkRoll(roll2, "roll2")
         if (optRoll1 != None && optRoll2 != None) {
            Display.text(Game.action(playerName, optRoll1.get, optRoll2.get))
         }
      } else {
         Display.error(s"Player $playerLabel $NOT found")
      }
   }

   private def _checkRoll(roll: Option[Int], key: String): Option[Int] = {
      val valRoll: Int = roll.getOrElse(Game.randomRoll)
      if (valRoll < 1 || valRoll > 6) {
         Display.error(s"${hl(key)} value $NOT valid: ${hl(valRoll)}. Must be between ${hl(1)} and ${hl(6)}")
         return None
      } else {
         return Option(valRoll)
      }
   }
}

case class Unknown(command: String) extends Command {
   override def run: Unit = {
      if (command.length > 0) {
         Display.error(s"Command ${hl(command)} unknown")
      }
   }
}

object CommandParser {
   def parser: Parser[Command] = {
      val help = token("help" ^^^ Help)
      val exit = token("exit" ^^^ Exit)

      val addPlayer = {
         val command = token("add player") ~> Space
         val name = token(StringBasic, s"[${hl("Name")} of the player]")
         val combinedParser = command ~> name
         combinedParser.map {
            case name => AddPlayer(name)
         }
      }

      val move = {
         val command = token("move") ~> Space
         val playerName = token(StringBasic, s"[${hl("Name")} of the player]") <~ Space.?
         val roll1 = token(IntBasic, s"[${hl("First roll")}: number between ${hl(1)} and ${hl(6)}]") <~ Space
         val roll2 = token(IntBasic, s"[${hl("Second roll")}: number between ${hl(1)} and ${hl(6)}]")
         val combinedParser = command ~> playerName ~ (roll1.? ~ roll2.?)
         combinedParser.map {
            case (playerName, (roll1, roll2)) => Move(playerName, roll1, roll2)
         }
      }

      val unknown = {
         val command = token(any)
         val combinedParser = command.*
         combinedParser.map {
            case command => Unknown(command.mkString)
         }
      }

      return help | exit | addPlayer | move | unknown
   }
}
