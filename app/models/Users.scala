package models

import java.sql.Timestamp

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import scala.slick.session.Session
import play.api.Play.current

/**
 * This UserRegistration case class is only meant for the
 * form mapping which later can be used to map to the
 * actual USER table.
 */
case class UserRegistration(
                firstName: String,
                middleInit: String,
                lastName: String,
                age: Int,
                email:String,
                password: String,
                password2: String 
              )

/**
 * The User case class is used for the db table mapping.
 */
case class User(id: Option[Long],
                firstName: String,
                middleInit: String,
                lastName: String,
                age: Int,
                email:String,
                passwordHash: String,
                timeStamp: Timestamp 
              )

/**
 * The Users table based on the User case class.
 */
class Users extends Table[User]("USERS"){
  def id            = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc) 
  def firstName     = column[String]("FIRST_NAME")
  def middleInitial = column[String]("MIDDLE_NAME_INIT")
  def lastName      = column[String]("LAST_NAME")
  def age           = column[Int]("AGE")
  def email         = column[String]("EMAIL")
  def passwordHash  = column[String]("PASSWORD_HASH")
  def timeStamp     = column[Timestamp]("TIMESTAMP")

  def * = id.? ~ firstName ~ middleInitial ~ lastName ~ age ~ email ~ passwordHash ~ timeStamp <> (User.apply _ , User.unapply _)

  def autoInc = firstName ~ middleInitial ~ lastName ~ age ~ email ~ passwordHash ~ timeStamp returning id.?
}

// Companion object 
object User {

  var table = new Users

  def all: List[User] = DB.withSession { implicit session =>
    Query(table).list 
  }

  def save(u: User) : Option[Long] = {
    val id: Option[Long] = DB.withSession { implicit session => 
      table.autoInc.insert(u.firstName, u.middleInit, u.lastName, u.age, u.email, u.passwordHash, u.timeStamp) 
    }  
  id 
  }
}
