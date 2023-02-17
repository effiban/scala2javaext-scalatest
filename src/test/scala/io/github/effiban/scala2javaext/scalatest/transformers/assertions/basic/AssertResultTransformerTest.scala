package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.AssertResultTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class AssertResultTransformerTest extends UnitTestSuite {

  test("transform() without clue") {
    transform(expected = q"3", actual = q"x + y").structure shouldBe q"assertThat(x + y, is(3))".structure
  }

  test("transform() with clue") {
    transform(expected = q"3", maybeClue = Some(q""""should be 3""""), actual = q"x + y").structure shouldBe
      q"""
      assertThat("should be 3",
                 x + y, is(3))
      """.structure
  }
}
