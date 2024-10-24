package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.test.utils.matchers.QualifiedTermApplyScalatestMatcher.equalQualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.AssertThrows
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.AssertThrowsTransformer.transform

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class AssertThrowsTransformerTest extends UnitTestSuite {

  test("transform() without an explicit type when body is a Term.Block") {
    val inputBody =
      q"""
      {
        doSomethingLegal()
        doSomethingIllegal()
      }
      """
    val expectedQualifiedTermApply = QualifiedTermApply(
      AssertThrows,
      List(
        q"classOf[java.lang.Throwable]",
        q"""
        () => {
          doSomethingLegal()
          doSomethingIllegal()
        }
        """
      )
    )

    transform(body = inputBody) should equalQualifiedTermApply(expectedQualifiedTermApply)
  }

  test("transform() without an explicit type when body is a Term.Apply") {
    val inputBody = q"doSomethingIllegal()"
    val expectedQualifiedTermApply = QualifiedTermApply(
      AssertThrows,
      List(q"classOf[java.lang.Throwable]", q"() => doSomethingIllegal()")
    )

    transform(body = inputBody) should equalQualifiedTermApply(expectedQualifiedTermApply)
  }


  test("transform() with an explicit type") {
    val exceptionType = t"java.lang.IllegalStateException"
    val inputBody = q"doSomethingIllegal()"
    val expectedQualifiedTermApply = QualifiedTermApply(
      AssertThrows,
      List(q"classOf[java.lang.IllegalStateException]", q"() => doSomethingIllegal()")
    )

    transform(exceptionType, inputBody) should equalQualifiedTermApply(expectedQualifiedTermApply)
  }
}
