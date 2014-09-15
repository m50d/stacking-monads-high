package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scalaz.transfigure._
import scalaz.transfigure.TransfigureToSyntax._
import scala.Predef.identity

class TransfigureClient(profileService: ProfileService) {
  def complexCalculation(username: EitherTFF[UserName]) = {
    val un = username.run.run.map { case (s, a) ⇒ Writer(s, a) }

    val a1 = (un: Future[WriterF[EitherF[UserName]]])
      .transfigureTo3[Future, WriterF, EitherF](profileService.getProfile)
    val a2 = (a1: Future[WriterF[EitherF[UserProfile]]])
      .transfigureTo3[Future, WriterF, EitherF](profileService.fetchFavouriteTag)
    val a3 = (a2: Future[WriterF[EitherF[String]]])
      .transfigureTo3[Future, WriterF, EitherF](profileService.calculateScore)
    val a4 = (a3: Future[WriterF[EitherF[Double]]])
      .transfigureTo3[Future, WriterF, EitherF](profileService.fetchInferredTagT)
  }
}