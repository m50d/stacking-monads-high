package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class LessNaïve(profileService: ProfileService) extends ProfileClient {

  def profile(username: EitherT[ReaderTFF, NonEmptyList[NetworkError], UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
    } yield profile

  def complexCalculation(username: S[UserName]) =
    for {
      un ← username
      profile ← EitherT.right[ReaderTFF, NonEmptyList[NetworkError], UserProfile](profileService.getProfile(un).lift[Future])
      tags ← MonadTrans[EitherTF].liftM(
        MonadTrans[ReaderTF].liftM[EitherF, List[String]](profileService.fetchFavouriteTags(profile)))
    } yield {}
}