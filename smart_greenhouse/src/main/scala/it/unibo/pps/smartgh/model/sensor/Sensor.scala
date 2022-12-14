package it.unibo.pps.smartgh.model.sensor

import it.unibo.pps.smartgh.model.area.AreaComponentsState
import it.unibo.pps.smartgh.model.area.AreaComponentsState.AreaComponentsStateImpl
import it.unibo.pps.smartgh.model.time.Timer
import monix.execution.Ack
import monix.execution.Ack.Continue
import monix.execution.Scheduler.Implicits.global
import monix.reactive.subjects.ConcurrentSubject
import monix.reactive.{MulticastStrategy, Observable}

import scala.concurrent.Future

/** This trait represents a sensor capable of detecting a specific environmental parameter within the greenhouse area.
  */
trait Sensor:
  /** Method that sets the observer of the external environmental parameter that can influence sensor readings.
    * @param observableEnvironment
    *   the [[Observable]] representing the environment parameter.
    */
  def setObserverEnvironmentValue(observableEnvironment: Observable[Double]): Unit =
    observableEnvironment.subscribe(onNextEnvironmentValue(), (ex: Throwable) => ex.printStackTrace(), () => {})

  /** Method that sets the observer of the actions that can be done by the user to change the value detected by the
    * sensor.
    * @param observableActionArea
    *   the [[Observable]] representing the actions performed by the user.
    */
  def setObserverActionsArea(observableActionArea: Observable[AreaComponentsStateImpl]): Unit =
    observableActionArea.subscribe(onNextAction(), (ex: Throwable) => ex.printStackTrace(), () => {})

  /** Method that deals with recording the callback that need to be performed when a new value is detected.
    * @param onNext
    *   the function that needs to be called when a new element has been generated.
    * @param onError
    *   the function that needs to be called when an error occur, during the elaboration of the data.
    * @param onComplete
    *   the function that needs to be called when the emission of data is completed.
    */
  def registerValueCallback(onNext: Double => Future[Ack], onError: Throwable => Unit, onComplete: () => Unit): Unit

  /** Method that specifies which operations need to be performed when the user takes a new action.
    *
    * @return
    *   a function that takes the action performed by the user and returns an [[Ack]] whether or not it is possible to
    *   continue with the operations.
    */
  def onNextAction(): AreaComponentsStateImpl => Future[Ack]

  /** Method that specifies which operations need to be performed when a new value for the parameter detected by the
    * sensor is emitted by the environment.
    * @return
    *   a function that takes the emitted environment parameter and returns an [[Ack]] whether or not it is possible to
    *   continue with operations.
    */
  def onNextEnvironmentValue(): Double => Future[Ack]

  /** Method that can be used to obtain the current value detected by the sensor.
    * @return
    *   the value detected by the sensor at the moment of the call.
    */
  def getCurrentValue: Double

/** This trait represents a sensor of emitting periodically detected values. */
trait SensorWithTimer extends Sensor:

  /** Method that need to be called to associate the sensor to the simulation timer, this method can be called only once
    * that the timer has started.
    * @param verifyTimePass
    *   a function for verify the passed time
    */
  protected def registerTimerCallback(verifyTimePass: String => Boolean): Unit

/** Abstract class that enclose the common aspects of the [[Sensor]] implementations.
  * @param areaComponentsState
  *   represents the current state of the components of the area.
  */
abstract class AbstractSensor(var areaComponentsState: AreaComponentsStateImpl) extends Sensor:

  protected var currentEnvironmentValue: Double = _
  protected val subject: ConcurrentSubject[Double, Double] = ConcurrentSubject[Double](MulticastStrategy.publish)
  protected var currentValue: Double = _

  override def getCurrentValue: Double = currentValue

  protected def computeNextSensorValue(): Unit

  override def registerValueCallback(
      onNext: Double => Future[Ack],
      onError: Throwable => Unit,
      onComplete: () => Unit
  ): Unit = subject.subscribe(onNext, onError, onComplete)

  override def onNextEnvironmentValue(): (newEnvironmentValue: Double) => Future[Ack] = envVal =>
    currentEnvironmentValue = envVal
    computeNextSensorValue()
    Continue

  override def onNextAction(): AreaComponentsStateImpl => Future[Ack] = currentAreaComponentsState =>
    areaComponentsState = currentAreaComponentsState
    computeNextSensorValue()
    Continue

/** Abstract class that encloses the common aspects of the [[SensorWithTimer]] implementations.
  * @param areaComponentsState
  *   represents the current state of the components of the area.
  * @param addTimerCallback
  *   the callback for the timer.
  */
abstract class AbstractSensorWithTimer(
    areaComponentsState: AreaComponentsStateImpl,
    addTimerCallback: (f: String => Unit) => Unit
) extends AbstractSensor(areaComponentsState)
    with SensorWithTimer:

  override protected def registerTimerCallback(verifyTimePass: String => Boolean): Unit =
    addTimerCallback((s: String) => if verifyTimePass(s) then computeNextSensorValue())

/** Enum that indicate the two possible state that can have a sensor. */
enum SensorStatus:
  case ALARM, NORMAL
export SensorStatus.*
