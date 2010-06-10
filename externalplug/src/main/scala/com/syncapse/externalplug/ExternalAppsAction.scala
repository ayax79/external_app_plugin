package com.syncapse.externalplug

import com.jivesoftware.community.action.JiveActionSupport
import java.util.{List => JList}
import reflect.BeanProperty
import collection.JavaConversions


class ExternalAppsAction extends JiveActionSupport {

  @BeanProperty
  var apps : JList[ExternalApp] = null

  override def execute = {
    apps = JavaConversions.asList(ExternalAppManager.loadAll)
    "success"
  }

  def delete = {
    "success"
  }

}

class ExternalAppEditAction extends JiveActionSupport {

  @BeanProperty
  var externalApp : ExternalApp = null

  override def execute = {
    ""
  }
}