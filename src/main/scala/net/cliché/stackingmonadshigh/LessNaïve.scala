package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class LessNaïve(profileService: ProfileService) extends ProfileClient {
  type EitherF[A] = NonEmptyList[NetworkError] \/ A
  type ReaderFuture[A] = ReaderT[Future, ApplicationContext, A]
  type ReaderTF[F[_], A] = ReaderT[F, ApplicationContext, A]

  def profile(username: EitherT[ReaderFuture, NonEmptyList[NetworkError], UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderFuture, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
    } yield profile
   
  def complexCalculation(username: EitherT[ReaderFuture, NonEmptyList[NetworkError], UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderFuture, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
      tags ← MonadTrans[ReaderTF].liftM[EitherF, List[String]](profileService.fetchFavouriteTags(profile))
    } yield {}
}