package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

class HoistClient(profileService: ProfileService) {
  type ReaderF[A] = Reader[ApplicationContext, A]
  type ReaderTF[F[_], A] = ReaderT[F, ApplicationContext, A]
  type ReaderTFF[A] = ReaderTF[Future, A]
  type ET[F[_], B] = EitherT[F, NetworkError, B]
  type S[A] = ET[ReaderTFF, A]
  
  def profile(username: UserName) = {
    MonadPartialOrder.transformer[ReaderTFF, ET]: MonadPartialOrder[S, ReaderTFF]
    MonadPartialOrder.transformer[Future, ReaderTF]: MonadPartialOrder[ReaderTFF, Future]
//    MonadPartialOrder.transform
  }
//    implicitly[MonadPartialOrder[S, R]].promote(profileService.getProfile(username))
}