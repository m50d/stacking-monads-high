package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scalaz.transfigure._
import scalaz.transfigure.TransfigureToSyntax._
import scala.Predef.identity

class TransfigureClient(profileService: ProfileService) {
  def complexCalculation(username: EitherTFF[UserName]) = {
    val un: Future[WriterF[EitherF[UserName]]] = username.run.run.map { case (s, a) ⇒ Writer(s, a) }
    val a1 = un
      .transfigureTo3[Future, WriterF, EitherF](profileService.getProfile)
    val a2 = (a1: Future[WriterF[EitherF[UserProfile]]])
      .transfigureTo3[Future, WriterF, EitherF](profileService.fetchFavouriteTag)

    //    for {
    //      un ← username
    //      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
    //      tags ← EitherT(profileService.fetchFavouriteTags(profile).point[ReaderTFF])
    //      score ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], Double](profileService.calculateScore(tags).liftReaderT[ApplicationContext])
    //      inferredTags ← EitherT(Kleisli { _: ApplicationContext ⇒ profileService.fetchInferredTags(score).run }: ReaderTFF[NonEmptyList[NetworkError] \/ List[String]])
    //    } yield inferredTags
  }

}