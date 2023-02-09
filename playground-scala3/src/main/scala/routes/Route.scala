package routes

import java.time.LocalDate

import com.comcast.ip4s.*
import cats.effect.IO
import cats.syntax.either.*
import cats.syntax.applicative.*
import cats.syntax.semigroupk.*

import org.http4s.dsl.io.*
import org.http4s.{ HttpApp, HttpRoutes }
import org.http4s.server.Router
import org.http4s.server.staticcontent.*

import sttp.tapir.*
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object Routes:

  // --- エンドポイント -----------------------------------
  // PublicEndpoint[入力に使う型, エラー型, 出力に使う型, -R]
  // 認証ありの場合： Endpoint[認証に使う型, 入力に使う型, エラー型, 出力に使う型, -R]
  val helloEP: PublicEndpoint[String, Unit, String, Any] =
    endpoint.get
      .in ("hello" / path[String] ("name") )
      .out (stringBody)

  val todayEP: PublicEndpoint[Unit, Unit, String, Any] =
    endpoint.get
      .in ("today")
      .out (stringBody)

  val swaggerEPs = SwaggerInterpreter().fromEndpoints[IO](
    List(helloEP, todayEP),
    "http4s × tapir × Swagger",
    "1.0"
  )

  // --- ビジネスロジック -----------------------------------
  def helloLogic(name: String): IO[Either[Unit, String]] =
    s"Hello $name".asRight[Unit].pure[IO]

  def todayLogic(a: Unit): IO[Either[Unit, String]] =
    s"Today is ${LocalDate.now()}".asRight[Unit].pure[IO]

  // --- エンドポイント + ビジネスロジック -----------------------------------
  val helloRoute: HttpRoutes[IO] =
    Http4sServerInterpreter[IO] ().toRoutes (helloEP serverLogic helloLogic)

  val todayRoute: HttpRoutes[IO] =
    Http4sServerInterpreter[IO] ().toRoutes (todayEP serverLogic todayLogic)

  val swaggerRoutes: HttpRoutes[IO] = Http4sServerInterpreter[IO]().toRoutes(swaggerEPs)

  // --- HttpApp -----------------------------------
  val services: HttpApp[IO] =
    (helloRoute <+> todayRoute <+> swaggerRoutes).orNotFound