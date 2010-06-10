package com.syncapse.externalplug

import javax.sql.DataSource
import java.sql.{ResultSet}

object ExternalApp extends DataAccess {

  def loadAll: List[ExternalApp] = {
    withStatement("SELECT * FROM synExternalApp") {
      ps =>
        process(ps, mapper)
    }
  }

  def insert(ea: ExternalApp) = {
    withStatement("INSERT INTO synExternalApp (name, key, canvasUrl, profileUrl) VALUES (?, ?, ?, ?)") {
      ps =>
        ps.setString(1, ea.name)
        ps.setLong(2, ea.key)
        ps.setString(3, ea.canvasUrl)
        ps.setString(4, ea.profileUrl)
        ps.executeUpdate
    }
  }

  def loadById(id: Long): ExternalApp = {
    withStatement("SELECT * FROM synExternalApp WHERE id = ?") {
      ps =>
        ps.setLong(1, id)
        process(ps, mapper).head
    }
  }

  def update(ea: ExternalApp) = {
    val ea2 = loadById(ea.id.asInstanceOf[Long])
    val name = if (ea.name != ea2.name) ea.name else ea2.name
    val canvasUrl = if (ea.canvasUrl != ea2.canvasUrl) ea.canvasUrl else ea2.canvasUrl
    val profileUrl = if (ea.profileUrl != ea2.profileUrl) ea.profileUrl else ea2.profileUrl

    ExternalApp.withStatement("UPDATE synExternalApp SET name = ?, canvasUrl = ?, profileUrl = ? WHERE id = ?") {
      ps =>
        ps.setString(1, name)
        ps.setString(2, canvasUrl)
        ps.setString(3, profileUrl)
        ps.setInt(4, ea2.id.asInstanceOf[Int])
        ps.executeUpdate
    }
  }

  def delete(id: Long) = {
    withStatement("DELETE FROM synExternalApp WHERE id = ?") {
      ps =>
        ps.setLong(1, id)
        ps.executeUpdate
    }
  }

  protected def mapper(rs: ResultSet) {
    new ExternalApp(
      rs.getObject("id").asInstanceOf[Option[Long]],
      rs.getString("name"),
      rs.getInt("key").asInstanceOf[Int],
      rs.getString("canvasUrl"),
      rs.getString("profileUrl"))
  }

}

case class ExternalApp(id: Option[Long],
                       name: String,
                       key: Long,
                       canvasUrl: String,
                       profileUrl: String) {
}

