package net.cliché

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future
import scala.language.higherKinds

package object stackingmonadshigh {
  type WriterF[A] = Reader[Vector[AuditEntry], A]
  type WriterTF[F[_], A] = WriterT[F, Vector[AuditEntry], A]
  type WriterTFF[A] = WriterTF[Future, A]
  type EitherF[A] = NonEmptyList[NetworkError] \/ A
  type EitherTF[F[_], B] = EitherT[F, NonEmptyList[NetworkError], B]
  type EitherTFG[A] = EitherTF[WriterF, A]
  type EitherTFF[A] = EitherTF[WriterTFF, A]
}