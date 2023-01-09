package io.github.effiban.scala2javaext.scalatest

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class ScalaTest2JUnitExtensionTest extends UnitTestSuite {

  private val scalatest2JunitExtension = new ScalaTest2JUnitExtension
  import scalatest2JunitExtension._

  test("shouldBeAppliedIfContains() for 'org.scalatest' should return true") {
    shouldBeAppliedIfContains(q"org.scalatest") shouldBe true
  }

  test("shouldBeAppliedIfContains() for a non-scalatest qualified name should return false") {
    shouldBeAppliedIfContains(q"org.othertest") shouldBe false
  }
}
