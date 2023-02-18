package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.AssertThat
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.{Be, Equal}

import scala.meta.Term

trait MatcherAssertionTransformer {

  def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply]
}

private[transformers] class MatcherAssertionTransformerImpl(assertionWordClassifier: ScalatestAssertionWordClassifier,
                                                            matcherTransformer: MatcherTransformer)
  extends MatcherAssertionTransformer {

  override def transform(actual: Term, assertionWord: Term.Name, matcher: Term): Option[Term.Apply] = {

    val adjustedMatcher = adjustMatcherByAssertion(assertionWord, matcher)

    matcherTransformer.transform(adjustedMatcher)
      .map(hamcrestMatcher => Term.Apply(AssertThat, List(actual, hamcrestMatcher)))
  }

  private def adjustMatcherByAssertion(assertionWord: Term.Name, matcher: Term) = {
    import assertionWordClassifier._
    assertionWord match {
      case word if isEqualAssertionWord(word) => Term.Apply(Equal, List(matcher))
      case word if isBeAssertionWord(word) => Term.Apply(Be, List(matcher))
      case _ => matcher
    }
  }
}

object MatcherAssertionTransformer extends MatcherAssertionTransformerImpl(
  ScalatestAssertionWordClassifier,
  new CompositeMatcherTransformer(
    List(
      EqualMatcherTransformer,
      BeMatcherTransformer
    )
  )
)
