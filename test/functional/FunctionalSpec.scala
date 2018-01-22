package functional

import controllers.{FibController, routes}
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class FunctionalSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting with ScalaFutures {

  import CSRFTokenHelper._

  "FibController" must {

    "1 process a POST request successfully" in {
      val controller = inject[FibController]

      val request = FakeRequest(routes.FibController.createSeq())
        .withFormUrlEncodedBody("Enter the number of elements:" -> "7")
        .withCSRFToken
      val futureResult: Future[Result] = controller.createSeq().apply(request)


      whenReady(futureResult) { result =>
        result.header.headers(LOCATION) must equal(routes.FibController.list().url)
      }
    }

    "2 process a POST request successfully" in {
      val controller = inject[FibController]

      val request = FakeRequest(routes.FibController.createSeq())
        .withFormUrlEncodedBody("Enter the number of elements:" -> "1")
        .withCSRFToken
      val futureResult: Future[Result] = controller.createSeq().apply(request)


      whenReady(futureResult) { result =>
        result.header.headers(LOCATION) must equal(routes.FibController.list().url)
      }
    }

    "1 reject a POST request when given bad input" in {
      val controller = inject[FibController]

      val request = FakeRequest(routes.FibController.createSeq())
        .withFormUrlEncodedBody("Enter the number of elements:" -> "-1")
        .withCSRFToken
      val futureResult: Future[Result] = controller.createSeq().apply(request)

      status(futureResult) must be(Status.BAD_REQUEST)
    }

    "2 reject a POST request when given bad input" in {
      val controller = inject[FibController]

      val request = FakeRequest(routes.FibController.createSeq())
        .withFormUrlEncodedBody("Enter the number of elements:" -> "0")
        .withCSRFToken
      val futureResult: Future[Result] = controller.createSeq().apply(request)

      status(futureResult) must be(Status.BAD_REQUEST)
    }
  }
}
