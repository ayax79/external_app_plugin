package com.syncapse.externalplug

import com.jivesoftware.community.action.JiveActionSupport
import java.util.{List => JList}
import reflect.BeanProperty
import collection.JavaConversions

trait BaseAction {
  protected val SUCCESS = "success"
  protected val INPUT = "input"
}

class ExternalAppsAction extends JiveActionSupport with BaseAction {
  
  @BeanProperty
  var apps: JList[ExternalApp] = null

  @BeanProperty
  var id: Int = -1

  override def execute = {
    apps = JavaConversions.asList(ExternalApp.loadAll)
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

  var id: Int = -1


  override def input = {
    externalApp = ExternalApp.loadById(id)
    INPUT    
  }

  override def execute = {
    SUCCESS
  }
}