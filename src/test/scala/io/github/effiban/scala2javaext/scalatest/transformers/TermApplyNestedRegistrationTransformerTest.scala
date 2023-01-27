package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitNestedTestClassGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class TermApplyNestedRegistrationTransformerTest extends UnitTestSuite {

  private val scalatestTermNameClassifier = mock[ScalatestTermNameClassifier]
  private val junitNestedTestClassGenerator = mock[JUnitNestedTestClassGenerator]

  private val transformer = new TermApplyNestedRegistrationTransformer(scalatestTermNameClassifier, junitNestedTestClassGenerator)

  test("transform() valid with one argument should return equivalent JUnit '@Nested' class") {
    val testRegistration =
      q"""
      Feature("check all") {      
        Scenario("check 1") { 
          doCheck1()
        }

        Scenario("check 2") { 
          doCheck2()
        }
      }
      """

    val registrationWord = q"Feature"
    val name = q""""check all""""
    val nestedRegistrations =
      List(
        q"""
        Scenario("check 1") {
          doCheck1()
        }
        """,
        q"""
        Scenario("check 2") {
          doCheck2()
        }
        """
      )

    val junitNestedTestClass =
      q"""
      @Nested
      @DisplayName("check all")
      class CheckAll {
        Scenario("check 1") {
          doCheck1()
        }

        Scenario("check 2") {
          doCheck2()
        }
      }
      """

    when(scalatestTermNameClassifier.isTermApplyNestedRegistrator(eqTree(registrationWord))).thenReturn(true)
    when(junitNestedTestClassGenerator.generate(eqTree(name), eqTree(Lit.String("")), eqTreeList(nestedRegistrations))).thenReturn(junitNestedTestClass)

    transformer.transform(testRegistration).value.structure shouldBe junitNestedTestClass.structure
  }

  test("transform() when the registration term is not a single word should return None") {
    val testRegistration =
      q"""
      My.Feature("check all") {
        Scenario("check 1") {
          doCheck1()
        }

        Scenario("check 2") {
          doCheck2()
        }
      }
      """

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when there are two names should return None") {
    val testRegistration =
      q"""
      Feature("check something", "check something else") {
        Scenario("check 1") {
          doCheck1()
        }

        Scenario("check 2") {
          doCheck2()
        }
      }
      """

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when has two bodies should return None") {
    val testRegistration =
      q"""
      Feature("check all")(
        {
          Scenario("check 1") {
            doCheck1()
          }
        },
        {
          Scenario("check 2") {
            doCheck2()
          }
        }
      )
      """

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when has no body should return None") {
    val testRegistration = q"""Feature("check all")"""

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when registration word is invalid should return None") {
    val testRegistration =
      q"""
      blabla("check all") {      
        Scenario("check 1") { 
          doCheck1()
        }

        Scenario("check 2") { 
          doCheck2()
        }
      }
      """

    val registrationWord = q"bla"

    when(scalatestTermNameClassifier.isTermApplyNestedRegistrator(eqTree(registrationWord))).thenReturn(false)

    transformer.transform(testRegistration) shouldBe None
  }

  test("transform() when test name is missing should return None") {
    val testRegistration =
      q"""
      Feature() {
        Scenario("check 1") {
          doCheck1()
        }

        Scenario("check 2") {
          doCheck2()
        }
      }
      """

    transformer.transform(testRegistration) shouldBe None
  }
}
