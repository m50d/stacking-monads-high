package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

class Naïve {
  def profile(username: EitherT[({type L[A]=ReaderT[Future, ApplicationContext, A]})#L, NonEmptyList[NetworkError], UserName]) =
    null
}