package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.IdentifierNormalizer

import scala.meta.{Lit, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

class JUnitTestMethodGeneratorImplTest extends UnitTestSuite {

  private val junitAnnotationGenerator = mock[JUnitAnnotationGenerator]
  private val identifierNormalizer = mock[IdentifierNormalizer]

  private val junitTestMethodGenerator = new JUnitTestMethodGeneratorImpl(junitAnnotationGenerator, identifierNormalizer)


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

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me")).thenReturn(mod"""@DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(Nil)).thenReturn(Nil)
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when valid and has tags") {
    val testName = "check me"
    val tagNames = List("tag1", "tag2")
    val tags = List(q"""Tag("tag1")""", q"""Tag("tag2")""")
    val args = List(Lit.String(testName)) ++ tags
    val body = q"doCheck()"

    val expectedJUnitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      @Tag("tag1")
      @Tag("tag2")
      def checkMe(): Unit = doCheck()
      """

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me")).thenReturn(mod"""@DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(tagNames)).thenReturn(List(mod"""@Tag("tag1")""", mod"""@Tag("tag2")"""))
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when test name is not a literal string, should return None") {
    junitTestMethodGenerator.generate(List(q"generateName()"))(q"check()") shouldBe None
  }
}
