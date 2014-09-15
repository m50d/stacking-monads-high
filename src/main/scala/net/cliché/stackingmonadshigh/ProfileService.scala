package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.Scalaz._
import scalaz.concurrent.Future

trait ProfileService {
  def getProfile(username: UserName) = UserProfile().set(Vector[AuditEntry]())
  
  val getProfileK: Kleisli[WriterF, UserName, UserProfile]
  
  def fetchFavouriteTags(profile: UserProfile): NonEmptyList[NetworkError] \/ List[String]
  
  val fetchFavouriteTagsK: Kleisli[EitherF, UserProfile, List[String]] 
  
  def calculateScore(data: List[String]): Future[Double]
  
  val calculateScoreK: Kleisli[Future, List[String], Double]
  
  def fetchInferredTags(score: Double): EitherT[Future, NonEmptyList[NetworkError], List[String]]
  
  val fetchInferredTagsK: Kleisli[({type L[A] = EitherTF[Future, A]})#L, Double, List[String]]
}