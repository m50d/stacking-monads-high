package net.cliché.stackingmonadshigh

import scalaz._

class ProfileService {
  def getProfile(username: UserName) = Reader((applicationContext: ApplicationContext) ⇒ applicationContext.getProfile(username))
}