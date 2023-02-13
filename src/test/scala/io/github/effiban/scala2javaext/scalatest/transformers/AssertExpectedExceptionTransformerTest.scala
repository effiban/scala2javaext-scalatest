package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.AssertExpectedExceptionTransformer.transform

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class AssertExpectedExceptionTransformerTest extends UnitTestSuite {

  test("transform() without an explicit type, and not returning the exception") {
    val exceptionType = t"Throwable"
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput =
      q"""
      Try {
        doSomethingIllegal()
        fail("Should have thrown an Throwable")
      }.recover(e =>
        e match {
          case _: Throwable =>
          case _ => fail("Should have thrown an Throwable")
        }
      )
      """

    transform(exceptionType, body).structure shouldBe expectedOutput.structure
  }

  test("transform() with an explicit type, and not returning the exception") {
    val exceptionType = t"IllegalStateException"
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput =
      q"""
      Try {
        doSomethingIllegal()
        fail("Should have thrown an IllegalStateException")
      }.recover(e =>
        e match {
          case _: IllegalStateException =>
          case _ => fail("Should have thrown an IllegalStateException")
        }
      )
      """

    transform(exceptionType, body).structure shouldBe expectedOutput.structure
  }

  test("transform() without an explicit type, and returning the exception") {
    val exceptionType = t"Throwable"
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput =
      q"""
    Try {
      doSomethingIllegal()
      fail("Should have thrown an Throwable")
    }.recover(e =>
      e match {
        case ex: Throwable => ex
        case _ => fail("Should have thrown an Throwable")
      }
    ).get()
    """

    transform(exceptionType, body, returnException = true).structure shouldBe expectedOutput.structure
  }

  test("transform() with explicit type, and returning the exception") {
    val exceptionType = t"IllegalStateException"
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput =
      q"""
      Try {
        doSomethingIllegal()
        fail("Should have thrown an IllegalStateException")
      }.recover(e =>
        e match {
          case ex: IllegalStateException => ex
          case _ => fail("Should have thrown an IllegalStateException")
        }
      ).get()
      """

    transform(exceptionType, body, returnException = true).structure shouldBe expectedOutput.structure
  }
}
