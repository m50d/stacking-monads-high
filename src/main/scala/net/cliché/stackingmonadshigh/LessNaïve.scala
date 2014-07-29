package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

class LessNaïve(profileService: ProfileService) extends ProfileClient {
  type ReaderFuture[A] = ReaderT[Future, ApplicationContext, A]

  def profile(username: EitherT[ReaderFuture, NonEmptyList[NetworkError], UserName]) =
    for {
      un ← username
      profile ← profileService.getProfile(un).lift[Future] |> EitherT.right[ReaderFuture, NonEmptyList[NetworkError], UserProfile]
    } yield profile
}