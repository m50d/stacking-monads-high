package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scalaz.TransfigureTo.syntax._

class TransfigureClient(profileService: ProfileService) {
  def complexCalculation(username: EitherTFF[UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
      tags ← EitherT(profileService.fetchFavouriteTags(profile).point[ReaderTFF])
      score ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], Double](profileService.calculateScore(tags).liftReaderT[ApplicationContext])
      inferredTags ← EitherT(Kleisli { _: ApplicationContext ⇒ profileService.fetchInferredTags(score).run }: ReaderTFF[NonEmptyList[NetworkError] \/ List[String]])
    } yield inferredTags

}