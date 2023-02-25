package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

object MatcherTransformers {

  private[matchers] lazy val rootMatcherTransformer = new CompositeMatcherTransformer(
    LazyList(
      BeMatcherTransformer,
      ContainMatcherTransformer,
      EqualMatcherTransformer,
      HaveMatcherTransformer,
      RegexMatcherTransformer,
      StringMatcherTransformer
    )
  )
}
