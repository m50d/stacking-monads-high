package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class KleisliClient(profileService: ProfileService) {
  type R[A] = Reader[ApplicationContext, A]
  type RT[A] = ReaderT[Future, ApplicationContext, A]
  type ET[F[_], B] = EitherT[F, NetworkError, B]
  
  val k = Kleisli[R, UserName, UserProfile](profileService.getProfile _)
  k.mapK[RT, UserProfile](_.lift[Future]).liftMK[ET]

}