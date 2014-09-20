
for {
    a <- fetchTweet()
    b = computeRating(a)
    c <- postReply(a, b)
    } yield c.status
