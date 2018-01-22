package unit

import controllers.ElsForm._
import controllers.FibController
import org.scalatestplus.play.PlaySpec
import play.api.data.FormError
import play.api.i18n._
import play.api.mvc._
import play.api.test._

class UnitSpec extends PlaySpec {

  "ElsForm" must {

    "1 apply successfully from request" in {
      val call = controllers.routes.FibController.createSeq()
      implicit val request: Request[_] = FakeRequest(call).withFormUrlEncodedBody("Enter the number of elements:" -> "25")
      val boundForm = form.bindFromRequest()
      val ElsData = boundForm.value.get

      ElsData.num must equal(25)
    }

    "2 apply successfully from request" in {
      val call = controllers.routes.FibController.createSeq()
      implicit val request: Request[_] = FakeRequest(call).withFormUrlEncodedBody("Enter the number of elements:" -> "1")
      val boundForm = form.bindFromRequest()
      val ElsData = boundForm.value.get

      ElsData.num must equal(1)
    }

    "1 apply successfully from map" in {
      val data = Map("Enter the number of elements:" -> "4")
      val boundForm = form.bind(data)
      val ElsData = boundForm.value.get

      ElsData.num must equal(4)
    }

    "2 apply successfully from map" in {
      val data = Map("Enter the number of elements:" -> "1")
      val boundForm = form.bind(data)
      val ElsData = boundForm.value.get

      ElsData.num must equal(1)
    }

    "1 show errors when applied unsuccessfully" in {
      val data = Map("Enter the number of elements:" -> "-100")

      val errorForm = form.bind(data)
      val listOfErrors = errorForm.errors
      val formError: FormError = listOfErrors.head
      formError.key must equal("Enter the number of elements:")

      errorForm.hasGlobalErrors mustBe false

      formError.message must equal("error.min")

      val lang: Lang = Lang.defaultLang
      val messagesApi: MessagesApi = new DefaultMessagesApi(Map(lang.code -> Map("error.min" -> "Must be greater or equal to {0}")))
      val messagesProvider: MessagesProvider = messagesApi.preferred(Seq(lang))
      val message: String = Messages(formError.message, formError.args: _*)(messagesProvider)

      message must equal("Must be greater or equal to 1")
    }

    "2 show errors when applied unsuccessfully" in {
      val data = Map("Enter the number of elements:" -> "48")

      val errorForm = form.bind(data)
      val listOfErrors = errorForm.errors
      val formError: FormError = listOfErrors.head
      formError.key must equal("Enter the number of elements:")

      errorForm.hasGlobalErrors mustBe false

      formError.message must equal("error.max")

      val lang: Lang = Lang.defaultLang
      val messagesApi: MessagesApi = new DefaultMessagesApi(Map(lang.code -> Map("error.max" -> "Must be less or equal to {0}")))
      val messagesProvider: MessagesProvider = messagesApi.preferred(Seq(lang))
      val message: String = Messages(formError.message, formError.args: _*)(messagesProvider)

      message must equal("Must be less or equal to 47")
    }
  }
}
