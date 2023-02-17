package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.HamcrestAssertThat

import scala.meta.{Term, XtensionQuasiquoteTerm}

trait MatcherAssertionTransformer {

  def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply]
}

private[transformers] class MatcherAssertionTransformerImpl(termNameClassifier: ScalatestTermNameClassifier,
                                                            equalityMatcherTransformer: EqualityMatcherTransformer)
  extends MatcherAssertionTransformer {

  override def transform(actual: Term, verb: Term.Name, matcher: Term): Option[Term.Apply] = {
    import termNameClassifier._

    val adjustedMatcher = if (isEqualityMatcherVerb(verb)) Term.Apply(q"equal", List(matcher)) else matcher

    val maybeHamcrestMatcher = equalityMatcherTransformer.transform(adjustedMatcher)

    maybeHamcrestMatcher.map(hamcrestMatcher => Term.Apply(HamcrestAssertThat, List(actual, hamcrestMatcher)))
  }
}

object MatcherAssertionTransformer extends MatcherAssertionTransformerImpl(
  ScalatestTermNameClassifier,
  EqualityMatcherTransformer
)
