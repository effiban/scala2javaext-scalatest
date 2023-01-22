package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class ScalatestPredicatesTest extends UnitTestSuite {

  private val scalatestPredicates = new ScalatestPredicates {}
  import scalatestPredicates._

  test("importerExcludedPredicate() should return ScalatestImporterExcludedPredicate") {
    importerExcludedPredicate() shouldBe ScalatestImporterExcludedPredicate
  }

  test("templateInitExcludedPredicate() should return ScalatestTemplateInitExcludedPredicate") {
    templateInitExcludedPredicate() shouldBe ScalatestTemplateInitExcludedPredicate
  }
}
