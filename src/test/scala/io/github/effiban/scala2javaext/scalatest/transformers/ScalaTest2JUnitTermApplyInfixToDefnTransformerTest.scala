package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class ScalaTest2JUnitTermApplyInfixToDefnTransformerTest extends UnitTestSuite {

  private val flatSpecRegistrationTransformer = mock[FlatSpecRegistrationTransformer]

  private val termApplyInfixToDefnTransformer = new ScalaTest2JUnitTermApplyInfixToDefnTransformer(flatSpecRegistrationTransformer)

  test("transform() when has FlatSpec syntax and FlatSpec registration transformer returns a value") {
    val flatSpecRegistration =
      q"""
      it should "fly" in {
         takeoff()
      }
      """
    val description = q"""it should "fly""""
    val body =
      q"""
      {
        takeoff()
      }
      """

    val junitMethod =
      q"""
      @Test
      @DisplayName("it should fly")
      def itShouldFly(): Unit = {
        takeoff();
      }
      """

    when(flatSpecRegistrationTransformer.transform(eqTree(description))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixToDefnTransformer.transform(flatSpecRegistration).value.structure shouldBe junitMethod.structure
  }

  test("transform() when has FlatSpec syntax and FlatSpec registration transformer returns None") {
    val flatSpecRegistration =
      q"""
      it should "fly" in {
        takeoff()
      }
      """

    val description = q"""it should "fly""""
    val body =
      q"""
      {
        takeoff()
      }
      """

    when(flatSpecRegistrationTransformer.transform(eqTree(description))(eqTree(body))).thenReturn(None)

    termApplyInfixToDefnTransformer.transform(flatSpecRegistration) shouldBe None
  }

  test("transform() when has unrecognized syntax") {

    val unrecognized =
      q"""
      have a nice day {
        goodbye()
      }
      """

    termApplyInfixToDefnTransformer.transform(unrecognized) shouldBe None

    verifyNoMoreInteractions(flatSpecRegistrationTransformer)
  }
}
