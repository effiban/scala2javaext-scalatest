package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.DefnDefTransformer

import scala.meta.{Defn, Mod, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

object ScalatestDefnDefTransformer extends DefnDefTransformer {

  override def transform(defnDef: Defn.Def): Defn.Def = {
    defnDef.name match {
      case q"beforeEach" => annotateWith(defnDef, mod"@org.junit.jupiter.api.BeforeEach")
      case q"afterEach" => annotateWith(defnDef, mod"@org.junit.jupiter.api.AfterEach")
      case q"beforeAll" => annotateWith(defnDef, mod"@org.junit.jupiter.api.BeforeAll")
      case q"afterAll" => annotateWith(defnDef, mod"@org.junit.jupiter.api.AfterAll")
      case _ => defnDef
    }
  }

  private def annotateWith(defnDef: Defn.Def, annotation: Mod.Annot): Defn.Def = {
    defnDef.copy(mods = annotation +: defnDef.mods)
  }
}
