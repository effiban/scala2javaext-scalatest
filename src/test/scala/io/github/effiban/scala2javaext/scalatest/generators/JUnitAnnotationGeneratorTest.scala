package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.generators.JUnitAnnotationGenerator.{disabledAnnotation, displayNameAnnotationWith, nestedAnnotation, tagAnnotationsWith, testAnnotation}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteMod

class JUnitAnnotationGeneratorTest extends UnitTestSuite {

  test("testAnnotation") {
    testAnnotation().structure shouldBe mod"@org.junit.jupiter.api.Test".structure
  }

  test("nestedAnnotation") {
    nestedAnnotation().structure shouldBe mod"@org.junit.jupiter.api.Nested".structure
  }

  test("tagAnnotationsWith") {
    tagAnnotationsWith(List("tag1", "tag2")).structure shouldBe
      List(mod"""@org.junit.jupiter.api.Tag("tag1")""", mod"""@org.junit.jupiter.api.Tag("tag2")""").structure
  }

  test("displayNameAnnotationWith") {
    displayNameAnnotationWith("my test").structure shouldBe mod"""@org.junit.jupiter.api.DisplayName("my test")""".structure
  }

  test("disabledAnnotation") {
    disabledAnnotation().structure shouldBe mod"@org.junit.jupiter.api.Disabled".structure
  }
}
