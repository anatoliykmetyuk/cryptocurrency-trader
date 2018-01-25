package cryptotrader.db

import cryptotrader.model._


object user extends MemoryDb[UserInternal] {
  def login(name: String, passwordHash: String): Option[UserData] =
    database.collectFirst { case (id, u@UserInternal(_, uname, upass)) if uname == name && upass == passwordHash =>
      internalToExternal(u)
    }

  def register(name: String, passwordHash: String): UserData = {
    val id = nextId()
    val u = UserInternal(id, name, passwordHash)
    database(id) = u
    internalToExternal(u)
  }

  def update(id: Int, name: String, passwordHash: String): UserData = {
    val u = UserInternal(id, name, passwordHash)
    database(id) = u
    internalToExternal(u)
  }

  def delete(id: Int): Unit = {
    database -= id
  }

  def get(id: Int): Option[UserData] =
    database.get(id).map(internalToExternal)


  def internalToExternal(u: UserInternal): UserData =
    UserData(u.id, u.username)
}
