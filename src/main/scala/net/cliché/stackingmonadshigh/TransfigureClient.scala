package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scalaz.transfigure._
import scalaz.transfigure.TransfigureToSyntax._
import scala.Predef.identity

class TransfigureClient(profileService: ProfileService) {
  def complexCalculation(username: EitherTFF[UserName]) = {
    val un: ReaderF[Future[EitherF[UserName]]] = username.run.mapK[Id, Future[EitherF[UserName]]](identity)

    un
      .transfigureTo3[ReaderF, Future, EitherF](profileService.getProfile _)
    //    transfigureTo[EitherTFF](profileService.getProfile _).run.run
    //      .map(_.transfigureTo[Future, EitherF](profileService.fetchFavouriteTags _))

    //    for {
    //      un ← username
    //      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
    //      tags ← EitherT(profileService.fetchFavouriteTags(profile).point[ReaderTFF])
    //      score ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], Double](profileService.calculateScore(tags).liftReaderT[ApplicationContext])
    //      inferredTags ← EitherT(Kleisli { _: ApplicationContext ⇒ profileService.fetchInferredTags(score).run }: ReaderTFF[NonEmptyList[NetworkError] \/ List[String]])
    //    } yield inferredTags
  }

}