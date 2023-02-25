package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class LogicalMatcherTransformerTest extends UnitTestSuite {

  private val nestedTransformer = mock[MatcherTransformer]

  private val transformer = new LogicalMatcherTransformer(nestedTransformer)

  test("transform() for a Term.ApplyInfix with 'not', when nested term is transformed") {
    val matcher = q"not myMatcher(3)"
    val nestedMatcher = q"myMatcher(3)"
    val expectedNestedHamcrestMatcher = q"myHamcrestMatcher(3)"
    val expectedHamcrestMatcher = q"not(myHamcrestMatcher(3))"

    when(nestedTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(expectedNestedHamcrestMatcher))

    transformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix with 'not', when nested term is not transformed") {
    val matcher = q"not bla(3)"
    val nestedMatcher = q"bla(3)"

    when(nestedTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix when term is not a logical matcher should return None") {
    transformer.transform(q"bla bla 3") shouldBe None
  }

  test("transform() for a Term.Apply should return None") {
    transformer.transform(q"not(3)") shouldBe None
  }

  test("transform() for a Term.Name should return None") {
    transformer.transform(q"not") shouldBe None
  }
}
