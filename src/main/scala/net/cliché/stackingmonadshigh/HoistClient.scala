package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class HoistClient(profileService: ProfileService) {
  def mangle(username: UserName): Future[Float] = null

  def profile(username: UserName) = {
    MonadPartialOrder.transformer[ReaderTFF, EitherTF]: MonadPartialOrder[EitherTFF, ReaderTFF]
    MonadPartialOrder.transformer[Future, ReaderTF]: MonadPartialOrder[ReaderTFF, Future]
    MonadPartialOrder.transitive[EitherTFF, ReaderTFF, Future]: MonadPartialOrder[EitherTFF, Future]
    
    implicitly[EitherTFF |>=| ReaderTFF]
    implicitly[ReaderTFF |>=| Future]
    //Why doesn't this work?
//    implicitly[S |>=| Future]
//    implicitly[S |>=| Future].promote(mangle(username))
  }
}