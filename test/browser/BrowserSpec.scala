package browser

import org.scalatestplus.play._
import org.scalatestplus.play.guice._

class BrowserSpec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {

  "The browser should" must {
    "1 successfully process a form" in {
      val listURL = controllers.routes.FibController.list().absoluteURL(false, s"localhost:$port")

      go to listURL

      textField("Enter the number of elements:").value = "31"

      submit()

      eventually {
        pageSource contains "0"
        pageSource contains "1"
        pageSource contains "832040"
      }
    }
  }

  "The browser should" must {
    "2 successfully process a form" in {
      val listURL = controllers.routes.FibController.list().absoluteURL(false, s"localhost:$port")

      go to listURL

      textField("Enter the number of elements:").value = "1"

      submit()

      eventually {
        pageSource contains "0"
      }
    }
  }
}