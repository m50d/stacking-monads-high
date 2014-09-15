package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds
import scala.Predef.implicitly

class KleisliClient(profileService: ProfileService) {
  val futurePoint = new (Id ~> Future) {
    def apply[A](a: A) = Future(a)
  }
  val writerFuturePoint = WriterT.writerTHoist[Vector[AuditEntry]].hoist(futurePoint)

  val k =
    profileService.getProfileK.mapK[WriterTFF, UserProfile](writerFuturePoint.apply).liftMK[EitherTF] >=>
      profileService.fetchFavouriteTagK.mapK[EitherTFF, String] { e ⇒ EitherT(e.point[WriterTFF]) } >=>
      profileService.calculateScoreK.liftMK[WriterTF].liftMK[EitherTF] >=>
      profileService.fetchInferredTagK.mapK[EitherTFF, String](Hoist[EitherTF].hoist(implicitly[WriterTFF |>=| Future]).apply)

}