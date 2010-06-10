package com.syncapse.externalplug

import com.jivesoftware.community.action.JiveActionSupport
import java.util.{List => JList}
import reflect.BeanProperty
import collection.JavaConversions

trait BaseAction {
  protected val success = "success"
}

class ExternalAppsAction extends JiveActionSupport with BaseAction {
  @BeanProperty
  var apps: JList[ExternalApp] = null


  override def execute = {
    apps = JavaConversions.asList(ExternalApp.loadAll)
    success
  }

  def delete = {
    success
  }

}

class ExternalAppEditAction extends JiveActionSupport with BaseAction {
  @BeanProperty
  var externalApp: ExternalApp = null

  var id: Int = -1

  override def execute = {
    externalApp = ExternalApp.loadById(id)
    success
  }
}