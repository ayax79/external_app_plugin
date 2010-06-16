package com.syncapse.externalplug

import java.sql.{ResultSet}
import util.Random
import java.util.UUID
import org.apache.commons.codec.binary.Base64.encodeBase64
import org.apache.commons.codec.digest.DigestUtils

case class ExternalApp(id: Long,
                       name: String,
                       key: String,
                       canvasUrl: String,
                       profileUrl: String) {
}


object ExternalApp extends DataAccess {
  val objectType: Int = Integer.MAX_VALUE - 251132

  def loadAll: List[ExternalApp] = {
    withStatement("SELECT * FROM synExternalApp") {
      ps =>
        process(ps, mapper)
    }
  }

  def insert(name: String, canvasUrl: String, profileUrl: String) = {
    val key = generateKey
    val id = ExternalApp.nextId

    withStatement("INSERT INTO synExternalApp (appId, name, key, canvasUrl, profileUrl) VALUES (?, ?, ?, ?, ?)") {
      ps =>
        ps.setLong(1, id)
        ps.setString(2, name)
        ps.setString(3, key)
        ps.setString(4, canvasUrl)
        ps.setString(5, profileUrl)
        ps.executeUpdate
    }

    ExternalApp(id, name, key, canvasUrl, profileUrl)
  }

  def loadById(id: Long): ExternalApp = {
    withStatement("SELECT * FROM synExternalApp WHERE appId = ?") {
      ps =>
        ps.setLong(1, id)
        process(ps, mapper).head
    }
  }

  def update(id: Long, name: String, canvasUrl: String, profileUrl: String) = {
    val ea2 = loadById(id)
    val dirty = name != ea2.name || canvasUrl != ea2.canvasUrl || profileUrl != ea2.profileUrl

    if (dirty) {
      withStatement("UPDATE synExternalApp SET name = ?, canvasUrl = ?, profileUrl = ? WHERE appId = ?") {
        ps =>
          ps.setString(1, name)
          ps.setString(2, canvasUrl)
          ps.setString(3, profileUrl)
          ps.setLong(4, ea2.id)
          ps.executeUpdate
      }
    }
    ExternalApp(ea2.id, name, ea2.key, canvasUrl, profileUrl)
  }

  def delete(id: Long) = {
    withStatement("DELETE FROM synExternalApp WHERE appId = ?") {
      ps =>
        ps.setLong(1, id)
        ps.executeUpdate
    }
  }

  protected def mapper(rs: ResultSet) = {
    new ExternalApp(
      rs.getObject("appId").asInstanceOf[Long],
      rs.getString("name"),
      rs.getString("key"),
      rs.getString("canvasUrl"),
      rs.getString("profileUrl"))
  }

  protected def generateKey(): String = {
    val rawGuid = UUID.randomUUID.toString + System.currentTimeMillis
    DigestUtils.shaHex(rawGuid)
  }

}


