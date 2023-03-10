package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.generators.JUnitAnnotationGenerator.{disabledAnnotation, displayNameAnnotationWith, nestedAnnotation, tagAnnotationsWith, testAnnotation}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteMod

class JUnitAnnotationGeneratorTest extends UnitTestSuite {

  test("testAnnotation") {
    testAnnotation().structure shouldBe mod"@Test".structure
  }

  test("nestedAnnotation") {
    nestedAnnotation().structure shouldBe mod"@Nested".structure
  }

  test("tagAnnotationsWith") {
    tagAnnotationsWith(List("tag1", "tag2")).structure shouldBe List(mod"""@Tag("tag1")""", mod"""@Tag("tag2")""").structure
  }

  test("displayNameAnnotationWith") {
    displayNameAnnotationWith("my test").structure shouldBe mod"""@DisplayName("my test")""".structure
  }

  test("disabledAnnotation") {
    disabledAnnotation().structure shouldBe mod"@Disabled".structure
  }
}
