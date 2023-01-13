package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalaTestTypeClassifier.isTestSuperclass
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Type, XtensionQuasiquoteType}

class ScalaTestTypeClassifierTest extends UnitTestSuite {

  private val Scenarios = Table(
    ("Type", "ExpectedResult"),
    (t"org.scalatest.featurespec.AnyFeatureSpec", true),
    (t"org.scalatest.featurespec.AsyncFeatureSpec", true),
    (t"org.scalatest.featurespec.FixtureAnyFeatureSpec", true),
    (t"org.scalatest.featurespec.FixtureAsyncFeatureSpec", true),
    (t"org.scalatest.flatspec.AnyFlatSpec", true),
    (t"org.scalatest.flatspec.AsyncFlatSpec", true),
    (t"org.scalatest.flatspec.FixtureAnyFlatSpec", true),
    (t"org.scalatest.flatspec.FixtureAsyncFlatSpec", true),
    (t"org.scalatest.freespec.AnyFreeSpec", true),
    (t"org.scalatest.freespec.AsyncFreeSpec", true),
    (t"org.scalatest.freespec.FixtureAnyFreeSpec", true),
    (t"org.scalatest.freespec.FixtureAsyncFreeSpec", true),
    (t"org.scalatest.freespec.PathAnyFreeSpec", true),
    (t"org.scalatest.funspec.AnyFunSpec", true),
    (t"org.scalatest.funspec.AsyncFunSpec", true),
    (t"org.scalatest.funspec.FixtureAnyFunSpec", true),
    (t"org.scalatest.funspec.FixtureAsyncFunSpec", true),
    (t"org.scalatest.funspec.PathAnyFunSpec", true),
    (t"org.scalatest.funsuite.AnyFunSuite", true),
    (t"org.scalatest.funsuite.AsyncFunSuite", true),
    (t"org.scalatest.funsuite.FixtureAnyFunSuite", true),
    (t"org.scalatest.propspec.AnyPropSpec", true),
    (t"org.scalatest.propspec.FixtureAnyPropSpec", true),
    (t"org.scalatest.refspec.RefSpec", true),
    (t"org.scalatest.wordspec.AnyWordSpec", true),
    (t"org.scalatest.wordspec.AsyncWordSpec", true),
    (t"org.scalatest.wordspec.FixtureAnyWordSpec", true),
    (t"org.scalatest.wordspec.FixtureAsyncWordSpec", true),

    (t"AnyFeatureSpec", true),
    (t"AsyncFeatureSpec", true),
    (t"FixtureAnyFeatureSpec", true),
    (t"FixtureAsyncFeatureSpec", true),
    (t"AnyFlatSpec", true),
    (t"AsyncFlatSpec", true),
    (t"FixtureAnyFlatSpec", true),
    (t"FixtureAsyncFlatSpec", true),
    (t"AnyFreeSpec", true),
    (t"AsyncFreeSpec", true),
    (t"FixtureAnyFreeSpec", true),
    (t"FixtureAsyncFreeSpec", true),
    (t"PathAnyFreeSpec", true),
    (t"AnyFunSpec", true),
    (t"AsyncFunSpec", true),
    (t"FixtureAnyFunSpec", true),
    (t"FixtureAsyncFunSpec", true),
    (t"PathAnyFunSpec", true),
    (t"AnyFunSuite", true),
    (t"AsyncFunSuite", true),
    (t"FixtureAnyFunSuite", true),
    (t"AnyPropSpec", true),
    (t"FixtureAnyPropSpec", true),
    (t"RefSpec", true),
    (t"AnyWordSpec", true),
    (t"AsyncWordSpec", true),
    (t"FixtureAnyWordSpec", true),
    (t"FixtureAsyncWordSpec", true),

    (t"AnyFeatureSpecLike", false),
    (t"AnyFunSpecLike", false),
    (t"blabla", false)
  )

  forAll(Scenarios) { case (tpe: Type, expectedResult: Boolean) =>
    test(s"$tpe should ${if (expectedResult) "be" else "not be"} classified as a test superclass") {
      isTestSuperclass(tpe) shouldBe expectedResult
    }
  }
}
