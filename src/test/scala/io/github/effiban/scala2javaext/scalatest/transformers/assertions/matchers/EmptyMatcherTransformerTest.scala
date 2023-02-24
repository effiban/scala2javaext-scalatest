package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.{HamcrestMatcherTerms, ScalatestConstants}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.EmptyMatcherTransformer.transform

import scala.meta.{Term, XtensionQuasiquoteTerm}

class EmptyMatcherTransformerTest extends UnitTestSuite {

  test("transform() when it is the 'empty' matcher should return the Hamcrest equivalent") {
    transform(ScalatestConstants.Empty).value.structure shouldBe Term.Apply(HamcrestMatcherTerms.Empty, Nil).structure
  }

  test("transform() when it is not the 'empty' matcher should return None") {
    transform(q"is") shouldBe None
  }
}
