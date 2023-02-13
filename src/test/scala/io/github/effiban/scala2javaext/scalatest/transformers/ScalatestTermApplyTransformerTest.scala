package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqSomeTree
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.eqTo

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class ScalatestTermApplyTransformerTest extends UnitTestSuite {

  private val assertTransformer = mock[AssertTransformer]
  private val assertResultTransformer = mock[AssertResultTransformer]
  private val assertExpectedExceptionTransformer = mock[AssertExpectedExceptionTransformer]

  private val scalatestTermApplyTransformer = new ScalatestTermApplyTransformer(
    assertTransformer,
    assertResultTransformer,
    assertExpectedExceptionTransformer
  )

  test("transform() for assert() without clue") {
    val assertInvocation = q"assert(x == 3)"
    val condition = q"x == 3"
    val expectedOutput = q"assertThat(x == 3, is(true))"

    when(assertTransformer.transform(eqTree(condition), eqTo(None))).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(assertInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for assert() with clue") {
    val assertInvocation = q"""assert(x == 3, "should be 3")"""
    val condition = q"x == 3"
    val clue = q""""should be 3""""
    val expectedOutput = q"""assertThat("should be 3", x == 3, is(true))"""

    when(assertTransformer.transform(eqTree(condition), eqSomeTree(clue))).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(assertInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for assertResult() without clue") {
    val assertResultInvocation = q"assertResult(3)(x + y)"
    val expected = q"3"
    val actual = q"x + y"
    val expectedOutput = q"assertThat(x + y, is(3))"

    when(assertResultTransformer.transform(eqTree(expected), eqTo(None), eqTree(actual))).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(assertResultInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for assertResult() with clue") {
    val assertResultInvocation = q"""assertResult(3, "should be 3")(x + y)"""
    val expected = q"3"
    val actual = q"x + y"
    val clue = q""""should be 3""""
    val expectedOutput = q"""assertThat("should be 3",  x + y, is(3))"""

    when(assertResultTransformer.transform(eqTree(expected), eqSomeTree(clue), eqTree(actual)))
      .thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(assertResultInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for assertThrows without a type should return a Try{}.recover()") {
    val assertThrowsInvocation = q"assertThrows { doSomethingIllegal() }"
    val assertThrowsBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"Throwable"
    val expectedOutput =
      q"""
    Try {
      doSomethingIllegal()
      fail("Should have thrown a Throwable")
    }.recover { e =>
      e match {
        case _: Throwable =>
        case _ => fail("Should have thrown a Throwable")
      }
    }
    """

    when(assertExpectedExceptionTransformer.transform(
      eqTree(exceptionType),
      eqTree(assertThrowsBody),
      eqTo(false)
    )).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(assertThrowsInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for assertThrows with a type should return a Try{}.recover()") {
    val assertThrowsInvocation = q"assertThrows[IllegalStateException] { doSomethingIllegal() }"
    val assertThrowsBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"IllegalStateException"
    val expectedOutput =
      q"""
      Try {
        doSomethingIllegal()
        fail("Should have thrown an IllegalStateException")
      }.recover { e =>
        e match {
          case _: IllegalStateException =>
          case _ => fail("Should have thrown an IllegalStateException")
        }
      }
      """

    when(assertExpectedExceptionTransformer.transform(
      eqTree(exceptionType),
      eqTree(assertThrowsBody),
      eqTo(false)
    )).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(assertThrowsInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for intercept() without a type should return a Try{}.recover().get()") {
    val interceptInvocation = q"intercept { doSomethingIllegal() }"
    val interceptBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"Throwable"
    val expectedOutput =
      q"""
    Try {
      doSomethingIllegal()
      fail("Should have thrown a Throwable")
    }.recover { e =>
      e match {
        case ex: Throwable => ex
        case _ => fail("Should have thrown a Throwable")
      }
    }.get()
    """

    when(assertExpectedExceptionTransformer.transform(
      eqTree(exceptionType),
      eqTree(interceptBody),
      eqTo(true)
    )).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(interceptInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for intercept() with a type should return a Try{}.recover().get()") {
    val interceptInvocation = q"intercept[IllegalStateException] { doSomethingIllegal() }"
    val interceptBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"IllegalStateException"
    val expectedOutput =
      q"""
      Try {
        doSomethingIllegal()
        fail("Should have thrown an IllegalStateException")
      }.recover { e =>
        e match {
          case ex: IllegalStateException => ex
          case _ => fail("Should have thrown an IllegalStateException")
        }
      }.get()
      """

    when(assertExpectedExceptionTransformer.transform(
      eqTree(exceptionType),
      eqTree(interceptBody),
      eqTo(true)
    )).thenReturn(expectedOutput)

    scalatestTermApplyTransformer.transform(interceptInvocation).structure shouldBe expectedOutput.structure
  }

  test("transform() for unrecognized invocation should return it unchanged") {
    val invocation = q"foo(x)"

    scalatestTermApplyTransformer.transform(invocation).structure shouldBe invocation.structure
  }
}
