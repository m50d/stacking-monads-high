package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class LessNaïve(profileService: ProfileService) extends ProfileClient {

  def profile(username: EitherTFF[UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
    } yield profile

  def complexCalculation(username: EitherTFF[UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
      tags ← EitherT(profileService.fetchFavouriteTags(profile).point[ReaderTFF])
      score ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], Double](profileService.calculateScore(tags).liftReaderT[ApplicationContext])
      inferredTags ← EitherT(Kleisli { _: ApplicationContext ⇒ profileService.fetchInferredTags(score).run }: ReaderTFF[NonEmptyList[NetworkError] \/ List[String]])
    } yield inferredTags
}