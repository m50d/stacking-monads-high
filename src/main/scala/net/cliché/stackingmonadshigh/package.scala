package net.cliché

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

package object stackingmonadshigh {
  type EitherF[A] = NonEmptyList[NetworkError] \/ A
  type ReaderF[A] = Reader[ApplicationContext, A]
  type ReaderTF[F[_], A] = ReaderT[F, ApplicationContext, A]
  type ReaderTFF[A] = ReaderTF[Future, A]
  type EitherTF[F[_], B] = EitherT[F, NetworkError, B]
  type S[A] = EitherTF[ReaderTFF, A]
}