package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class BeMatcherTransformerTest extends UnitTestSuite {

  private val nestedTransformer = mock[MatcherTransformer]

  private val transformer = new BeMatcherTransformer(nestedTransformer)

  test("transform() for a Term.Apply with a qualified 'be', when nested matcher term is transformed") {
    val expectedHamcrestMatcher = q"myHamcrestMatcher(3)"

    when(nestedTransformer.transform(eqTree(q"myMatcher(3)"))).thenReturn(Some(expectedHamcrestMatcher))

    transformer.transform(q"super.be(myMatcher(3))").value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.Apply with a qualified 'be', when nested term is not transformed") {
    when(nestedTransformer.transform(eqTree(q"3"))).thenReturn(None)

    transformer.transform(q"super.be(3)").value.structure shouldBe q"org.hamcrest.Matchers.is(3)".structure
  }

  test("transform() for a Term.Apply with an unqualified 'be', when nested matcher term is transformed") {
    val expectedHamcrestMatcher = q"myHamcrestMatcher(3)"

    when(nestedTransformer.transform(eqTree(q"myMatcher(3)"))).thenReturn(Some(expectedHamcrestMatcher))

    transformer.transform(q"be(myMatcher(3))").value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.Apply with an unqualified 'be', when nested term is not transformed") {
    when(nestedTransformer.transform(eqTree(q"3"))).thenReturn(None)

    transformer.transform(q"be(3)").value.structure shouldBe q"org.hamcrest.Matchers.is(3)".structure
  }

  test("transform() for a Term.ApplyInfix with a qualified 'be' and nested matcher terms") {
    val expectedHamcrestMatcher = q"myHamcrestMatcher(3)"

    when(nestedTransformer.transform(eqTree(q"myMatcher(3)"))).thenReturn(Some(expectedHamcrestMatcher))

    transformer.transform(q"super.be myMatcher 3").value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix with an unqualified 'be' and nested matcher terms") {
    val expectedHamcrestMatcher = q"myHamcrestMatcher(3)"

    when(nestedTransformer.transform(eqTree(q"myMatcher(3)"))).thenReturn(Some(expectedHamcrestMatcher))

    transformer.transform(q"be myMatcher 3").value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.Apply when term is not 'be' should return None") {
    transformer.transform(q"bla(3)") shouldBe None
  }

  test("transform() for a Term.ApplyInfix when term is not 'be' should return None") {
    transformer.transform(q"bla matches 3") shouldBe None
  }

  test("transform() for a Term.Name should return None") {
    transformer.transform(q"bla") shouldBe None
  }
}
