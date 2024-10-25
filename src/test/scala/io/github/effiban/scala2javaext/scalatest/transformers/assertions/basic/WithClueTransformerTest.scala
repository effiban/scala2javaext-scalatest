package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.test.utils.matchers.QualifiedTermApplyScalatestMatcher.equalQualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.WithClueTransformer.transform

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class WithClueTransformerTest extends UnitTestSuite {

  test("transform() without type") {
    val clue = q""""special clue""""
    val body =
      q"""{
        doSomething()
      }
      """

    val expectedQualifiedTermApply = QualifiedTermApply(
      q"""
      io.vavr.control.Try.of(() => {
        doSomething()
      }).recover
      """,
      List(
        q"""
        e => e match {
          case ex: org.opentest4j.AssertionFailedError => org.junit.jupiter.api.Assertions.fail("special clue", ex)
          case _ => throw e
        }
        """
      )
    )

    transform(clue = clue, body = body) should equalQualifiedTermApply(expectedQualifiedTermApply)
  }


  test("transform() with type") {
    val returnType = t"int"
    val clue = q""""special clue""""
    val body =
      q"""{
        doSomething()
      }
      """

    val expectedQualifiedTermApply = QualifiedTermApply(
      q"""
      io.vavr.control.Try.ofSupplier[int](() => {
        doSomething()
      }).recover(e => e match {
        case ex: org.opentest4j.AssertionFailedError => org.junit.jupiter.api.Assertions.fail("special clue", ex)
        case _ => throw e
      }).get
      """,
      Nil
    )

    transform(Some(returnType), clue, body) should equalQualifiedTermApply(expectedQualifiedTermApply)
  }
}
