package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class KleisliClient(profileService: ProfileService) {

  val k =
    profileService.getProfileK.mapK[ReaderTFF, UserProfile](_.lift[Future]).liftMK[EitherTF] >=>
      profileService.fetchFavouriteTagsK.mapK[EitherTFF, List[String]] { e ⇒ EitherT(e.point[ReaderTFF]) } >=>
      profileService.calculateScoreK.liftMK[ReaderTF].liftMK[EitherTF] >=>
      profileService.fetchInferredTagsK.mapK[EitherTFF, List[String]](Hoist[EitherTF].hoist(implicitly[ReaderTFF |>=| Future]).apply)

}