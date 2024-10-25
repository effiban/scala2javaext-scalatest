package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class HaveMatcherTransformerTest extends UnitTestSuite {

  private val nestedMatcherTransformer = mock[MatcherTransformer]

  private val haveMatcherTransformer = new HaveMatcherTransformer(nestedMatcherTransformer)

  test("transform() for a Term.Apply() when fun is 'super.have', and nested matcher returns a result - should return it") {
    val matcher = q"super.have(size(3))"
    val nestedMatcher = q"size(3)"
    val hamcrestMatcher = q"org.hamcrest.Matchers.hasSize(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(hamcrestMatcher))

    haveMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform() for a Term.Apply() when fun is 'super.have', and nested matcher returns None - should return None") {
    val matcher = q"super.have(bla(3))"
    val nestedMatcher = q"bla(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    haveMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.Apply() when fun is 'super.have', and there is more than one argument - should return None") {
    val matcher = q"super.have(size(3), width(4))"

    haveMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(nestedMatcherTransformer)
  }

  test("transform() for a Term.Apply() when fun is 'super.could', should return None") {
    val matcher = q"super.could(size(3))"

    haveMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(nestedMatcherTransformer)
  }

  test("transform() for a Term.Apply() when fun is 'have', and nested matcher returns a result - should return it") {
    val matcher = q"have(size(3))"
    val nestedMatcher = q"size(3)"
    val hamcrestMatcher = q"org.hamcrest.Matchers.hasSize(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(hamcrestMatcher))

    haveMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform() for a Term.Apply() when fun is 'have', and nested matcher returns None - should return None") {
    val matcher = q"have(bla(3))"
    val nestedMatcher = q"bla(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    haveMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.Apply() when fun is 'have', and there is more than one argument - should return None") {
    val matcher = q"have(size(3), width(4))"

    haveMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(nestedMatcherTransformer)
  }

  test("transform() for a Term.Apply() when fun is 'could', should return None") {
    val matcher = q"could(size(3))"

    haveMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(nestedMatcherTransformer)
  }

  test("transform() for a Term.ApplyInfix() when LHS is 'super.have', and nested matcher returns a result - should return it") {
    val matcher = q"super.have size 3"
    val nestedMatcher = q"size(3)"
    val hamcrestMatcher = q"org.hamcrest.Matchers.hasSize(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(hamcrestMatcher))

    haveMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix() when LHS is 'super.have', and nested matcher returns None - should return None") {
    val matcher = q"super.have bla 3"
    val nestedMatcher = q"bla(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    haveMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix() when LHS is 'super.could', should return None") {
    val matcher = q"super.could size 3"

    haveMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(nestedMatcherTransformer)
  }

  test("transform() for a Term.ApplyInfix() when LHS is 'have', and nested matcher returns a result - should return it") {
    val matcher = q"have size 3"
    val nestedMatcher = q"size(3)"
    val hamcrestMatcher = q"org.hamcrest.Matchers.hasSize(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(hamcrestMatcher))

    haveMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix() when LHS is 'have', and nested matcher returns None - should return None") {
    val matcher = q"have bla 3"
    val nestedMatcher = q"bla(3)"

    when(nestedMatcherTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    haveMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix() when LHS is 'could', should return None") {
    val matcher = q"could size 3"

    haveMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(nestedMatcherTransformer)
  }
}
