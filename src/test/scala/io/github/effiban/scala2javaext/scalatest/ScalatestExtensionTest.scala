package io.github.effiban.scala2javaext.scalatest

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class ScalatestExtensionTest extends UnitTestSuite {

  private val scalatestExtension = new ScalatestExtension
  import scalatestExtension._

  test("shouldBeAppliedIfContains() for 'org.scalatest' should return true") {
    shouldBeAppliedIfContains(q"org.scalatest") shouldBe true
  }

  test("shouldBeAppliedIfContains() for a non-scalatest qualified name should return false") {
    shouldBeAppliedIfContains(q"org.othertest") shouldBe false
  }
}
