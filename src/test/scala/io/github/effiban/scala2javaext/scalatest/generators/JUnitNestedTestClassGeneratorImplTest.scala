package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.IdentifierNormalizer

import scala.meta.{XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

class JUnitNestedTestClassGeneratorImplTest extends UnitTestSuite {

  private val junitAnnotationGenerator = mock[JUnitAnnotationGenerator]
  private val identifierNormalizer = mock[IdentifierNormalizer]

  private val junitNestedTestClassGenerator = new JUnitNestedTestClassGeneratorImpl(junitAnnotationGenerator, identifierNormalizer)

  test("generate()") {
    val name = q""""car""""

    val nestedRegistrations = List(
      q"""
      Scenario("should accelerate when gas pedal pressed") {
         pressGasPedal()
         assertSpeedIncreased()
      }
      """,
      q"""
      Scenario("should decelerate when brake pedal pressed") {
        pressBrakePedal()
        assertSpeedDecreased()
      }
      """
    )

    val junitNestedTestClass =
      q"""
      @Nested
      @DisplayName("car")
      class Car {

        Scenario("should accelerate when gas pedal pressed") {
          pressGasPedal()
          assertSpeedIncreased()
        }

        Scenario("should decelerate when brake pedal pressed") {
          pressBrakePedal()
          assertSpeedDecreased()
        }
      }
      """

    when(junitAnnotationGenerator.nestedAnnotation()).thenReturn(mod"@Nested")
    when(junitAnnotationGenerator.displayNameAnnotationWith("car")).thenReturn(mod"""@DisplayName("car")""")
    when(identifierNormalizer.toClassName("car")).thenReturn("Car")

    junitNestedTestClassGenerator.generate(name, nestedRegistrations).structure shouldBe junitNestedTestClass.structure
  }

}
