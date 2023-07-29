package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

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

    val expectedResult =
      q"""
      Try.of(() => {
        doSomething()
      }).recover(e => e match {
        case ex: AssertionFailedError => fail("special clue", ex)
        case _ => throw e
      })
      """

    transform(clue = clue, body = body).structure shouldBe expectedResult.structure
  }


  test("transform() with type") {
    val returnType = t"Int"
    val clue = q""""special clue""""
    val body =
      q"""{
        doSomething()
      }
      """

    val expectedResult =
      q"""
      Try.ofSupplier[Int](() => {
        doSomething()
      }).recover(e => e match {
        case ex: AssertionFailedError => fail("special clue", ex)
        case _ => throw e
      }).get()
      """

    transform(Some(returnType), clue, body).structure shouldBe expectedResult.structure
  }
}
