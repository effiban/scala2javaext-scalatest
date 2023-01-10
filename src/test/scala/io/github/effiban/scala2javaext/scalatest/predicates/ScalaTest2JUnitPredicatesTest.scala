package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class ScalaTest2JUnitPredicatesTest extends UnitTestSuite {

  private val scalaTest2JUnitPredicates = new ScalaTest2JUnitPredicates {}
  import scalaTest2JUnitPredicates._

  test("importerExcludedPredicate() should return ScalaTestImporterExcludedPredicate") {
    importerExcludedPredicate() shouldBe ScalaTestImporterExcludedPredicate
  }
}
