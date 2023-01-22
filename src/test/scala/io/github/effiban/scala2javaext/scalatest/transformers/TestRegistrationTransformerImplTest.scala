package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class TestRegistrationTransformerImplTest extends UnitTestSuite {

  private val identifierNormalizer = mock[IdentifierNormalizer]

  private val testRegistrationTransformer = new TestRegistrationTransformerImpl(identifierNormalizer)


  test("transform when valid and has no tags") {
    val testName = "check me"
    val args = List(Lit.String(testName))
    val body = q"doCheck()"

    val expectedJUnitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      def checkMe(): Unit = doCheck()
      """

    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    testRegistrationTransformer.transform(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when valid and has tags") {
    val testName = "check me"
    val args = List(Lit.String(testName), q"""Tag("tag1")""", q"""Tag("tag2")""")
    val body = q"doCheck()"

    val expectedJUnitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      @Tag("tag1")
      @Tag("tag2")
      def checkMe(): Unit = doCheck()
      """
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    testRegistrationTransformer.transform(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when test name is not a literal string, should return None") {
    testRegistrationTransformer.transform(List(q"generateName()"))(q"check()") shouldBe None
  }
}
