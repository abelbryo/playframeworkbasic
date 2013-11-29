package controllers

import java.util.Date
import java.sql.Timestamp

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import org.mindrot.jbcrypt.BCrypt._


import models._

object Application extends Controller {

  def index = Action {
    val user = User.all
    Ok(views.html.index(user))
  }


  val registrationForm: Form[UserRegistration] = Form(mapping(
    "firstName" -> nonEmptyText,
    "middleInitial" -> nonEmptyText,
    "lastName" -> nonEmptyText,
    "age" -> number,
    "email" -> email,
    "password" -> text(minLength = 6),
    "password2" -> text)(UserRegistration.apply _)(UserRegistration.unapply _)
    verifying ( " Password should match ", f => f.password == f.password2 )

  )

  def add = Action {
    Ok(views.html.form(registrationForm))
  }

  def submitUser = Action { implicit request =>
    registrationForm.bindFromRequest.fold(
      errors => BadRequest(views.html.form(errors)),
      u => {
        val now = new Timestamp(new Date().getTime) 
        val user = User(null, u.firstName, u.middleInit, u.lastName, u.age, u.email, hashpw(u.password, gensalt()), now)

        val result = User.save(user)
        println("----- ----- ----- ----- -----")
        println("Insert succeeded! " + result)
        println("----- ----- ----- ----- -----")
        Redirect(routes.Application.index)
      })
  }

}
