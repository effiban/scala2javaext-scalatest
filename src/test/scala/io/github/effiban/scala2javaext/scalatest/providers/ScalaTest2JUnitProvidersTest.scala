package io.github.effiban.scala2javaext.scalatest.providers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class ScalaTest2JUnitProvidersTest extends UnitTestSuite {
  private val scalaTest2JUnitProviders = new ScalaTest2JUnitProviders {}
  import scalaTest2JUnitProviders._

  test("additionalImportersProvider() should return JUnitImportersProvider") {
    additionalImportersProvider() shouldBe JUnitImportersProvider
  }
}
