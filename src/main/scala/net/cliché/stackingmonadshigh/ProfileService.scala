package net.cliché.stackingmonadshigh

import scalaz._
import scalaz.concurrent.Future

trait ProfileService {
  def getProfile(username: UserName) = Reader((applicationContext: ApplicationContext) ⇒ applicationContext.getProfile(username))
  
  def fetchFavouriteTags(profile: UserProfile): NonEmptyList[NetworkError] \/ List[String]
  
  def calculateScore(data: List[String]): Future[Double]
  
  def fetchInferredTags(score: Double): EitherT[Future, NonEmptyList[NetworkError], List[String]]
}