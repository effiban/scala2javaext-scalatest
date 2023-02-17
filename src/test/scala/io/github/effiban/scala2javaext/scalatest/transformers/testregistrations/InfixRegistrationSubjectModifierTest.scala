package io.github.effiban.scala2javaext.scalatest.transformers.testregistrations

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.testregistrations.InfixRegistrationSubjectModifier.prepend

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class InfixRegistrationSubjectModifierTest extends UnitTestSuite {

  test("prepend() when has a subject string which is not empty and not nested, should prepend the prefix") {
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

  test("prepend() when has a subject string which is not empty and nested, should prepend the prefix") {
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

  test("prepend() when the prefix is empty, should leave it unchanged") {
    val registration =
      q"""
      "should return three items" in {
         verifyNumItems()
      }
      """

    val prefix = Lit.String("")

    prepend(registration, prefix).structure shouldBe registration.structure
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
