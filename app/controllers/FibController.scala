package controllers

import javax.inject.Inject

import play.api.data._
import play.api.i18n._
import play.api.mvc._

class FibController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  import ElsForm._

  val fibs:Stream[Int] = 0 #:: 1 #:: (fibs zip fibs.tail).map{ t => t._1 + t._2 }

  def fibSeq(n: Int): List[Int] = {
    fibs take n toList
  }

  private var sequence = fibSeq(0)

  private val postUrl = routes.FibController.createSeq()

  def index = Action {
    Ok(views.html.index())
  }

  def list = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.list(sequence, form, postUrl))
  }

  def createSeq = Action { implicit request: MessagesRequest[AnyContent] =>

    val errorFunction = { formWithErrors: Form[Data] =>
      BadRequest(views.html.list(sequence, formWithErrors, postUrl))
    }

    val successFunction = { data: Data =>
      sequence = fibSeq(data.num)
      Redirect(routes.FibController.list())
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }
}
