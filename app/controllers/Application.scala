package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models._

object Application extends Controller {

  def index = Action {
    val user = User.all
    Ok(views.html.index(user))
  }

  val signup_form: Form[User] = Form( mapping(
    "firstName" -> nonEmptyText, 
    "middleInitial" -> nonEmptyText, 
    "lastName" -> nonEmptyText, 
    "age" -> number) 
    ((firstName, middleInitial, lastName, age) => User(null, firstName, middleInitial, lastName, age))
    ((user: User) => Some((user.firstName, user.middleInit, user.lastName, user.age)))
  )

  def add = Action {
    Ok(views.html.form(signup_form)) 
  }

  def submitUser = Action { implicit request => 
    signup_form.bindFromRequest.fold(
      errors => BadRequest(views.html.form(errors)),
      user => {
        val result = User.save(user) 

        println("----- ----- ----- ----- -----")
        println(user.firstName )
        println(user.lastName )
        println(user.middleInit )
        println(user.age )
        println("Insert succeeded! " + result )
        println("----- ----- ----- ----- -----")
        Redirect(routes.Application.index)
      }
    )
  }






}
