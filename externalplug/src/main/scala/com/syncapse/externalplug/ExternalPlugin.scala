package com.syncapse.externalplug

import com.jivesoftware.base.plugin.Plugin
import reflect.BeanProperty
import org.slf4j.LoggerFactory
import com.jivesoftware.base.UserManager
import org.springframework.beans.factory.annotation.Required
import javax.sql.DataSource

class ExternalPlugin extends Object with Plugin[ExternalPlugin] {
  var log = LoggerFactory.getLogger(getClass)

  @Required
  @BeanProperty
  var dataSource: DataSource = null

  def destroy = {
    log.info("The plugin is now being destroyed")
  }

  def init = {
    ExternalAppManager.dataSource = dataSource
  }

}