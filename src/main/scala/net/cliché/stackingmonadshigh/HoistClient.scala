package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class HoistClient(profileService: ProfileService) {
  type R[A] = Reader[ApplicationContext, A]
  type RT[A] = ReaderT[Future, ApplicationContext, A]
  type ET[F[_], B] = EitherT[F, NetworkError, B]
  type S[A] = ET[RT, A]
  
  def profile(username: UserName) = 
    implicitly[MonadPartialOrder[S, R]].promote(profileService.getProfile(username))
}