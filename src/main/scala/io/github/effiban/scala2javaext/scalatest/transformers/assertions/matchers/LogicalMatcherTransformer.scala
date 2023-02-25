package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AllOf, Not}

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class LogicalMatcherTransformer(nestedTransformer: => MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(q"not", List(nestedMatcher)) => transformNot(nestedMatcher)
      case Term.ApplyInfix(q"not", nestedWord, _, nestedArgs) => transformNot(nestedWord, nestedArgs)
      case Term.ApplyInfix(lhsMatcher, q"and", _, List(rhsMatcher)) => transformAnd(lhsMatcher, rhsMatcher)
      case _ => None
    }
  }

  private def transformAnd(lhsMatcher: Term, rhsMatcher: Term) = {
    (transformNested(lhsMatcher), transformNested(rhsMatcher)) match {
      case (Some(hamcrestLhsMatcher), Some(hamcrestRhsMatcher)) => Some(Term.Apply(AllOf, List(hamcrestLhsMatcher, hamcrestRhsMatcher)))
      case _ => None
    }
  }

  private def transformNot(nestedWord: Term.Name, nestedArgs: List[Term]): Option[Term] = transformNot(Term.Apply(nestedWord, nestedArgs))

  private def transformNot(nestedMatcher: Term): Option[Term] = {
    transformNested(nestedMatcher).map(nestedHamcrestMatcher => Term.Apply(Not, List(nestedHamcrestMatcher)))
  }

  private def transformNested(nestedMatcher: Term) = nestedTransformer.transform(nestedMatcher)
}
