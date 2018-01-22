package controllers

object ElsForm {
  import play.api.data.Forms._
  import play.api.data.Form

  case class Data(num: Int)

  val form = Form(
    mapping(
      "Enter the number of elements:" -> number(min = 1, max = 47)
    )(Data.apply)(Data.unapply)
  )
}
