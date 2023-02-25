package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.any

import scala.meta.{Term, XtensionQuasiquoteTerm}

class LogicalMatcherTransformerTest extends UnitTestSuite {

  private val nestedTransformer = mock[MatcherTransformer]

  private val transformer = new LogicalMatcherTransformer(nestedTransformer)

  test("transform() for a Term.ApplyInfix with 'not', when nested transformer returns a result - should return it wrapped by 'not'") {
    val matcher = q"not myMatcher(3)"
    val nestedMatcher = q"myMatcher(3)"
    val expectedNestedHamcrestMatcher = q"myHamcrestMatcher(3)"
    val expectedHamcrestMatcher = q"not(myHamcrestMatcher(3))"

    when(nestedTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(expectedNestedHamcrestMatcher))

    transformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix with 'not', when nested transformer returns None - should return None") {
    val matcher = q"not bla(3)"
    val nestedMatcher = q"bla(3)"

    when(nestedTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix with 'and', when LHS and RHS both transformed, should return 'allOf' of the results") {
    val matcher = q"leftIs(2) and rightIs(3)"
    val leftMatcher = q"leftIs(2)"
    val rightMatcher = q"rightIs(3)"
    val expectedLeftHamcrestMatcher = q"hamcrestLeftIs(2)"
    val expectedRightHamcrestMatcher = q"hamcrestRightIs(3)"
    val expectedHamcrestMatcher = q"allOf(hamcrestLeftIs(2), hamcrestRightIs(3))"

    when(nestedTransformer.transform(any[Term])).thenAnswer((matcher: Term) => matcher match {
      case aMatcher if aMatcher.structure == leftMatcher.structure => Some(expectedLeftHamcrestMatcher)
      case aMatcher if aMatcher.structure == rightMatcher.structure => Some(expectedRightHamcrestMatcher)
      case _ => None
    })

    transformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix with 'and', when only LHS transformed, should return None") {
    val matcher = q"leftIs(2) and rightIs(3)"
    val leftMatcher = q"leftIs(2)"
    val expectedLeftHamcrestMatcher = q"hamcrestLeftIs(2)"

    when(nestedTransformer.transform(any[Term])).thenAnswer((matcher: Term) => matcher match {
      case aMatcher if aMatcher.structure == leftMatcher.structure => Some(expectedLeftHamcrestMatcher)
      case _ => None
    })

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix with 'and', when only RHS transformed, should return None") {
    val matcher = q"leftIs(2) and rightIs(3)"
    val rightMatcher = q"rightIs(3)"
    val expectedRightHamcrestMatcher = q"hamcrestRightIs(3)"

    when(nestedTransformer.transform(any[Term])).thenAnswer((matcher: Term) => matcher match {
      case aMatcher if aMatcher.structure == rightMatcher.structure => Some(expectedRightHamcrestMatcher)
      case _ => None
    })

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix with 'and', when neither side transformed, should return None") {
    val matcher = q"leftIs(2) and rightIs(3)"

    when(nestedTransformer.transform(any[Term])).thenReturn(None)

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix with 'or', when LHS and RHS both transformed, should return 'allOf' of the results") {
    val matcher = q"leftIs(2) or rightIs(3)"
    val leftMatcher = q"leftIs(2)"
    val rightMatcher = q"rightIs(3)"
    val expectedLeftHamcrestMatcher = q"hamcrestLeftIs(2)"
    val expectedRightHamcrestMatcher = q"hamcrestRightIs(3)"
    val expectedHamcrestMatcher = q"anyOf(hamcrestLeftIs(2), hamcrestRightIs(3))"

    when(nestedTransformer.transform(any[Term])).thenAnswer((matcher: Term) => matcher match {
      case aMatcher if aMatcher.structure == leftMatcher.structure => Some(expectedLeftHamcrestMatcher)
      case aMatcher if aMatcher.structure == rightMatcher.structure => Some(expectedRightHamcrestMatcher)
      case _ => None
    })

    transformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.ApplyInfix with 'or', when only LHS transformed, should return None") {
    val matcher = q"leftIs(2) or rightIs(3)"
    val leftMatcher = q"leftIs(2)"
    val expectedLeftHamcrestMatcher = q"hamcrestLeftIs(2)"

    when(nestedTransformer.transform(any[Term])).thenAnswer((matcher: Term) => matcher match {
      case aMatcher if aMatcher.structure == leftMatcher.structure => Some(expectedLeftHamcrestMatcher)
      case _ => None
    })

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix with 'or', when only RHS transformed, should return None") {
    val matcher = q"leftIs(2) or rightIs(3)"
    val rightMatcher = q"rightIs(3)"
    val expectedRightHamcrestMatcher = q"hamcrestRightIs(3)"

    when(nestedTransformer.transform(any[Term])).thenAnswer((matcher: Term) => matcher match {
      case aMatcher if aMatcher.structure == rightMatcher.structure => Some(expectedRightHamcrestMatcher)
      case _ => None
    })

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix with 'or', when neither side transformed, should return None") {
    val matcher = q"leftIs(2) or rightIs(3)"

    when(nestedTransformer.transform(any[Term])).thenReturn(None)

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.ApplyInfix when term is not a logical matcher should return None") {
    transformer.transform(q"bla bla 3") shouldBe None
  }

  test("transform() for a Term.Apply with 'not' when nested transformer returns a result - should return it wrapped by 'not'") {
    val matcher = q"not(myMatcher(3))"
    val nestedMatcher = q"myMatcher(3)"
    val expectedNestedHamcrestMatcher = q"myHamcrestMatcher(3)"
    val expectedHamcrestMatcher = q"not(myHamcrestMatcher(3))"

    when(nestedTransformer.transform(eqTree(nestedMatcher))).thenReturn(Some(expectedNestedHamcrestMatcher))

    transformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() for a Term.Apply with 'not' when nested matcher is not transformed, should return None") {
    val matcher = q"not(myMatcher(3))"
    val nestedMatcher = q"myMatcher(3)"

    when(nestedTransformer.transform(eqTree(nestedMatcher))).thenReturn(None)

    transformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.Apply when fun is not a logical matcher word, should return None") {
    transformer.transform(q"bla(3)") shouldBe None
  }

  test("transform() for a Term.Name should return None") {
    transformer.transform(q"not") shouldBe None
  }
}
