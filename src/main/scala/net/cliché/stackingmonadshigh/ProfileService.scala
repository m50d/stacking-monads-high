package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

trait ProfileService {
  def getProfile(username: UserName) = UserProfile().set(Vector[AuditEntry]())
  
  val getProfileK: Kleisli[WriterF, UserName, UserProfile]
  
  def fetchFavouriteTag(profile: UserProfile): NonEmptyList[NetworkError] \/ String
  
  val fetchFavouriteTagK: Kleisli[EitherF, UserProfile, String] 
  
  def calculateScore(data: String): Future[Double]
  
  val calculateScoreK: Kleisli[Future, String, Double]
  
  def fetchInferredTag(score: Double): EitherT[Future, NonEmptyList[NetworkError], String]
  
  def fetchInferredTagT(score: Double): Future[NonEmptyList[NetworkError] \/ String]
  
  val fetchInferredTagK: Kleisli[({type L[A] = EitherTF[Future, A]})#L, Double, String]
}