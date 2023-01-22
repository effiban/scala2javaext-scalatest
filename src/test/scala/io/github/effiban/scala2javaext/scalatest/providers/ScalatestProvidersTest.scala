package io.github.effiban.scala2javaext.scalatest.providers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class ScalatestProvidersTest extends UnitTestSuite {
  private val scalatestProviders = new ScalatestProviders {}
  import scalatestProviders._

  test("additionalImportersProvider() should return JUnitImportersProvider") {
    additionalImportersProvider() shouldBe JUnitImportersProvider
  }
}
