package by.iodkowski.user

import skunk.codec.all._
import skunk.implicits._
import skunk._

object UserQueries {

  val selectUser: Query[String, User] =
    sql"""
         SELECT id, username, password
         FROM users
         WHERE username = ${varchar(30)}
    """
      .query(uuid ~ varchar(30) ~ varchar(30))
      .gmap[User]

  val insertUser: Command[User] =
    sql"""
         INSERT INTO users
         VALUES ($uuid, ${varchar(30)}, ${varchar(30)})
    """
      .command
      .gcontramap[User]
}
