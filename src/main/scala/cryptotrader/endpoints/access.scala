package cryptotrader
package endpoints

import io.finch._
import io.finch.circe._, io.circe.generic.auto._

import cryptotrader.model._
import crypto._

object access {
  def all = login :+: logout :+: signup

  def root = / :: "access"

  def login =
    post(root) :: jsonBody[LoginReq] mapOutput { case LoginReq(login, passwd) =>
      db.user.login(login, sha256(passwd, secret.passwordSalt)) match {
        case Some(u) => Ok(u)
        case None => err("Incorrect login or password")
      }
    }

  val haveGoodPassword = ValidationRule[SignupReq]("have password containing at least one digit and letter, and be at least 6 characters long") {
    case SignupReq(_, passwd) =>
      passwd.length >= 6 && passwd.exists(_.isLetter) && passwd.exists(_.isDigit) }

  def signup =
    post(root :: "signup") :: (jsonBody[SignupReq] should haveGoodPassword) mapOutput { case SignupReq(login, password) =>
      val u = db.user.register(login, sha256(password, secret.passwordSalt))
      Ok(u)
    }

  def logout =
    delete(root) :: authenticatedUser mapOutput { u =>
      msg(s"You have logged out, $u. Please delete your session")
    }
}
