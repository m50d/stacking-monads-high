package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

class Naïve(profileService: ProfileService) extends ProfileClient {
  def profile(username: EitherT[({ type L[A] = WriterT[Future, Vector[AuditEntry], A] })#L, NonEmptyList[NetworkError], UserName]) =
    EitherT[({ type L[A] = WriterT[Future, Vector[AuditEntry], A] })#L, NonEmptyList[NetworkError], UserProfile] {
      WriterT {
        username.run.run.map {
          case (audit, result) ⇒
            result match {
              case -\/(e) ⇒ (audit, -\/(e))
              case \/-(r) ⇒
                val (audit2, r2) = profileService.getProfile(r).run
                (audit ++ audit2, \/-(r2))
            }
        }
      }
    }
}