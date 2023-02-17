package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import scala.meta.Term

trait MatcherTransformer {

  def transform(matcher: Term): Option[Term]
}