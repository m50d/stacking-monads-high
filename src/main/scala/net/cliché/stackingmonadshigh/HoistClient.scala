package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class HoistClient(profileService: ProfileService) {
  def mangle(username: UserName): Future[Float] = null

  def profile(username: UserName) = {
    MonadPartialOrder.transformer[ReaderTFF, ET]: MonadPartialOrder[S, ReaderTFF]
    MonadPartialOrder.transformer[Future, ReaderTF]: MonadPartialOrder[ReaderTFF, Future]
    MonadPartialOrder.transitive[S, ReaderTFF, Future]: MonadPartialOrder[S, Future]
    
    implicitly[S |>=| ReaderTFF]
    implicitly[ReaderTFF |>=| Future]
    //Why doesn't this work?
//    implicitly[S |>=| Future]
//    implicitly[S |>=| Future].promote(mangle(username))
  }
}