package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Importer, XtensionQuasiquoteImporter}

class ScalaTestImporterExcludedPredicateTest extends UnitTestSuite {

  private val ImporterScenarios = Table(
    ("Importer", "ExpectedExcluded"),
    (importer"org.scalatest._", true),
    (importer"org.scalatest.BeforeAndAfterEach", true),
    (importer"org.scalatest.{OneInstancePerTest, OptionValues}", true),
    (importer"org.scalatest.funsuite.AnyFunSuite", true),
    (importer"org.scalatest.matchers.should.Matchers", true),
    (importer"aaa.scalatest.bbb", false),
    (importer"bla.bla", false),
  )

  forAll(ImporterScenarios) { case (importer: Importer, expectedExcluded: Boolean) =>
    test(s"$importer should ${if (expectedExcluded) "be" else "not be"} excluded") {
      ScalaTestImporterExcludedPredicate(importer) shouldBe expectedExcluded
    }
  }
}
