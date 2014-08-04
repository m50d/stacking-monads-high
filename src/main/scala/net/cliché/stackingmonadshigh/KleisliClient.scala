package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class KleisliClient(profileService: ProfileService) {
  //  implicitly[EitherTFG =:= Kleisli[]]

  val k =
    //    profileService.getProfileK.mapK[ReaderTFF, UserProfile](_.lift[Future]).liftMK[EitherTF] >=>
    profileService.fetchFavouriteTagsK.mapK[EitherTFG, List[String]] {
      e ⇒ EitherT[ReaderF, NonEmptyList[NetworkError], List[String]] { Kleisli[Id, ApplicationContext, EitherF[List[String]]] { _: ApplicationContext ⇒ e } } }
      //.mapK[EitherTFF, List[String]]() >=>
  //      profileService.calculateScoreK.liftMK[ReaderTF].liftMK[EitherTF]
}