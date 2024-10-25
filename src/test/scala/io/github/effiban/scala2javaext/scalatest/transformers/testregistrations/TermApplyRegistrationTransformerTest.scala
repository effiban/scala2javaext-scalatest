package io.github.effiban.scala2javaext.scalatest.transformers.testregistrations

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.eqTo

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class TermApplyRegistrationTransformerTest extends UnitTestSuite {

  private val registrationWordClassifier = mock[TestRegistrationWordClassifier]
  private val junitTestMethodGenerator = mock[JUnitTestMethodGenerator]

  private val transformer = new TermApplyRegistrationTransformer(registrationWordClassifier, junitTestMethodGenerator)

  test("transform() when valid and not ignored, and one argument - should return equivalent JUnit '@Test' method") {
    val registration =
      q"""
      super.test("check me") {
        doCheck()
      }
      """

    val registrator = q"test"
    val args = List(Lit.String("check me"))
    val body =
      q"""
      {
        doCheck()
      }
      """

    val junitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      def checkMe(): scala.Unit = doCheck()
      """

    when(registrationWordClassifier.isTermApplyRegistrator(eqTree(registrator))).thenReturn(true)
    when(registrationWordClassifier.isIgnore(eqTree(registrator))).thenReturn(false)
    when(junitTestMethodGenerator.generate(eqTreeList(args), disabled = eqTo(false))(eqTree(body))).thenReturn(Some(junitTestMethod))

    transformer.transform(registration).value.structure shouldBe junitTestMethod.structure
  }

  test("transform() when valid and ignored, and one argument - should return equivalent JUnit '@Test' method") {
    val registration =
      q"""
      super.ignore("check me") {
        doCheck()
      }
      """

    val registrator = q"ignore"
    val args = List(Lit.String("check me"))
    val body =
      q"""
      {
        doCheck()
      }
      """

    val junitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      @org.junit.jupiter.api.Disabled
      def checkMe(): scala.Unit = doCheck()
      """

    when(registrationWordClassifier.isTermApplyRegistrator(eqTree(registrator))).thenReturn(true)
    when(registrationWordClassifier.isIgnore(eqTree(registrator))).thenReturn(true)
    when(junitTestMethodGenerator.generate(eqTreeList(args), disabled = eqTo(true))(eqTree(body))).thenReturn(Some(junitTestMethod))

    transformer.transform(registration).value.structure shouldBe junitTestMethod.structure
  }

  test("transform() when valid with two arguments should return equivalent JUnit '@Test' method") {
    val registration =
      q"""
      super.test("check me", "tag") {
        doCheck()
      }
      """

    val registrator = q"test"
    val args = List(Lit.String("check me"), Lit.String("tag"))
    val body =
      q"""
      {
      doCheck()
      }
      """

    val junitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      @org.junit.jupiter.api.Tag("tag")
      def checkMe(): scala.Unit = doCheck()
      """

    when(registrationWordClassifier.isTermApplyRegistrator(eqTree(registrator))).thenReturn(true)
    when(registrationWordClassifier.isIgnore(eqTree(registrator))).thenReturn(false)
    when(junitTestMethodGenerator.generate(eqTreeList(args), disabled = eqTo(false))(eqTree(body))).thenReturn(Some(junitTestMethod))

    transformer.transform(registration).value.structure shouldBe junitTestMethod.structure
  }

  test("transform() when the registration term is unqualified (a Term.Name)- should return None") {
    val registration = q"""test("check me")(body1, body2)"""

    transformer.transform(registration) shouldBe None
  }

  test("transform() when has two bodies should return None") {
    val registration = q"""super.test("check me")(body1, body2)"""

    transformer.transform(registration) shouldBe None
  }

  test("transform() when has no body should return None") {
    val registration = q"""super.test("check me")"""

    transformer.transform(registration) shouldBe None
  }

  test("transform() when registration word is invalid should return None") {
    val registration =
      q"""
      super.bla("check me") {
        doCheck()
      }
      """

    val registrator = q"bla"

    when(registrationWordClassifier.isTermApplyRegistrator(eqTree(registrator))).thenReturn(false)

    transformer.transform(registration) shouldBe None
  }

  test("transform() when nested test name is missing should return None") {
    val registration =
      q"""
      super.test() {
        doCheck()
      }
      """

    transformer.transform(registration) shouldBe None
  }
}
