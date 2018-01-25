package cryptotrader.model

case class SignupReq(login: String, password: String)
case class LoginReq (login: String, password: String)
case class UpdateReq(id: Int, login: String, password: String)

