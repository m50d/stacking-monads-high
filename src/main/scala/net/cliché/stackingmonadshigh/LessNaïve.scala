package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class LessNaïve(profileService: ProfileService) extends ProfileClient {
  //An earlier version of this talk used ReaderT rather than WriterT.
  //ReaderT has a .lift[Future], but this is specific to Reader.
  //This was the best "general" solution I could come up with
  val futurePoint = new (Id ~> Future) {
//    def apply[A]
  }
  val writerFuturePoint = WriterT.writerTHoist[Vector[AuditEntry]].hoist(futurePoint)

  def profile(username: EitherTFF[UserName]) =
    for {
      un ← username
      profile ← EitherT.right[WriterTFF, NonEmptyList[NetworkError], UserProfile](writerFuturePoint(profileService.getProfile(un)))
    } yield profile

  def complexCalculation(username: EitherTFF[UserName]) =
    for {
      un ← username
      profile ← EitherT.right[WriterTFF, NonEmptyList[NetworkError], UserProfile](writerFuturePoint(profileService.getProfile(un)))
      tags ← EitherT(profileService.fetchFavouriteTags(profile).point[WriterTFF])
      score ← EitherT.right[WriterTFF, NonEmptyList[NetworkError], Double](profileService.calculateScore(tags).liftReaderT[ApplicationContext])
      inferredTags ← EitherT(Kleisli { _: ApplicationContext ⇒ profileService.fetchInferredTags(score).run }: ReaderTFF[NonEmptyList[NetworkError] \/ List[String]])
    } yield inferredTags
}