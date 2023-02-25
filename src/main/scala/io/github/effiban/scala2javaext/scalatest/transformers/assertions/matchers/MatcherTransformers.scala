package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.manipulators.TermApplyInfixManipulator

object MatcherTransformers {

  private[matchers] lazy val logicalMatcherTransformer: MatcherTransformer = new LogicalMatcherTransformer(rootMatcherTransformer)

  private[matchers] lazy val rootMatcherTransformer: MatcherTransformer = new NestedInfixCapableMatcherTransformer(
    new CompositeMatcherTransformer(
      LazyList(
        BeMatcherTransformer,
        ContainMatcherTransformer,
        EqualMatcherTransformer,
        HaveMatcherTransformer,
        logicalMatcherTransformer,
        RegexMatcherTransformer,
        StringMatcherTransformer
      )
    ),
    TermApplyInfixManipulator
  )
}
