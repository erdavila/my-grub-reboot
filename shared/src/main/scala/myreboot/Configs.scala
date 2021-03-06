package myreboot

import java.io.{File, FileReader}
import java.util.Properties

object Configs {

  val FileName = "my-reboot-configs.properties"

  val GrubEntrySubKey = "grubEntry"
  val DeviceIdSubKey = "deviceId"
  val DisplaySwitchArgSubKey = "displaySwitchArg"

  def load(directory: File): Configs = {
    val props = new Properties
    val file = new File(directory, FileName)
    val reader = new FileReader(file)
    props.load(reader)
    reader.close()

    def enumProperties[A <: WithCode](values: Seq[A], subKey: String): Map[A, String] =
      values.map { value =>
        val key = value.code + "." + subKey
        val property = props.getProperty(key)
        value -> property
      }.toMap

    new Configs(
      osGrubEntry = enumProperties(OS.Values, GrubEntrySubKey),
      windowsDeviceIds = enumProperties(Display.Values, DeviceIdSubKey),
      windowsDisplaySwitchArgs = enumProperties(Display.Values, DisplaySwitchArgSubKey),
    )
  }

}

class Configs private(
  val osGrubEntry: Map[OS, String],
  val windowsDeviceIds: Map[Display, String],
  val windowsDisplaySwitchArgs: Map[Display, String],
) {
  def osByEntry(entry: String): Option[OS] =
    osGrubEntry.collectFirst { case (os, `entry`) => os }

  def displayByDeviceId(deviceId: String): Option[Display] =
    windowsDeviceIds.collectFirst { case (display, `deviceId`) => display }
}
