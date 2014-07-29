package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

class Naïve(profileService: ProfileService) extends ProfileClient {
  def profile(username: EitherT[({ type L[A] = ReaderT[Future, ApplicationContext, A] })#L, NonEmptyList[NetworkError], UserName]) =
    username.run.map {
      case -\/(e) ⇒ -\/(e)
      case \/-(r) ⇒ profileService.getProfile(r)
    }
}