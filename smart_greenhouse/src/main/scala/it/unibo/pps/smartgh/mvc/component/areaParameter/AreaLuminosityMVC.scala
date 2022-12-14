package it.unibo.pps.smartgh.mvc.component.areaParameter

import it.unibo.pps.smartgh.controller.component.areaParameter.AreaLuminosityControllerModule.AreaLuminosityController
import it.unibo.pps.smartgh.controller.component.areaParameter.AreaParameterController.AreaParameterController
import it.unibo.pps.smartgh.controller.component.areaParameter.{AreaLuminosityControllerModule, AreaParameterController}
import it.unibo.pps.smartgh.model.area.AreaModelModule
import it.unibo.pps.smartgh.model.area.AreaModelModule.AreaModel
import it.unibo.pps.smartgh.view.component.areaParameter.AreaLuminosityViewModule.AreaLuminosityView
import it.unibo.pps.smartgh.view.component.areaParameter.AreaParameterView.AreaParameterView
import it.unibo.pps.smartgh.view.component.areaParameter.{AreaLuminosityViewModule, AreaParameterView}

/** A trait that represents the MVC component for the area luminosity section. */
trait AreaLuminosityMVC extends AreaModelModule.Interface
  with AreaLuminosityViewModule.Interface
  with AreaLuminosityControllerModule.Interface
  with AreaParameterMVC

/** Object that incapsulate the model view and controller module for area luminosity. */
object AreaLuminosityMVC:

  /** Create a new [[AreaLuminosityMVCImpl]].
    * @param areaModel
    *   the model of the area
    * @param updateStateMessage
    *   a function for update the area status and messages
    * @return
    *   a new instance of [[AreaLuminosityMVCI]].
    */
  def apply(areaModel: AreaModel, updateStateMessage: (String, Boolean) => Unit): AreaLuminosityMVC =
    AreaLuminosityMVCImpl(areaModel, updateStateMessage)

  private class AreaLuminosityMVCImpl(
      override val areaModel: AreaModel,
      private val updateStateMessage: (String, Boolean) => Unit
  ) extends AreaLuminosityMVC:

    override val parameterController: AreaParameterController = AreaLuminosityControllerImpl(updateStateMessage)
    override val parameterView: AreaParameterView = AreaLuminosityViewImpl()

    parameterController.initialize()
