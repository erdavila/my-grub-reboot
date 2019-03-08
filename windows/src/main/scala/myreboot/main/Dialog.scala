package myreboot.main

import myreboot.{Action, Platform}

object Dialog extends DialogBase {

  override protected def platformName: String = "Windows"

  override protected def actions: Seq[Action] = Seq(
    Action("Desligar") { Platform.shutdown() },
    Action("Reiniciar usando o monitor") {},
    Action("Reiniciar usando a TV") {},
    Action("Reiniciar no Linux") {},
  )
}
