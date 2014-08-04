package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class KleisliClient(profileService: ProfileService) {
  val k =
    profileService.getProfileK.mapK[ReaderTFF, UserProfile](_.lift[Future]).liftMK[EitherTF] >=>
      profileService.fetchFavouriteTagsK.mapK[ReaderTFF, List[String]](Kleisli.ask).mapK[EitherTFF, List[String]]() >=>
      profileService.calculateScoreK.liftMK[ReaderTF].liftMK[EitherTF]
}