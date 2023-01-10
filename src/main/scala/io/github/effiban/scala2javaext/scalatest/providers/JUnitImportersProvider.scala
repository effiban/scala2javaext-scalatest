package io.github.effiban.scala2javaext.scalatest.providers

import io.github.effiban.scala2java.spi.providers.AdditionalImportersProvider

import scala.meta.{Importer, XtensionQuasiquoteImporter}

object JUnitImportersProvider extends AdditionalImportersProvider {
  override def provide(): List[Importer] = List(
    importer"org.junit.jupiter.api._",
    importer"org.junit.jupiter.api.Assertions._"
  )
}
