package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class LessNaïve(profileService: ProfileService) extends ProfileClient {
  //An earlier version of this talk used ReaderT rather than WriterT.
  //ReaderT has a .lift[Future], but this is specific to Reader.
  //This was the best "general" solution I could come up with
  //It's possible an alternative exists using |>=|
  val futurePoint = new (Id ~> Future) {
    def apply[A](a: A) = Future(a)
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
      score ← EitherT.right[WriterTFF, NonEmptyList[NetworkError], Double](WriterT.put(profileService.calculateScore(tags))(Vector[AuditEntry]()))
      inferredTags ← EitherT(WriterT(profileService.fetchInferredTags(1.0).run.map(e ⇒ (Vector(), e))): WriterTFF[NonEmptyList[NetworkError] \/ List[String]])
    } yield inferredTags
}