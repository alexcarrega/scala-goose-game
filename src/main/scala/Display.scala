package com.alexcarrega.game.goose

object Display {
  final val NOT = "NOT"
  final val NEW_LINE: String = "\n"
  final val ARROW: String = fansi.Color.Cyan(">>>").toString

  def styleInfo(text: Any): String = fansi.Color.Magenta(s"$text").toString
  def styleWarn(text: Any): String = fansi.Color.Yellow(s"$text").toString
  def styleError(text: Any): String = fansi.Color.Red(s"$text").toString

  def styleHead(text: Any): String = fansi.Color.Green(s"$text").toString
  def stylePrompt(text: Any): String = (fansi.Color.Cyan ++ fansi.Bold.On)(s"$text").toString
  def styleText(text: Any): String = fansi.Color.White(s"$text").toString

  def styleHighlight(text: Any): String = fansi.Bold.On(s"$text").toString

  def info(text: Any): Unit = println(NEW_LINE ++ styleInfo(text) ++ NEW_LINE)
  def warn(text: Any): Unit = println(NEW_LINE ++ styleWarn(text) ++ NEW_LINE)
  def error(text: Any): Unit = println(NEW_LINE ++ styleError(text) ++ NEW_LINE)

  def head(text: Any): Unit = println(NEW_LINE ++ styleHead(text) ++ NEW_LINE)
  def text(text: Any): Unit = println(NEW_LINE ++ styleText(text) ++ NEW_LINE)
}
