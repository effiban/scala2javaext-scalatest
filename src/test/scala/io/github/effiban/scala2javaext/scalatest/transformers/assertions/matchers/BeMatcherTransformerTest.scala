package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class BeMatcherTransformerTest extends UnitTestSuite {

  private val nestedTransformer = mock[MatcherTransformer]

  private val transformer = new BeMatcherTransformer(nestedTransformer)

  test("transform() for a Term.Apply when nested term is transformed") {
    val expectedHamcrestMatcher = q"myHamcrestMatcher(3)"

    when(nestedTransformer.transform(eqTree(q"myMatcher(3)"))).thenReturn(Some(expectedHamcrestMatcher))

    transformer.transform(q"be(myMatcher(3))").value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.Apply when nested term is not transformed") {
    when(nestedTransformer.transform(eqTree(q"3"))).thenReturn(None)

    transformer.transform(q"be(3)").value.structure shouldBe q"is(3)".structure
  }

  test("transform() for a Term.Apply when term is not 'be' should return None") {
    transformer.transform(q"bla(3)") shouldBe None
  }

  test("transform() for a Term.Name should return None") {
    transformer.transform(q"bla") shouldBe None
  }
}
