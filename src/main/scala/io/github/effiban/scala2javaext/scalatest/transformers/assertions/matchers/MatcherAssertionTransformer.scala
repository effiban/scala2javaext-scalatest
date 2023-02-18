package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.AssertThat
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.Equal

import scala.meta.Term

trait MatcherAssertionTransformer {

  def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply]
}

private[transformers] class MatcherAssertionTransformerImpl(assertionWordClassifier: ScalatestAssertionWordClassifier,
                                                            matcherTransformer: MatcherTransformer)
  extends MatcherAssertionTransformer {

  override def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply] = {
    import assertionWordClassifier._

    val adjustedMatcher = if (isEqualityAssertionWord(verb)) Term.Apply(Equal, List(matcher)) else matcher

    matcherTransformer.transform(adjustedMatcher)
      .map(hamcrestMatcher => Term.Apply(AssertThat, List(actual, hamcrestMatcher)))
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
