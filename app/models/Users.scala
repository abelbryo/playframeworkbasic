package models

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import scala.slick.session.Session
import play.api.Play.current

case class User(id: Option[Long], firstName: String, middleInit: String, lastName: String, age: Int)


class Users extends Table[User]("USERS"){
  def id = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc) 
  def firstName = column[String]("FIRST_NAME")
  def middleInitial = column[String]("MIDDLE_NAME_INIT")
  def lastName = column[String]("LAST_NAME")
  def age = column[Int]("AGE")

  def * = id.? ~ firstName ~ middleInitial ~ lastName ~ age <> (User.apply _ , User.unapply _)

  def autoInc = firstName ~ middleInitial ~ lastName ~ age returning id.?
}

object User {

  var table = new Users

  def all: List[User] = DB.withSession { implicit session =>
    Query(table).list 
  }

  def save(user: User) : Option[Long] = {
    val id: Option[Long] = DB.withSession { implicit session => 
      table.autoInc.insert(user.firstName, user.middleInit, user.lastName, user.age) 
    }
    id 
  }
}
