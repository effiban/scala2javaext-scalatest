package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.extractors.{InfixNestedRegistratorNameExtractor, SubjectNameExtractor}
import io.github.effiban.scala2javaext.scalatest.generators.JUnitNestedTestClassGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class TermApplyInfixNestedRegistrationTransformerTest extends UnitTestSuite {

  private val infixNestedRegistratorNameExtractor = mock[InfixNestedRegistratorNameExtractor]
  private val subjectNameExtractor = mock[SubjectNameExtractor]
  private val junitNestedTestClassGenerator = mock[JUnitNestedTestClassGenerator]

  private val transformer = new TermApplyInfixNestedRegistrationTransformer(
    infixNestedRegistratorNameExtractor,
    subjectNameExtractor,
    junitNestedTestClassGenerator
  )


  test("transform() when valid should return a JUnit nested class") {
    val subject = q"it"
    val subjectName = q""""it""""

    val registratorWord = q"should"
    val registratorName = q""""should""""


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

    val nestedRegistrations = List(nestedRegistration1, nestedRegistration2)

    val infixRegistration =
    q"""
    it should {
      "accelerate when gas pedal pressed" in {
        pressGasPedal()
        assertSpeedIncreased()
      }

      "decelerate when brake pedal pressed" in {
        pressBrakePedal()
        assertSpeedDecreased()
      }
    }
    """

    val junitNestedTestClass =
      q"""
      @Nested
      @DisplayName("it")
      class It {

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


    when(subjectNameExtractor.extract(eqTree(subject))).thenReturn(Some(subjectName))
    when(infixNestedRegistratorNameExtractor.extract(eqTree(registratorWord))).thenReturn(Some(registratorName))
    when(junitNestedTestClassGenerator.generate(eqTree(subjectName), eqTree(registratorName), eqTreeList(nestedRegistrations))).thenReturn(junitNestedTestClass)

    transformer.transform(infixRegistration).value.structure shouldBe junitNestedTestClass.structure
  }

  test("transform() when registrator word is invalid should return None") {
    val invalidRegistratorWord = q"blabla"

    val invalidInfixRegistration =
      q"""
      "car" blabla {
        "accelerate when gas pedal pressed" in {
          pressGasPedal()
          assertSpeedIncreased()
        }
      }
      """

    when(subjectNameExtractor.extract(eqTree(q""""car""""))).thenReturn(Some(q""""car""""))
    when(infixNestedRegistratorNameExtractor.extract(eqTree(invalidRegistratorWord))).thenReturn(None)

    transformer.transform(invalidInfixRegistration) shouldBe None
  }

  test("transform() when subject is invalid should return None") {
    val invalidSubject = q"blabla"

    val invalidInfixRegistration =
      q"""
      blabla should {
        "accelerate when gas pedal pressed" in {
          pressGasPedal()
          assertSpeedIncreased()
        }
      }
      """

    when(subjectNameExtractor.extract(eqTree(invalidSubject))).thenReturn(None)

    transformer.transform(invalidInfixRegistration) shouldBe None
  }

  test("transform() when registration has two blocks should return None") {
    val registrationWithTwoBlocks =
      q"""
      it should(
        {
          "doSomething" in {}
        },
        {
          "doSomethingElse" in {}
        }
      )
      """

    transformer.transform(registrationWithTwoBlocks) shouldBe None
  }

  test("transform() when registration has no block should return None") {
    val registrationWithoutBlock = q"it should doSomething()"

    transformer.transform(registrationWithoutBlock) shouldBe None
  }
}
