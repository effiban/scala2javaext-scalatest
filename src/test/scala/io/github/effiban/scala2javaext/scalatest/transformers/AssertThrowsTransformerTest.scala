package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.AssertThrowsTransformer.transform

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class AssertThrowsTransformerTest extends UnitTestSuite {

  test("testTransform") {
    val exceptionType = t"IllegalStateException"
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput =
      q"""
      Try {
        doSomethingIllegal()
        fail()
      }.recover {
        case _: IllegalStateException =>
      }
      """

    transform(exceptionType, body).structure shouldBe expectedOutput.structure

  }

}
