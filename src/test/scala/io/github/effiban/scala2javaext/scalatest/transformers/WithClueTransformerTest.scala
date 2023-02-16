package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.WithClueTransformer.transform

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
      Try {
        doSomething()
      }.recover(e => e match {
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
      Try[Int] {
        doSomething()
      }.recover(e => e match {
        case ex: AssertionFailedError => fail("special clue", ex)
        case _ => throw e
      }).get()
      """

    transform(Some(returnType), clue, body).structure shouldBe expectedResult.structure
  }
}
