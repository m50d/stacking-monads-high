package net.cliché.stackingmonadshigh

case class UserName()

case class UserProfile()

case class AuditEntry(message: String)

sealed trait NetworkError

case object NoNetwork extends NetworkError

case object Interrupted extends NetworkError