package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import scala.meta.Term

private[transformers] class CompositeMatcherTransformer(matcherTransformers: List[MatcherTransformer]) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcherTransformers.foldLeft[Option[Term]](None)(
      (maybeHamcrestMatcher, matcherTransformer) => maybeHamcrestMatcher.orElse(matcherTransformer.transform(matcher))
    )
  }
}

object CompositeMatcherTransformer extends CompositeMatcherTransformer(
  List(
    EqualityMatcherTransformer
  )
)
