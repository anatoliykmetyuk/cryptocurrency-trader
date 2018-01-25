package cryptotrader
package endpoints

import shapeless._

import io.finch._
import io.finch.circe._
import io.circe.generic.auto._

import cryptotrader.model._
import crypto._

object user {
  def all = create :+: read :+: update :+: deleteUser

  def root = / :: "user"

  def create =
    post(root) :: jsonBody[SignupReq] :: admin mapOutput { case SignupReq(login, password) :: _ :: HNil =>
      val u = db.user.register(login, sha256(password, secret.passwordSalt))
      Ok(u)
    }

  def read =
    get(root) :: int :: admin mapOutput { case id :: _ :: HNil =>
      db.user.get(id) match {
        case Some(u) => Ok(u)
        case None => err("Incorrect id")
      }
    }

  def update =
    put(root) :: jsonBody[UpdateReq] :: admin mapOutput { case UpdateReq(id, login, password) :: _ :: HNil =>
      val u = db.user.update(id, login, sha256(password, secret.passwordSalt))
      Ok(u)
    }

  def deleteUser =
    delete(root) :: int :: admin mapOutput { case id :: _ :: HNil =>
      db.user.delete(id)
      msg("Success")
    }
}
