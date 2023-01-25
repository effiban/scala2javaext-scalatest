package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.extractors.TermApplyInfixSpecNameExtractor
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class TermApplyInfixTestRegistrationTransformerTest extends UnitTestSuite {

  private val termApplyInfixSpecNameExtractor = mock[TermApplyInfixSpecNameExtractor]
  private val junitTestMethodGenerator = mock[JUnitTestMethodGenerator]

  private val termApplyInfixSpecRegistrationTransformer = new TermApplyInfixTestRegistrationTransformer(
    termApplyInfixSpecNameExtractor,
    junitTestMethodGenerator
  )


  test("transform valid with name only") {
    val testRegistration =
      q"""
      it should "succeed" in {
        doSomething()
      }
      """
    val spec = q"""it should "succeed""""
    val body =
      q"""
      {
        doSomething()
      }
      """

    val junitMethod =
      q"""
      @Test
      @DisplayName("it should succeed")
      def itShouldSucceed(): Unit = {
          doSomething();
      }
      """
    val name = q""""it should succeed""""


    when(termApplyInfixSpecNameExtractor.extract(eqTree(spec))).thenReturn(Some(name))
    when(junitTestMethodGenerator.generate(eqTreeList(List(name)))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixSpecRegistrationTransformer.transform(testRegistration).value.structure shouldBe junitMethod.structure
  }

  test("transform valid with name and tags") {
    val testRegistration =
      q"""
      it should "succeed" taggedAs(Tag("tag1"), Tag("tag2")) in {
        doSomething()
      }
      """
    val spec = q"""it should "succeed""""
    val tags = List(q"""Tag("tag1")""", q"""Tag("tag2")""")
    val body =
      q"""
      {
        doSomething()
      }
      """

    val junitMethod =
      q"""
      @Test
      @DisplayName("it should succeed")
      @Tag("tag1")
      @Tag("tag2")
      def itShouldSucceed(): Unit = {
        doSomething();
      }
      """
    val name = q""""it should succeed""""

    when(termApplyInfixSpecNameExtractor.extract(eqTree(spec))).thenReturn(Some(name))
    when(junitTestMethodGenerator.generate(eqTreeList(name :: tags))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixSpecRegistrationTransformer.transform(testRegistration).value.structure shouldBe junitMethod.structure
  }

  test("transform when registration clause is invalid should return None") {
    val invalidRegistration =
      q"""
      it should "succeed" blabla {
        doSomething()
      }
      """

    termApplyInfixSpecRegistrationTransformer.transform(invalidRegistration) shouldBe None

    verifyNoMoreInteractions(termApplyInfixSpecNameExtractor, junitTestMethodGenerator)
  }

  test("transform when spec is invalid should return None") {
    val invalidRegistration =
      q"""
      it blabla "succeed" in {
        doSomething()
      }
      """
    val spec = q"""it blabla "succeed""""

    when(termApplyInfixSpecNameExtractor.extract(eqTree(spec))).thenReturn(None)

    termApplyInfixSpecRegistrationTransformer.transform(invalidRegistration) shouldBe None

    verifyNoMoreInteractions(junitTestMethodGenerator)
  }

  test("transform when JUnit method generator returns None should return None") {
    val invalidRegistration =
      q"""
      it should "succeed" in {
        doSomething()
      }
      """
    val spec = q"""it should "succeed""""
    val body =
      q"""
      {
        doSomething()
      }
      """
    val name = q""""it should succeed""""


    when(termApplyInfixSpecNameExtractor.extract(eqTree(spec))).thenReturn(Some(name))
    when(junitTestMethodGenerator.generate(eqTreeList(List(name)))(eqTree(body))).thenReturn(None)

    termApplyInfixSpecRegistrationTransformer.transform(invalidRegistration) shouldBe None

    verify(junitTestMethodGenerator).generate(eqTreeList(List(name)))(eqTree(body))
  }
}
