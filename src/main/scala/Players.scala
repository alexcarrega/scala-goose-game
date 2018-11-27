package com.alexcarrega.game.goose

import scala.collection.mutable.HashMap

object Players {
   private val _db = HashMap.empty[String, Int]

   def add(name: String): this.type = {
      _db += (name -> 0)
      return this
   }

   def get(name: String): Int = {
      return _db(name)
   }

   def contains(name: String): Boolean = {
      return _db.contains(name)
   }

   def all: Iterable[String] = {
      return _db.keys
   }

   def move(name: String, pos: Int): this.type = {
      _db(name) = pos
      return this
   }
}
