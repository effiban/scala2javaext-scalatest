package io.github.effiban.scala2javaext.scalatest.providers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class JUnitProvidersTest extends UnitTestSuite {
  private val junitProviders = new JUnitProviders {}
  import junitProviders._

  test("additionalImportersProvider() should return JUnitImportersProvider") {
    additionalImportersProvider() shouldBe JUnitImportersProvider
  }
}
