package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.normalizers.IdentifierNormalizer
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

class JUnitTestMethodGeneratorImplTest extends UnitTestSuite {

  private val junitAnnotationGenerator = mock[JUnitAnnotationGenerator]
  private val identifierNormalizer = mock[IdentifierNormalizer]

  private val junitTestMethodGenerator = new JUnitTestMethodGeneratorImpl(junitAnnotationGenerator, identifierNormalizer)


  test("transform for basic valid scenario") {
    val testName = "check me"
    val args = List(Lit.String(testName))
    val body =
      q"""{
        doCheck()
      }
      """

    val expectedJUnitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      def checkMe(): scala.Unit = {
        doCheck()
      }
      """

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@org.junit.jupiter.api.Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me"))
      .thenReturn(mod"""@org.junit.jupiter.api.DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(Nil)).thenReturn(Nil)
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when has a fixture param") {
    val testName = "check me"
    val args = List(Lit.String(testName))
    val body =
      q"""{ db =>
        doCheck(db)
      }
      """

    val expectedJUnitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      def checkMe(): scala.Unit = doCheck(db)
      """

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@org.junit.jupiter.api.Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me"))
      .thenReturn(mod"""@org.junit.jupiter.api.DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(Nil)).thenReturn(Nil)
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when disabled") {
    val testName = "check me"
    val args = List(Lit.String(testName))
    val body =
      q"""{
        doCheck()
      }
      """

    val expectedJUnitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      @org.junit.jupiter.api.Disabled
      def checkMe(): scala.Unit = {
        doCheck()
      }
      """

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@org.junit.jupiter.api.Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me"))
      .thenReturn(mod"""@org.junit.jupiter.api.DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(Nil)).thenReturn(Nil)
    when(junitAnnotationGenerator.disabledAnnotation()).thenReturn(mod"@org.junit.jupiter.api.Disabled")
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args, disabled = true)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when has qualified tags") {
    val testName = "check me"
    val tagNames = List("tag1", "tag2")
    val tags = List(q"""org.scalatest.Tag("tag1")""", q"""org.scalatest.Tag("tag2")""")
    val args = List(Lit.String(testName)) ++ tags
    val body =
      q"""{
        doCheck()
      }
      """

    val expectedJUnitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      @org.junit.jupiter.api.Tag("tag1")
      @org.junit.jupiter.api.Tag("tag2")
      def checkMe(): scala.Unit = {
        doCheck()
      }
      """

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@org.junit.jupiter.api.Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me"))
      .thenReturn(mod"""@org.junit.jupiter.api.DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(tagNames))
      .thenReturn(List(mod"""@org.junit.jupiter.api.Tag("tag1")""", mod"""@org.junit.jupiter.api.Tag("tag2")"""))
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when has unqualified tags") {
    val testName = "check me"
    val tagNames = List("tag1", "tag2")
    val tags = List(q"""Tag("tag1")""", q"""Tag("tag2")""")
    val args = List(Lit.String(testName)) ++ tags
    val body =
      q"""{
        doCheck()
      }
      """

    val expectedJUnitTestMethod =
      q"""
      @org.junit.jupiter.api.Test
      @org.junit.jupiter.api.DisplayName("check me")
      @org.junit.jupiter.api.Tag("tag1")
      @org.junit.jupiter.api.Tag("tag2")
      def checkMe(): scala.Unit = {
        doCheck()
      }
      """

    when(junitAnnotationGenerator.testAnnotation()).thenReturn(mod"@org.junit.jupiter.api.Test")
    when(junitAnnotationGenerator.displayNameAnnotationWith("check me"))
      .thenReturn(mod"""@org.junit.jupiter.api.DisplayName("check me")""")
    when(junitAnnotationGenerator.tagAnnotationsWith(tagNames))
      .thenReturn(List(mod"""@org.junit.jupiter.api.Tag("tag1")""", mod"""@org.junit.jupiter.api.Tag("tag2")"""))
    when(identifierNormalizer.toMemberName(testName)).thenReturn("checkMe")

    junitTestMethodGenerator.generate(args)(body).value.structure shouldBe expectedJUnitTestMethod.structure
  }

  test("transform when test name is not a literal string, should return None") {
    junitTestMethodGenerator.generate(List(q"generateName()"))(q"{ check() }") shouldBe None
  }
}
