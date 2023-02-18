package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class CompositeMatcherTransformerTest extends UnitTestSuite {

  private val TheMatcher = q"equal(3)"
  private val TheHamcrestMatcher = q"is(3)"

  private val transformer1 = mock[MatcherTransformer]
  private val transformer2 = mock[MatcherTransformer]

  test("transform when there are no transformers - should return empty") {
    compositeTransformer().transform(TheMatcher) shouldBe None
  }

  test("transform when there is one transformer returning non-empty should return its result") {
    when(transformer1.transform(eqTree(TheMatcher))).thenReturn(Some(TheHamcrestMatcher))

    compositeTransformer(List(transformer1)).transform(TheMatcher).value.structure shouldBe TheHamcrestMatcher.structure
  }

  test("transform when there are two transformers and first returns non-empty should return result of first") {
    when(transformer1.transform(eqTree(TheMatcher))).thenReturn(Some(TheHamcrestMatcher))

    compositeTransformer(List(transformer1, transformer2)).transform(TheMatcher).value.structure shouldBe TheHamcrestMatcher.structure
  }

  test("transform when there are two transformers, first returns empty and second returns non-empty - should return result of second") {
    when(transformer1.transform(eqTree(TheMatcher))).thenReturn(None)
    when(transformer2.transform(eqTree(TheMatcher))).thenReturn(Some(TheHamcrestMatcher))

    compositeTransformer(List(transformer1, transformer2)).transform(TheMatcher).value.structure shouldBe TheHamcrestMatcher.structure
  }

  test("transform when there are two transformers, both returning empty - should return empty") {
    when(transformer1.transform(eqTree(TheMatcher))).thenReturn(None)
    when(transformer2.transform(eqTree(TheMatcher))).thenReturn(None)

    compositeTransformer(List(transformer1, transformer2)).transform(TheMatcher) shouldBe None
  }

  private def compositeTransformer(transformers: List[MatcherTransformer] = Nil) = new CompositeMatcherTransformer(transformers)
}
