package by.iodkowski.user

import skunk.codec.all._
import skunk.implicits._
import skunk._

object UserQueries {

  val selectUser: Query[String, User] =
    sql"""
         SELECT id, username, password
         FROM users
         WHERE username = $varchar
    """
      .query(uuid ~ varchar ~ varchar)
      .gmap[User]

  val insertUser: Command[User] =
    sql"""
         INSERT INTO users
         VALUES ($uuid, $varchar, $varchar)
    """
      .command
      .gcontramap[User]
}
