package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.test.utils.matchers.QualifiedTermApplyScalatestMatcher.equalQualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.AssertThat
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.AssertTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class AssertTransformerTest extends UnitTestSuite {

  test("transform() without clue") {
    val expectedQualifiedTermApply = QualifiedTermApply(AssertThat, List(q"x == 3", q"org.hamcrest.Matchers.is(true)"))

    transform(q"x == 3") should equalQualifiedTermApply(expectedQualifiedTermApply)
  }

  test("transform() with clue") {
    val expectedQualifiedTermApply = QualifiedTermApply(
      AssertThat,
      List(q""""should be 3"""", q"x == 3", q"org.hamcrest.Matchers.is(true)")
    )

    transform(q"x == 3", Some(q""""should be 3"""")) should equalQualifiedTermApply(expectedQualifiedTermApply)
  }
}
