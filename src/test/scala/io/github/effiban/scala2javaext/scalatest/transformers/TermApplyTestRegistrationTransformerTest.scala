package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class TermApplyTestRegistrationTransformerTest extends UnitTestSuite {

  private val scalatestTermNameClassifier = mock[ScalatestTermNameClassifier]
  private val junitTestMethodGenerator = mock[JUnitTestMethodGenerator]

  private val transformer = new TermApplyTestRegistrationTransformer(scalatestTermNameClassifier, junitTestMethodGenerator)

  test("transform() when valid with one argument should return equivalent JUnit '@Test' method") {
    val testRegistration =
      q"""
      test("check me") {
        doCheck()
      }
      """

    val registrationWord = q"test"
    val args = List(Lit.String("check me"))
    val body =
      q"""
      {
        doCheck()
      }
      """

    val junitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      def checkMe(): Unit = doCheck()
      """

    when(scalatestTermNameClassifier.isTestRegistrationWord(eqTree(registrationWord))).thenReturn(true)
    when(junitTestMethodGenerator.generate(eqTreeList(args))(eqTree(body))).thenReturn(Some(junitTestMethod))

    transformer.transform(testRegistration).value.structure shouldBe junitTestMethod.structure
  }

  test("transform() when valid with two arguments should return equivalent JUnit '@Test' method") {
    val testRegistration =
      q"""
      test("check me", "tag") {
        doCheck()
      }
      """

    val registrationWord = q"test"
    val args = List(Lit.String("check me"), Lit.String("tag"))
    val body =
      q"""
      {
      doCheck()
      }
      """

    val junitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      @Tag("tag")
      def checkMe(): Unit = doCheck()
      """

    when(scalatestTermNameClassifier.isTestRegistrationWord(eqTree(registrationWord))).thenReturn(true)
    when(junitTestMethodGenerator.generate(eqTreeList(args))(eqTree(body))).thenReturn(Some(junitTestMethod))

    transformer.transform(testRegistration).value.structure shouldBe junitTestMethod.structure
  }

  test("transform() when the registration term is not a single word should return None") {
    val testRegistration = q"""some.test("check me")(body1, body2)"""

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when has two bodies should return None") {
    val testRegistration = q"""test("check me")(body1, body2)"""

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when has no body should return None") {
    val testRegistration = q"""test("check me")"""

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when registration word is invalid should return None") {
    val testRegistration =
      q"""
      bla("check me") {
        doCheck()
      }
      """

    val registrationWord = q"bla"

    when(scalatestTermNameClassifier.isTestRegistrationWord(eqTree(registrationWord))).thenReturn(false)

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when nested test name is missing should return None") {
    val testRegistration =
      q"""
      test() {
        doCheck()
      }
      """

    transformer.transform(testRegistration) shouldBe None
  }
}
