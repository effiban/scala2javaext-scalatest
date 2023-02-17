package io.github.effiban.scala2javaext.scalatest.transformers.testregistrations

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo
import io.github.effiban.scala2javaext.scalatest.extractors.TermSpecInfoExtractor
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.eqTo

import scala.meta.XtensionQuasiquoteTerm

class TermApplyInfixRegistrationTransformerTest extends UnitTestSuite {

  private val scalatestTermNameClassifier = mock[ScalatestTermNameClassifier]
  private val termSpecInfoExtractor = mock[TermSpecInfoExtractor]
  private val junitTestMethodGenerator = mock[JUnitTestMethodGenerator]

  private val termApplyInfixSpecRegistrationTransformer = new TermApplyInfixRegistrationTransformer(
    scalatestTermNameClassifier,
    termSpecInfoExtractor,
    junitTestMethodGenerator
  )


  test("transform when valid, not ignored, and has name only") {
    val registration =
      q"""
      it should "succeed" in {
        doSomething()
      }
      """
    val spec = q"""it should "succeed""""
    val registrator = q"in"
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


    when(scalatestTermNameClassifier.isTermApplyInfixRegistrator(eqTree(registrator))).thenReturn(true)
    when(termSpecInfoExtractor.extract(eqTree(spec))).thenReturn(Some(SpecInfo(name)))
    when(scalatestTermNameClassifier.isIgnore(eqTree(registrator))).thenReturn(false)
    when(junitTestMethodGenerator.generate(eqTreeList(List(name)), disabled = eqTo(false))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixSpecRegistrationTransformer.transform(registration).value.structure shouldBe junitMethod.structure
  }

  test("transform when valid, ignored by registrator word, and has name only") {
    val registration =
      q"""
      it should "succeed" ignore {
        doSomething()
      }
      """
    val spec = q"""it should "succeed""""
    val registrator = q"ignore"
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
      @Disabled
      def itShouldSucceed(): Unit = {
          doSomething();
      }
      """
    val name = q""""it should succeed""""


    when(scalatestTermNameClassifier.isTermApplyInfixRegistrator(eqTree(registrator))).thenReturn(true)
    when(termSpecInfoExtractor.extract(eqTree(spec))).thenReturn(Some(SpecInfo(name)))
    when(scalatestTermNameClassifier.isIgnore(eqTree(registrator))).thenReturn(true)
    when(junitTestMethodGenerator.generate(eqTreeList(List(name)), disabled = eqTo(true))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixSpecRegistrationTransformer.transform(registration).value.structure shouldBe junitMethod.structure
  }

  test("transform when valid, ignored by subject word, and has name only") {
    val registration =
      q"""
      ignore should "succeed" in {
        doSomething()
      }
      """
    val spec = q"""ignore should "succeed""""
    val registrator = q"in"
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
      @Disabled
      def itShouldSucceed(): Unit = {
          doSomething();
      }
      """
    val name = q""""it should succeed""""


    when(scalatestTermNameClassifier.isTermApplyInfixRegistrator(eqTree(registrator))).thenReturn(true)
    when(termSpecInfoExtractor.extract(eqTree(spec))).thenReturn(Some(SpecInfo(name, ignored = true)))
    when(scalatestTermNameClassifier.isIgnore(eqTree(registrator))).thenReturn(false)
    when(junitTestMethodGenerator.generate(eqTreeList(List(name)), disabled = eqTo(true))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixSpecRegistrationTransformer.transform(registration).value.structure shouldBe junitMethod.structure
  }

  test("transform when valid, with name and tags") {
    val registration =
      q"""
      it should "succeed" taggedAs(Tag("tag1"), Tag("tag2")) in {
        doSomething()
      }
      """
    val spec = q"""it should "succeed""""
    val tags = List(q"""Tag("tag1")""", q"""Tag("tag2")""")
    val registrator = q"in"
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

    when(scalatestTermNameClassifier.isTermApplyInfixRegistrator(eqTree(registrator))).thenReturn(true)
    when(termSpecInfoExtractor.extract(eqTree(spec))).thenReturn(Some(SpecInfo(name)))
    when(scalatestTermNameClassifier.isIgnore(eqTree(registrator))).thenReturn(false)
    when(junitTestMethodGenerator.generate(eqTreeList(name :: tags), disabled = eqTo(false))(eqTree(body))).thenReturn(Some(junitMethod))

    termApplyInfixSpecRegistrationTransformer.transform(registration).value.structure shouldBe junitMethod.structure
  }

  test("transform when registrator word is invalid should return None") {
    val invalidRegistration =
      q"""
      it should "succeed" blabla {
        doSomething()
      }
      """

    termApplyInfixSpecRegistrationTransformer.transform(invalidRegistration) shouldBe None

    verifyNoMoreInteractions(termSpecInfoExtractor, junitTestMethodGenerator)
  }

  test("transform when spec is invalid should return None") {
    val invalidRegistration =
      q"""
      it blabla "succeed" in {
        doSomething()
      }
      """
    val spec = q"""it blabla "succeed""""
    val registrator = q"in"

    when(scalatestTermNameClassifier.isTermApplyInfixRegistrator(eqTree(registrator))).thenReturn(true)
    when(termSpecInfoExtractor.extract(eqTree(spec))).thenReturn(None)
    when(scalatestTermNameClassifier.isIgnore(eqTree(registrator))).thenReturn(false)

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
    val registrator = q"in"
    val body =
      q"""
      {
        doSomething()
      }
      """
    val name = q""""it should succeed""""


    when(scalatestTermNameClassifier.isTermApplyInfixRegistrator(eqTree(registrator))).thenReturn(true)
    when(termSpecInfoExtractor.extract(eqTree(spec))).thenReturn(Some(SpecInfo(name)))
    when(scalatestTermNameClassifier.isIgnore(eqTree(registrator))).thenReturn(false)
    when(junitTestMethodGenerator.generate(eqTreeList(List(name)), disabled = eqTo(false))(eqTree(body))).thenReturn(None)

    termApplyInfixSpecRegistrationTransformer.transform(invalidRegistration) shouldBe None

    verify(junitTestMethodGenerator).generate(eqTreeList(List(name)), disabled = eqTo(false))(eqTree(body))
  }
}
