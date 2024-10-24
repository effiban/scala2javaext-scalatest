package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class ATypeMatcherTransformerTest extends UnitTestSuite {

  private val matcherWordClassifier = mock[ScalatestMatcherWordClassifier]

  private val aTypeMatcherTransformer = new ATypeMatcherTransformer(matcherWordClassifier)


  test("transform for a Term.Apply with a Term.ApplyType having a valid qualified word should return Hamcrest 'isA'") {
    val matcher = q"super.a[java.lang.String]()"
    val word = q"a"
    val expectedHamcrestMatcher = q"org.hamcrest.Matchers.isA(classOf[java.lang.String])"

    when(matcherWordClassifier.isATypeWord(eqTree(word))).thenReturn(true)

    aTypeMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform for a Term.Apply with a Term.ApplyType having an unqualified word should return None") {
    val matcher = q"a[java.lang.String]()"

    aTypeMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform for a for a Term.Apply with a Term.ApplyType having a valid qualified word and two types should return None") {
    val matcher = q"super.a[java.lang.String, int]()"

    aTypeMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherWordClassifier)
  }

  test("transform for a Term.Apply with a Term.ApplyType having an invalid qualified word should return None") {
    val matcher = q"super.bla[java.lang.String]()"
    val word = q"bla"

    when(matcherWordClassifier.isATypeWord(eqTree(word))).thenReturn(false)

    aTypeMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform for a Term.Apply with no Term.ApplyType should return None") {
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
