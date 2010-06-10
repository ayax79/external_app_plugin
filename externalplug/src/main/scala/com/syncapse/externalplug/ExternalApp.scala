package com.syncapse.externalplug

case class ExternalApp(id: Option[Long],
                       name: String,
                       key: Long,
                       canvasUrl: String,
                       profileUrl: String) {
}

