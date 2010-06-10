package com.syncapse.externalplug

import javax.sql.DataSource
import java.sql.{ResultSet}
import com.jivesoftware.community.JiveObject
import util.Random

object ExternalApp extends DataAccess {
  val objectType: Int = Integer.MAX_VALUE - 251132
  protected val random: Random = new Random

  def loadAll: List[ExternalApp] = {
    withStatement("SELECT * FROM synExternalApp") {
      ps =>
        process(ps, mapper)
    }
  }

  def insert(name: String, canvasUrl: String, profileUrl: String) = {
    val key = random.nextInt
    val id = ExternalApp.nextId

    withStatement("INSERT INTO synExternalApp (id, name, key, canvasUrl, profileUrl) VALUES (?, ?, ?, ?, ?)") {
      ps =>
        ps.setLong(1, id)
        ps.setString(2, name)
        ps.setLong(3, key)
        ps.setString(4, canvasUrl)
        ps.setString(5, profileUrl)
        ps.executeUpdate
    }

    ExternalApp(id, name, key, canvasUrl, profileUrl)
  }

  def loadById(id: Long): ExternalApp = {
    withStatement("SELECT * FROM synExternalApp WHERE id = ?") {
      ps =>
        ps.setLong(1, id)
        process(ps, mapper).head
    }
  }

  def update(id: Long, name: String, canvasUrl: String, profileUrl: String) = {
    var dirty = false
    val ea2 = loadById(id)
    
    if (name != ea2.name) {
      dirty = true
    }

    if (canvasUrl != ea2.canvasUrl) {
      dirty = true
    }

    if (profileUrl != ea2.profileUrl) {
      dirty = true
    } 

    if (dirty) {
      withStatement("UPDATE synExternalApp SET name = ?, canvasUrl = ?, profileUrl = ? WHERE id = ?") {
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
    withStatement("DELETE FROM synExternalApp WHERE id = ?") {
      ps =>
        ps.setLong(1, id)
        ps.executeUpdate
    }
  }

  protected def mapper(rs: ResultSet) {
    new ExternalApp(
      rs.getObject("id").asInstanceOf[Long],
      rs.getString("name"),
      rs.getInt("key").asInstanceOf[Int],
      rs.getString("canvasUrl"),
      rs.getString("profileUrl"))
  }

}

case class ExternalApp(id: Long,
                       name: String,
                       key: Long,
                       canvasUrl: String,
                       profileUrl: String) {
}

