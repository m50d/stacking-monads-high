package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

trait ProfileClient {
  def profile(username: EitherT[({ type L[A] = WriterT[Future, Vector[AuditEntry], A] })#L, NonEmptyList[NetworkError], UserName])
  : EitherT[({ type L[A] = WriterT[Future, Vector[AuditEntry], A] })#L, NonEmptyList[NetworkError], UserProfile]
}