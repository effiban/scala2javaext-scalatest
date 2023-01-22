package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.extractors.FlatSpecTestNameExtractor
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class FlatSpecRegistrationTransformerImplTest extends UnitTestSuite {

  private val flatSpecTestNameExtractor = mock[FlatSpecTestNameExtractor]
  private val testRegistrationTransformer = mock[TestRegistrationTransformer]

  private val flatSpecRegistrationTransformer = new FlatSpecRegistrationTransformerImpl(flatSpecTestNameExtractor, testRegistrationTransformer)


  test("transform valid with name only") {
    val nameClause = q"""it should "succeed""""
    val name = q""""it should succeed""""
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

    when(flatSpecTestNameExtractor.extract(eqTree(nameClause))).thenReturn(Some(name))
    when(testRegistrationTransformer.transform(eqTreeList(List(name)))(eqTree(body))).thenReturn(Some(junitMethod))

    flatSpecRegistrationTransformer.transform(nameClause)(body).value.structure shouldBe junitMethod.structure
  }

  test("transform valid with name and tags") {
    val description = q"""it should "succeed" taggedAs(Tag("tag1"), Tag("tag2"))"""
    val nameClause = q"""it should "succeed""""
    val name = q""""it should succeed""""
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

    when(flatSpecTestNameExtractor.extract(eqTree(nameClause))).thenReturn(Some(name))
    when(testRegistrationTransformer.transform(eqTreeList(name :: tags))(eqTree(body))).thenReturn(Some(junitMethod))

    flatSpecRegistrationTransformer.transform(description)(body).value.structure shouldBe junitMethod.structure
  }

  test("transform when name cannot be extracted should return None") {
    val nameClause = q"""a blabla "b""""
    val body =
      q"""
      {
        doSomething()
      }
      """

    when(flatSpecTestNameExtractor.extract(eqTree(nameClause))).thenReturn(None)

    flatSpecRegistrationTransformer.transform(nameClause)(body) shouldBe None
  }

  test("transform when inner transformer returns None should return None") {
    val nameClause = q"""it should "succeed""""
    val name = q""""it should succeed""""
    val body =
      q"""
      {
        doSomething()
      }
      """

    when(flatSpecTestNameExtractor.extract(eqTree(nameClause))).thenReturn(Some(name))
    when(testRegistrationTransformer.transform(eqTreeList(List(name)))(eqTree(body))).thenReturn(None)

    flatSpecRegistrationTransformer.transform(nameClause)(body) shouldBe None
  }
}
