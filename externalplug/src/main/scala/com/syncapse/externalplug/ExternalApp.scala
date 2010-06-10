package com.syncapse.externalplug


import javax.sql.DataSource
import org.slf4j.LoggerFactory
import java.sql.{Connection, PreparedStatement, ResultSet}
import collection.mutable.HashSet

object ExternalApp {
  val logger = LoggerFactory.getLogger(this.getClass)

  var dataSource: DataSource = null

  def loadAll : List[ExternalApp] = {
    withStatement("SELECT * FROM synExternalApp") { ps =>
      process(ps, mapper)
    }
  }

  def insert(ea: ExternalApp) = {
    withStatement("INSERT INTO synExternalApp (name, key, canvasUrl, profileUrl) VALUES (?, ?, ?, ?)") { ps =>
      ps.setString(1, ea.name)
      ps.setLong(2, ea.key)
      ps.setString(3, ea.canvasUrl)
      ps.setString(4, ea.profileUrl)
      ps.executeUpdate
    }
  }

  def update(ea: ExternalApp) = {
    withStatement("UPDATE synExternalApp SET name = ?, canvasUrl = ?, profileUrl = ? WHERE id = ?") { ps =>
      ps.setString(1, ea.name)
      ps.setString(2, ea.canvasUrl)
      ps.setString(3, ea.profileUrl)
      ps.setInt(4, ea.id.asInstanceOf[Int])
      ps.executeUpdate
    }
  }

  def loadById(id: Long) = {
    withStatement("SELECT * FROM synExternalApp WHERE id = ?") { ps =>
      ps.setLong(1, id)
      process(ps, mapper).head
    }
  }

  protected def mapper(rs: ResultSet) {
    new ExternalApp(
      rs.getObject("id").asInstanceOf[Option[Long]],
      rs.getString("name"),
      rs.getInt("key").asInstanceOf[Int],
      rs.getString("canvasUrl"),
      rs.getString("profileUrl")
      )
  }

  protected def withConnection[T](f: Connection => T): T = {
    val conn = dataSource.getConnection
    try {f(conn)} finally conn.close
  }

  protected def withStatement[T](sql: String)(f: PreparedStatement => T): T = {
    withConnection {
      conn =>
        val ps = conn.prepareStatement(sql)
        try {f(ps)} finally ps.close
    }
  }

  protected def process[T,R](ps: PreparedStatement, f: ResultSet => T): List[R] = {
    val results = new HashSet[R]
    val rs = ps.executeQuery
    while (rs.next()) {
      results += f(rs).asInstanceOf[R]
    }
    results.toList
  }


}

case class ExternalApp(id: Option[Long],
                       name: String,
                       key: Long,
                       canvasUrl: String,
                       profileUrl: String) {
}

