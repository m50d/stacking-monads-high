package net.cliché.stackingmonadshigh

case class UserName()

case class UserProfile()

class ApplicationContext {
  def getProfile(username: UserName) = UserProfile()
}

sealed trait NetworkError

case object NoNetwork extends NetworkError

case object Interrupted extends NetworkError