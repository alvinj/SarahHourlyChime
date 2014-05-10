package com.devdaily.sarah.plugin.hourlychime

import com.devdaily.sarah.plugins._
import java.util.Calendar
import java.text.SimpleDateFormat
import akka.actor.ActorRef
import com.devdaily.sarah.actors.StartPluginMessage
import com.devdaily.sarah.actors.StopPluginMessage
import akka.event.Logging
import akka.actor.ActorSystem
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class AkkaHourlyChimePlugin 
extends SarahAkkaActorBasedPlugin {

  implicit val actorSystem = ActorSystem("CurrentTimeActorSystem")

  def receive = {
    case StartPluginMessage(theBrain) => 
         brain = theBrain
         startLoop

    case _ => println("HourlyChime got an unexpected message.") 
  }
  
  def startLoop {
    while (true) {
      sleepForAMinute
      if (onTheHour) {
        println("IN OnTheHour, about to call Brain ...")
        val currentTime = format("%s:%s %s\n", getCurrentHour, getCurrentMinute, getAmOrPm)
        val f = Future { brain ! PleaseSay("The current time is " + currentTime) }
      }
    }
  }

  /**
   * TODO these next two methods are needed to implement SarahPlugin.
   *      fix the API so these aren't needed for Actor plugins.
   */
  def textPhrasesICanHandle: List[String] = List("foo bar baz")
  def handlePhrase(phrase: String): Boolean = false
  def startPlugin = ()
  
  def sleepForAMinute = PluginUtils.sleep(60*1000)
  
  // returns true if minutes = 0, i.e., the current time is "on the hour"
  def onTheHour :Boolean = {
    val today = Calendar.getInstance().getTime()
    val minuteFormat = new SimpleDateFormat("mm")
    val currentMinuteAsString = minuteFormat.format(today)
    try {
      val currentMinute = Integer.parseInt(currentMinuteAsString)
      if (currentMinute % 60 == 0) return true
      else return false
    } catch {
      case _: Throwable => return false
    }
  }

  // returns the current hour as a string
  def getCurrentHour: String = {
    val today = Calendar.getInstance().getTime()
    val hourFormat = new SimpleDateFormat("hh")
    try {
      // returns something like "01" if i just return at this point, so cast it to
      // an int, then back to a string (or return the leading '0' if you prefer)
      val currentHour = Integer.parseInt(hourFormat.format(today))
      return "" + currentHour
    } catch {
      // TODO return Some/None/Whatever
      case _: Throwable => return "0"
    }
    return hourFormat.format(today)
  }
  
  // returns the current minute as a string
  def getCurrentMinute: String = {
    val today = Calendar.getInstance().getTime()
    val minuteFormat = new SimpleDateFormat("mm")
    // in this case, returning "01" is okay
    return minuteFormat.format(today)
  }
  
  // returns "AM" or "PM"
  def getAmOrPm: String = {
    val today = Calendar.getInstance().getTime()
    val amPmFormat = new SimpleDateFormat("a")
    return amPmFormat.format(today)
  }
}

