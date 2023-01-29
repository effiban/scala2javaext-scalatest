package io.github.effiban.scala2javaext.scalatest.entities

import scala.meta.Lit

case class SpecInfo(name: Lit.String, ignored: Boolean = false)
