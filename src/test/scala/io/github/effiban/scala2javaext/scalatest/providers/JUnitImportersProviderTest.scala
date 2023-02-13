package io.github.effiban.scala2javaext.scalatest.providers

import io.github.effiban.scala2javaext.scalatest.providers.JUnitImportersProvider.provide
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteImporter

class JUnitImportersProviderTest extends UnitTestSuite {

  test("provide() should return wildcard imports of JUnit 5 and Hamcrest") {
    val expectedImporters = List(
      importer"org.junit.jupiter.api._",
      importer"org.junit.jupiter.api.Assertions._",
      importer"org.hamcrest.Matchers._",
      importer"org.hamcrest.MatcherAssert._"
    )
    provide().structure shouldBe expectedImporters.structure
  }
}
