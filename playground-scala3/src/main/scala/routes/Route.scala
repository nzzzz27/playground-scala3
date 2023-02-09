package routes

import com.comcast.ip4s.*
import cats.effect.IO

import org.http4s.dsl.io.*
import org.http4s.{ HttpApp, HttpRoutes }
import org.http4s.server.Router
import org.http4s.server.staticcontent.*

object Routes:

  val service = HttpRoutes.of[IO] {
    case GET -> Root => Ok("Hello world")
  }

  val router = Router(
    "/"       -> service
  ).orNotFound