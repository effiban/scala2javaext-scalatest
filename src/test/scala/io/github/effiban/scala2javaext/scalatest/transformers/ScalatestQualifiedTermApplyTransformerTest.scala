package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.contexts.TermApplyTransformationContext
import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqSomeTree
import io.github.effiban.scala2java.test.utils.matchers.QualifiedTermApplyScalatestMatcher.equalQualifiedTermApply
import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.AssertionsType
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.{AssertResultTransformer, AssertThrowsTransformer, AssertTransformer, WithClueTransformer}
import org.mockito.ArgumentMatchersSugar.eqTo

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class ScalatestQualifiedTermApplyTransformerTest extends UnitTestSuite {

  private val assertTransformer = mock[AssertTransformer]
  private val assertResultTransformer = mock[AssertResultTransformer]
  private val assertThrowsTransformer = mock[AssertThrowsTransformer]
  private val withClueTransformer = mock[WithClueTransformer]

  private val scalatestQualifiedTermApplyTransformer = new ScalatestQualifiedTermApplyTransformer(
    assertTransformer,
    assertResultTransformer,
    assertThrowsTransformer,
    withClueTransformer
  )

  private val context  = TermApplyTransformationContext(maybeQualifierType = Some(AssertionsType))

  test("transform() for assert() when fully qualified, without clue") {
    val assertInvocation = QualifiedTermApply(q"org.scalatest.Assertions.assert", List(q"x == 3"))
    val condition = q"x == 3"
    val expectedOutput = QualifiedTermApply(q"org.hamcrest.matcher.Assert.assertThat", List(q"x == 3", q"org.hamcrest.Matchers.is(true)"))

    when(assertTransformer.transform(eqTree(condition), eqTo(None))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assert() when fully qualified, with clue") {
    val clue = q""""should be 3""""
    val condition = q"x == 3"
    val assertInvocation = QualifiedTermApply(q"org.scalatest.Assertions.assert", List(condition, clue))
    val expectedOutput = QualifiedTermApply(
      q"org.hamcrest.matcher.Assert.assertThat",
      List(clue, condition, q"org.hamcrest.Matchers.is(true)")
    )

    when(assertTransformer.transform(eqTree(condition), eqSomeTree(clue))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assert() when qualified by type, without clue") {
    val assertInvocation = QualifiedTermApply(q"super.assert", List(q"x == 3"))
    val condition = q"x == 3"
    val expectedOutput = QualifiedTermApply(q"org.hamcrest.matcher.Assert.assertThat", List(q"x == 3", q"org.hamcrest.Matchers.is(true)"))

    when(assertTransformer.transform(eqTree(condition), eqTo(None))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assert() when qualified by type, with clue") {
    val clue = q""""should be 3""""
    val condition = q"x == 3"
    val assertInvocation = QualifiedTermApply(q"super.assert", List(condition, clue))
    val expectedOutput = QualifiedTermApply(
      q"org.hamcrest.matcher.Assert.assertThat",
      List(clue, condition, q"org.hamcrest.Matchers.is(true)")
    )

    when(assertTransformer.transform(eqTree(condition), eqSomeTree(clue))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertResult() when fully qualified, without clue") {
    val expected = q"3"
    val actual = q"x + y"
    val assertResultInvocation = QualifiedTermApply(q"org.scalatest.Assertions.assertResult", List(expected, actual))
    val expectedOutput = QualifiedTermApply(q"org.hamcrest.matcher.Assert.assertThat", List(actual, q"org.hamcrest.Matchers.is(3)"))

    when(assertResultTransformer.transform(eqTree(expected), eqTo(None), eqTree(actual))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertResultInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertResult() when fully qualified, with clue") {
    val expected = q"3"
    val actual = q"x + y"
    val clue = q""""should be 3""""
    val assertResultInvocation = QualifiedTermApply(q"org.scalatest.Assertions.assertResult", List(expected, clue, actual))
    val expectedOutput = QualifiedTermApply(q"org.hamcrest.matcher.Assert.assertThat", List(clue, actual, q"org.hamcrest.Matchers.is(3)"))

    when(assertResultTransformer.transform(eqTree(expected), eqSomeTree(clue), eqTree(actual)))
      .thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertResultInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertResult() when qualified by type, without clue") {
    val expected = q"3"
    val actual = q"x + y"
    val assertResultInvocation = QualifiedTermApply(q"super.assertResult", List(expected, actual))
    val expectedOutput = QualifiedTermApply(q"org.hamcrest.matcher.Assert.assertThat", List(actual, q"org.hamcrest.Matchers.is(3)"))

    when(assertResultTransformer.transform(eqTree(expected), eqTo(None), eqTree(actual))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertResultInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertResult() when qualified by type, with clue") {
    val expected = q"3"
    val actual = q"x + y"
    val clue = q""""should be 3""""
    val assertResultInvocation = QualifiedTermApply(q"super.assertResult", List(expected, clue, actual))
    val expectedOutput = QualifiedTermApply(q"org.hamcrest.matcher.Assert.assertThat", List(clue, actual, q"org.hamcrest.Matchers.is(3)"))

    when(assertResultTransformer.transform(eqTree(expected), eqSomeTree(clue), eqTree(actual)))
      .thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertResultInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertThrows() when fully qualified, without a type") {
    val assertThrowsBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.Throwable"
    val assertThrowsInvocation = QualifiedTermApply(q"org.scalatest.Assertions.assertThrows", List(assertThrowsBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows", List(q"classOf[java.lang.Throwable]", assertThrowsBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType),eqTree(assertThrowsBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertThrowsInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertThrows() when fully qualified, with a type") {
    val assertThrowsBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.IllegalStateException"
    val assertThrowsInvocation = QualifiedTermApply(q"org.scalatest.Assertions.assertThrows", List(exceptionType), List(assertThrowsBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows",
      List(q"classOf[java.lang.IllegalStateException]", assertThrowsBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType), eqTree(assertThrowsBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertThrowsInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertThrows() when qualified by type, without a type") {
    val assertThrowsBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.Throwable"
    val assertThrowsInvocation = QualifiedTermApply(q"super.assertThrows", List(assertThrowsBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows", List(q"classOf[java.lang.Throwable]", assertThrowsBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType),eqTree(assertThrowsBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertThrowsInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for assertThrows() when qualified by type, with a type") {
    val assertThrowsBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.IllegalStateException"
    val assertThrowsInvocation = QualifiedTermApply(q"super.assertThrows", List(exceptionType), List(assertThrowsBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows",
      List(q"classOf[java.lang.IllegalStateException]", assertThrowsBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType), eqTree(assertThrowsBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(assertThrowsInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for intercept() when fully qualified, without a type") {
    val interceptBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.Throwable"
    val interceptInvocation = QualifiedTermApply(q"org.scalatest.Assertions.intercept", List(interceptBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows", List(q"classOf[java.lang.Throwable]", interceptBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType),eqTree(interceptBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(interceptInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for intercept() when fully qualified, with a type") {
    val interceptBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.IllegalStateException"
    val interceptInvocation = QualifiedTermApply(q"org.scalatest.Assertions.intercept", List(exceptionType), List(interceptBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows",
      List(q"classOf[java.lang.IllegalStateException]", interceptBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType), eqTree(interceptBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(interceptInvocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for intercept() when qualified by type, without a type") {
    val interceptBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.Throwable"
    val interceptInvocation = QualifiedTermApply(q"super.intercept", List(interceptBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows", List(q"classOf[java.lang.Throwable]", interceptBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType),eqTree(interceptBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(interceptInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for intercept() when qualified by type, with a type") {
    val interceptBody = q"{ doSomethingIllegal() }"
    val exceptionType = t"java.lang.IllegalStateException"
    val interceptInvocation = QualifiedTermApply(q"super.intercept", List(exceptionType), List(interceptBody))
    val expectedOutput = QualifiedTermApply(
      q"org.junit.jupiter.api.Assertions.assertThrows",
      List(q"classOf[java.lang.IllegalStateException]", interceptBody)
    )

    when(assertThrowsTransformer.transform(eqTree(exceptionType), eqTree(interceptBody))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(interceptInvocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for withClue() when fully qualified, without a type") {
    val clue = q""""special clue""""
    val body = q"{ doSomething() }"
    val invocation = QualifiedTermApply(q"org.scalatest.Assertions.withClue", List(clue, body))
    val expectedOutput =
      QualifiedTermApply(
        q"""
        io.vavr.control.Try.of {
          doSomething()
        }.recover
        """,
        List(
          q"""
          e => e match {
            case ex: org.opentest4j.AssertionFailedError => org.junit.jupiter.api.Assertions.fail("special clue", ex)
            case ex => throw ex
          }
          """
        )
      )

    when(withClueTransformer.transform(eqTo(None), eqTree(clue), eqTree(body))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(invocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for withClue() when fully qualified, with a type") {
    val clue = q""""special clue""""
    val body = q"{ doSomething() }"
    val invocation = QualifiedTermApply(q"org.scalatest.Assertions.withClue", List(t"int"), List(clue, body))

    val expectedOutput =
      QualifiedTermApply(
        q"""
        io.vavr.control.Try.ofSupplier[int] {
          doSomething()
        }.recover
        """,
        List(
          q"""
          e => e match {
            case ex: org.opentest4j.AssertionFailedError => org.junit.jupiter.api.Assertions.fail("special clue", ex)
            case ex => throw ex
          }
          """
        )
      )

    when(withClueTransformer.transform(eqSomeTree(t"int"), eqTree(clue), eqTree(body))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(invocation).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for withClue() when qualified by type, without a type") {
    val clue = q""""special clue""""
    val body = q"{ doSomething() }"
    val invocation = QualifiedTermApply(q"super.withClue", List(clue, body))
    val expectedOutput =
      QualifiedTermApply(
        q"""
        io.vavr.control.Try.of {
          doSomething()
        }.recover
        """,
        List(
          q"""
          e => e match {
            case ex: org.opentest4j.AssertionFailedError => org.junit.jupiter.api.Assertions.fail("special clue", ex)
            case ex => throw ex
          }
          """
        )
      )

    when(withClueTransformer.transform(eqTo(None), eqTree(clue), eqTree(body))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(invocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for withClue() when qualified by type, with a type") {
    val clue = q""""special clue""""
    val body = q"{ doSomething() }"
    val invocation = QualifiedTermApply(q"super.withClue", List(t"int"), List(clue, body))

    val expectedOutput =
      QualifiedTermApply(
        q"""
        io.vavr.control.Try.ofSupplier[int] {
          doSomething()
        }.recover
        """,
        List(
          q"""
          e => e match {
            case ex: org.opentest4j.AssertionFailedError => org.junit.jupiter.api.Assertions.fail("special clue", ex)
            case ex => throw ex
          }
          """
        )
      )

    when(withClueTransformer.transform(eqSomeTree(t"int"), eqTree(clue), eqTree(body))).thenReturn(expectedOutput)

    scalatestQualifiedTermApplyTransformer.transform(invocation, context).value should equalQualifiedTermApply(expectedOutput)
  }

  test("transform() for unrecognized invocation should return it unchanged") {
    val invocation = QualifiedTermApply(q"foo.foo", List(q"x"))

    scalatestQualifiedTermApplyTransformer.transform(invocation) shouldBe None
  }
}
