package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class KleisliClient(profileService: ProfileService) {
  val k = Kleisli[ReaderF, UserName, UserProfile](profileService.getProfile _)
  k.mapK[ReaderTFF, UserProfile](_.lift[Future]).liftMK[EitherTF]
}