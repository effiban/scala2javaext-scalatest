package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.DifferentTypeTransformer0
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Defn, Term, XtensionQuasiquoteTerm}

class CompositeTermToDefnTransformerTest extends UnitTestSuite {

  private val TheTermApply = q"foo(1, 2)"
  private val TheDefn = q"def bar(x: Int, y: Int): Unit = {}"

  private val transformer1 = mock[TestTermToDefnTransformer]
  private val transformer2 = mock[TestTermToDefnTransformer]

  test("transform when there are no transformers - should return empty") {
    compositeTransformer().transform(TheTermApply) shouldBe None
  }

  test("transform when there is one transformer returning non-empty should return its result") {
    when(transformer1.transform(eqTree(TheTermApply))).thenReturn(Some(TheDefn))

    compositeTransformer(List(transformer1)).transform(TheTermApply).value.structure shouldBe TheDefn.structure
  }

  test("transform when there are two transformers and first returns non-empty should return result of first") {
    when(transformer1.transform(eqTree(TheTermApply))).thenReturn(Some(TheDefn))

    compositeTransformer(List(transformer1, transformer2)).transform(TheTermApply).value.structure shouldBe TheDefn.structure
  }

  test("transform when there are two transformers, first returns empty and second returns non-empty - should return result of second") {
    when(transformer1.transform(eqTree(TheTermApply))).thenReturn(None)
    when(transformer2.transform(eqTree(TheTermApply))).thenReturn(Some(TheDefn))

    compositeTransformer(List(transformer1, transformer2)).transform(TheTermApply).value.structure shouldBe TheDefn.structure
  }

  test("transform when there are two transformers, both returning empty - should return empty") {
    when(transformer1.transform(eqTree(TheTermApply))).thenReturn(None)
    when(transformer2.transform(eqTree(TheTermApply))).thenReturn(None)

    compositeTransformer(List(transformer1, transformer2)).transform(TheTermApply) shouldBe None
  }

  private def compositeTransformer(transformers: List[TestTermToDefnTransformer] = Nil) = new TestCompositeTermToDefnTransformer(transformers)


  private sealed trait TestTermToDefnTransformer extends DifferentTypeTransformer0[Term, Defn]

  private class TestCompositeTermToDefnTransformer(override protected val transformers: List[DifferentTypeTransformer0[Term, Defn]] = Nil)
    extends CompositeTermToDefnTransformer[Term] with TestTermToDefnTransformer
}
