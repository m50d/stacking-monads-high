package net.cliché.stackingmonadshigh

case class ApplicationContext()

case class UserProfile()

sealed trait NetworkError

case object NoNetwork extends NetworkError

case object Interrupted extends NetworkError