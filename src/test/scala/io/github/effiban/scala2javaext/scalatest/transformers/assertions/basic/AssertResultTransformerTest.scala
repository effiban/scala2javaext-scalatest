package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.test.utils.matchers.QualifiedTermApplyScalatestMatcher.equalQualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.AssertThat
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.AssertResultTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class AssertResultTransformerTest extends UnitTestSuite {

  test("transform() without clue") {
    val expectedQualifiedTermApply = QualifiedTermApply(AssertThat, List(q"x + y", q"org.hamcrest.Matchers.is(3)"))

    transform(expected = q"3", actual = q"x + y") should equalQualifiedTermApply(expectedQualifiedTermApply)
  }

  test("transform() with clue") {
    val expectedQualifiedTermApply = QualifiedTermApply(AssertThat, List(q""""should be 3"""", q"x + y", q"org.hamcrest.Matchers.is(3)"))

    transform(expected = q"3", maybeClue = Some(q""""should be 3""""), actual = q"x + y") should
      equalQualifiedTermApply(expectedQualifiedTermApply)
  }
}
