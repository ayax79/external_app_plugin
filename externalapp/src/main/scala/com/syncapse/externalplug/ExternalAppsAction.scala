package com.syncapse.externalplug

import com.jivesoftware.community.action.JiveActionSupport
import java.util.{List => JList}
import reflect.BeanProperty
import collection.JavaConversions
import org.slf4j.LoggerFactory

trait BaseAction {
  protected val SUCCESS = "success"
  protected val INPUT = "input"
  protected lazy val logger = LoggerFactory.getLogger(getClass.getName)
}

class ExternalAppsAction extends JiveActionSupport with BaseAction {
  
  @BeanProperty
  var externalApps: JList[ExternalApp] = null

  @BeanProperty
  var id: Int = -1

  override def execute = {
    externalApps = JavaConversions.asList(ExternalApp.loadAll)
    logger.info("apps size "+externalApps)
    SUCCESS
  }

  def delete = {
    ExternalApp.delete(id)
    SUCCESS
  }

}

class ExternalAppEditAction extends JiveActionSupport with BaseAction {
  @BeanProperty
  var externalApp: ExternalApp = null

  @BeanProperty
  var id: Int = -1

  @BeanProperty
  var name: String = null

  @BeanProperty
  var canvasUrl: String = null

  @BeanProperty
  var profileUrl: String = null

  override def input = {
    externalApp = ExternalApp.loadById(id)
    INPUT    
  }

  def update = {
    ExternalApp.update(id, name, canvasUrl, profileUrl)
    SUCCESS
  }

  def create = {
    ExternalApp.insert(name, canvasUrl, profileUrl)
    SUCCESS
  }
}