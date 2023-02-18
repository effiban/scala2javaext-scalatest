package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.AssertThat
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.Equal

import scala.meta.Term

trait MatcherAssertionTransformer {

  def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply]
}

private[transformers] class MatcherAssertionTransformerImpl(termNameClassifier: ScalatestTermNameClassifier,
                                                            matcherTransformer: MatcherTransformer)
  extends MatcherAssertionTransformer {

  override def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply] = {
    import termNameClassifier._

    val adjustedMatcher = if (isEqualityMatcherVerb(verb)) Term.Apply(Equal, List(matcher)) else matcher

    matcherTransformer.transform(adjustedMatcher)
      .map(hamcrestMatcher => Term.Apply(AssertThat, List(actual, hamcrestMatcher)))
  }
}

object MatcherAssertionTransformer extends MatcherAssertionTransformerImpl(
  ScalatestTermNameClassifier,
  CompositeMatcherTransformer
)
