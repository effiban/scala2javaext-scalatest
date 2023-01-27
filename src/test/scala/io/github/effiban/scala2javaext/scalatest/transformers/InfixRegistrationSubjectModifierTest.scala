package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.InfixRegistrationSubjectModifier.prepend

import scala.meta.XtensionQuasiquoteTerm

class InfixRegistrationSubjectModifierTest extends UnitTestSuite {

  test("prepend() when has a subject string which is not nested, should prepend the prefix") {
    val registration =
      q"""
      "return three items" in {
         verifyNumItems()
      }
      """

    val prefix = q""""should""""

    val modifiedRegistration =
      q"""
      "should return three items" in {
       verifyNumItems()
      }
      """

    prepend(registration, prefix).structure shouldBe modifiedRegistration.structure
  }

  test("prepend() when has a subject string which is nested, should prepend the prefix") {
    val registration =
      q"""
      "return three items" taggedAs(MyTag) in {
        verifyNumItems()
      }
      """

    val prefix = q""""should""""

    val modifiedRegistration =
      q"""
      "should return three items" taggedAs(MyTag) in {
        verifyNumItems()
      }
      """

    prepend(registration, prefix).structure shouldBe modifiedRegistration.structure
  }

  test("prepend() when has a subject which is a non-literal word, should leave it unchanged") {
    val registration =
      q"""
      it should {
        verifyNumItems()
      }
      """

    val prefix = q""""when""""

    prepend(registration, prefix).structure shouldBe registration.structure
  }
}
