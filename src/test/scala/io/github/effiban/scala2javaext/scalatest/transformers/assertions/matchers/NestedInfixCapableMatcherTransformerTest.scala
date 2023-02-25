package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.manipulators.TermApplyInfixManipulator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.any

import scala.meta.{Term, XtensionQuasiquoteTerm}

class NestedInfixCapableMatcherTransformerTest extends UnitTestSuite {

  private val innerTransformer = mock[MatcherTransformer]
  private val termApplyInfixManipulator = mock[TermApplyInfixManipulator]

  private val outerTransformer = new NestedInfixCapableMatcherTransformer(
    innerTransformer,
    termApplyInfixManipulator
  )

  test("transform() a Term.ApplyInfix, when inner transformer returns a result - should return it") {
    val matcher = q"have size(3)"
    val expectedHamcrestMatcher = q"hasSize(3)"

    when(innerTransformer.transform(eqTree(matcher))).thenReturn(Some(expectedHamcrestMatcher))

    outerTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() a Term.ApplyInfix, when inner transformer returns a result after a right-shift - should return it") {
    val matcher = q"""contain("a") and have size 4"""
    val shiftedMatcher = q"""contain("a") and (have size 4)"""
    val expectedHamcrestMatcher = q"is(3)"

    when(innerTransformer.transform(any[Term.ApplyInfix])).thenAnswer((infix: Term.ApplyInfix) => infix match {
      case anInfix if anInfix.structure == shiftedMatcher.structure => Some(expectedHamcrestMatcher)
      case _ => None
    })

    when(termApplyInfixManipulator.rightShiftInnerIfPossible(any[Term.ApplyInfix])).thenAnswer((infix: Term.ApplyInfix) => infix match {
      case anInfix if anInfix.structure == matcher.structure => Some(shiftedMatcher)
      case _ => None
    })

    outerTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() a Term.ApplyInfix, when inner transformer does not return a result and right-shift not possible - should return None") {
    val matcher = q"bla bla(3)"

    when(innerTransformer.transform(eqTree(matcher))).thenReturn(None)

    when(termApplyInfixManipulator.rightShiftInnerIfPossible(eqTree(matcher))).thenReturn(None)

    outerTransformer.transform(matcher) shouldBe None
  }

  test("transform() a Term.Apply, when inner transformer returns a result - should return it") {
    val matcher = q"be(3)"
    val expectedHamcrestMatcher = q"is(3)"

    when(innerTransformer.transform(eqTree(matcher))).thenReturn(Some(expectedHamcrestMatcher))

    outerTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() a Term.Apply, when inner transformer returns None - should return None") {
    val matcher = q"bla(3)"

    when(innerTransformer.transform(eqTree(matcher))).thenReturn(None)

    outerTransformer.transform(matcher) shouldBe None
  }


}
