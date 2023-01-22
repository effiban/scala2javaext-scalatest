package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class ScalatestTemplateTermApplyToDefnTransformerTest extends UnitTestSuite {

  private val testRegistrationTransformer = mock[TestRegistrationTransformer]

  private val termApplyToDefnTransformer = new ScalatestTemplateTermApplyToDefnTransformer(testRegistrationTransformer)

  test("transform() valid 'test' invocation should return equivalent JUnit '@Test' method") {
    val testRegistration =
      q"""
      test("check me") {
          doCheck()
      }
      """

    val args = List(Lit.String("check me"))
    val body =
      q"""
      {
        doCheck()
      }
      """

    val junitTestMethod =
      q"""
      @Test
      @DisplayName("check me")
      def checkMe(): Unit = doCheck()
      """

    when(testRegistrationTransformer.transform(eqTreeList(args))(eqTree(body))).thenReturn(Some(junitTestMethod))

    termApplyToDefnTransformer.transform(testRegistration).value.structure shouldBe junitTestMethod.structure
  }

  test("transform() 'test' invocation with two bodies should return None") {
    val testRegistration =
      q"""test("check me")(body1, body2)"""

    termApplyToDefnTransformer.transform(testRegistration) shouldBe None
  }

  test("transform() 'test' invocation without body should return None") {
    val testRegistration = q"""test("check me")"""

    termApplyToDefnTransformer.transform(testRegistration) shouldBe None
  }

  test("transform() 'bla' invocation should return None") {
    val testRegistration =
      q"""
      bla("check me") {
        doCheck()
      }
      """

    termApplyToDefnTransformer.transform(testRegistration) shouldBe None
  }
}
