package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.AssertThrowsTransformer.transform

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class AssertThrowsTransformerTest extends UnitTestSuite {

  test("transform() without an explicit type") {
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput = q"assertThrows(classOf[Throwable], { doSomethingIllegal() } )"

    transform(body = body).structure shouldBe expectedOutput.structure
  }

  test("transform() with an explicit type") {
    val exceptionType = t"IllegalStateException"
    val body = q"{ doSomethingIllegal() }"
    val expectedOutput = q"assertThrows(classOf[IllegalStateException], { doSomethingIllegal() } )"

    transform(exceptionType, body).structure shouldBe expectedOutput.structure
  }
}
