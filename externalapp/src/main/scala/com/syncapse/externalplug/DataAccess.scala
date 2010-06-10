package com.syncapse.externalplug

import collection.mutable.HashSet
import java.sql.{Connection, PreparedStatement, ResultSet}
import javax.sql.DataSource


trait DataAccess {

  var dataSource: DataSource = null
  
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

  protected def process[T, R](ps: PreparedStatement, f: ResultSet => T): List[R] = {
    val results = new HashSet[R]
    val rs = ps.executeQuery
    while (rs.next()) {
      results += f(rs).asInstanceOf[R]
    }
    results.toList
  }

}