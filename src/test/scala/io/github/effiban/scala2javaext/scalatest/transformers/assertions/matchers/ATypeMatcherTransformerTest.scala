package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class ATypeMatcherTransformerTest extends UnitTestSuite {

  private val matcherWordClassifier = mock[ScalatestMatcherWordClassifier]

  private val aTypeMatcherTransformer = new ATypeMatcherTransformer(matcherWordClassifier)


  test("transform for a Term.ApplyType with a valid word should return Hamcrest 'isA'") {
    val matcher = q"a[String]"
    val word = q"a"
    val expectedHamcrestMatcher = q"isA(classOf[String])"

    when(matcherWordClassifier.isATypeWord(eqTree(word))).thenReturn(true)

    aTypeMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform for a Term.ApplyType with a valid word and two types should return None") {
    val matcher = q"a[String, Int]"

    aTypeMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherWordClassifier)
  }

  test("transform for a Term.ApplyType with an invalid word should return None") {
    val matcher = q"bla[String]"
    val word = q"bla"

    when(matcherWordClassifier.isATypeWord(eqTree(word))).thenReturn(false)

    aTypeMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform for a Term.Apply should return None") {
    val matcher = q"a(something)"

    aTypeMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherWordClassifier)
  }

  test("transform for a Term.Name should return None") {
    val matcher = q"a"

    aTypeMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherWordClassifier)
  }
}
