package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers
import io.github.effiban.scala2javaext.scalatest.manipulators.TermApplyInfixManipulator

import scala.meta.Term

class NestedInfixCapableMatcherTransformer(innerTransformer: => MatcherTransformer,
                                           termApplyInfixManipulator: TermApplyInfixManipulator) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import termApplyInfixManipulator._

    innerTransformer.transform(matcher).orElse {
      matcher match {
        case infix: Term.ApplyInfix => rightShiftInnerIfPossible(infix).flatMap(transform)
        case _ => None
      }
    }
  }
}
