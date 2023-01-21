package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class TestInvocationTransformerImplTest extends UnitTestSuite {

  private val identifierNormalizer = mock[IdentifierNormalizer]

  private val testInvocationTransformer = new TestInvocationTransformerImpl(identifierNormalizer)


  test("transform when valid and has no tags") {
    val testName = "check me"

    val expectedJUnitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      def checkMe(): Unit = doCheck()
      """

    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    testInvocationTransformer.transform(List(Lit.String(testName)))(q"doCheck()").value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when valid and has tags") {
    val testName = "check me"

    val expectedJUnitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      @Tag("tag1")
      @Tag("tag2")
      def checkMe(): Unit = doCheck()
      """

    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    testInvocationTransformer.transform(List(Lit.String(testName), q"""Tag("tag1")""", q"""Tag("tag2")"""))(q"doCheck()").value.structure shouldBe
      expectedJUnitTestMethod.structure
  }

  test("transform when test name is not a literal string, should return None") {
    testInvocationTransformer.transform(List(q"generateName()"))(q"check()") shouldBe None
  }
}
