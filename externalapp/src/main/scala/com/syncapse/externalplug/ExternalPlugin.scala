package com.syncapse.externalplug

import com.jivesoftware.base.plugin.Plugin
import reflect.BeanProperty
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Required
import javax.sql.DataSource
import com.jivesoftware.base.database.dao.SequenceDAO
import com.jivesoftware.base.database.dao.impl.SequenceProviderImpl

class ExternalPlugin extends Object with Plugin[ExternalPlugin] {
  
  private val log = LoggerFactory.getLogger(getClass)

  @BeanProperty
  var dataSource: DataSource = null

  @BeanProperty
  var sequenceDao: SequenceDAO = null


  def destroy = {
    log.info("The plugin is now being destroyed")
  }

  def init = {
    ExternalApp.dataSource = dataSource
    ExternalApp.sequence = new SequenceProviderImpl(ExternalApp.objectType, sequenceDao)
  }

}