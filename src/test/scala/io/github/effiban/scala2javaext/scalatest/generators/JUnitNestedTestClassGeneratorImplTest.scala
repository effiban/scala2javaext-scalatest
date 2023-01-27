package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.{IdentifierNormalizer, InfixRegistrationSubjectModifier}
import org.mockito.ArgumentMatchersSugar.any

import scala.meta.{Lit, Term, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

class JUnitNestedTestClassGeneratorImplTest extends UnitTestSuite {

  private val infixRegistrationSubjectModifier = mock[InfixRegistrationSubjectModifier]
  private val junitAnnotationGenerator = mock[JUnitAnnotationGenerator]
  private val identifierNormalizer = mock[IdentifierNormalizer]

  private val junitNestedTestClassGenerator = new JUnitNestedTestClassGeneratorImpl(
    infixRegistrationSubjectModifier,
    junitAnnotationGenerator,
    identifierNormalizer
  )


  test("generate() when registration is a Term.Apply") {
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

    junitNestedTestClassGenerator.generate(name = name, nestedRegistrations = nestedRegistrations).structure shouldBe junitNestedTestClass.structure

    verifyNoMoreInteractions(infixRegistrationSubjectModifier)
  }

  test("generate() when registration is a Term.ApplyInfix") {
    val name = q""""car""""
    val nestedPrefix = q""""should""""

    val nestedRegistration1 =
      q"""
      "accelerate when gas pedal pressed" in {
        pressGasPedal()
        assertSpeedIncreased()
      }
      """

    val nestedRegistration2 =
      q"""
      "decelerate when brake pedal pressed" in {
        pressBrakePedal()
        assertSpeedDecreased()
      }
      """

    val modifiedNestedRegistration1 =
      q"""
      "should accelerate when gas pedal pressed" in {
        pressGasPedal()
        assertSpeedIncreased()
      }
      """

    val modifiedNestedRegistration2 =
      q"""
      "should decelerate when brake pedal pressed" in {
        pressBrakePedal()
        assertSpeedDecreased()
      }
      """

    val nestedRegistrations = List(
      nestedRegistration1,
      nestedRegistration2
    )

    val junitNestedTestClass =
      q"""
      @Nested
      @DisplayName("car")
      class Car {

        "should accelerate when gas pedal pressed" in {
          pressGasPedal()
          assertSpeedIncreased()
        }

        "should decelerate when brake pedal pressed" in {
          pressBrakePedal()
          assertSpeedDecreased()
        }
      }
      """

    when(infixRegistrationSubjectModifier.prepend(any[Term.ApplyInfix], eqTree(nestedPrefix)))
      .thenAnswer( (nestedRegistration: Term.ApplyInfix, _: Lit.String) => nestedRegistration match {
        case reg if reg.structure == nestedRegistration1.structure => modifiedNestedRegistration1
        case reg if reg.structure == nestedRegistration2.structure => modifiedNestedRegistration2
        case aNestedRegistration => aNestedRegistration
      })
    when(junitAnnotationGenerator.nestedAnnotation()).thenReturn(mod"@Nested")
    when(junitAnnotationGenerator.displayNameAnnotationWith("car")).thenReturn(mod"""@DisplayName("car")""")
    when(identifierNormalizer.toClassName("car")).thenReturn("Car")

    junitNestedTestClassGenerator.generate(name, nestedPrefix, nestedRegistrations).structure shouldBe junitNestedTestClass.structure
  }
}
