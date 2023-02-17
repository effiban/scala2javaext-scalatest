package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.AssertTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class AssertTransformerTest extends UnitTestSuite {

  test("transform() without clue") {
    transform(q"x == 3").structure shouldBe q"assertThat(x == 3, is(true))".structure
  }

  test("transform() with clue") {
    transform(q"x == 3", Some(q""""should be 3"""")).structure shouldBe
      q"""
      assertThat("should be 3",
                 x == 3, is(true))
      """.structure
  }
}
