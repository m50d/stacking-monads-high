
//for {
//    a <- fetchTweet()
//    b = computeRating(a)
//    c <- postReply(a, b)
//    } yield c.status
//
//case class Kleisli[M[_], A, B](run: (A) ⇒ M[B])
