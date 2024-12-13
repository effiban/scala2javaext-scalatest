package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.DefnDefTransformer

import scala.meta.{Defn, Mod, Term, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

object ScalatestDefnDefTransformer extends DefnDefTransformer {

  override def transform(defnDef: Defn.Def): Defn.Def = {
    defnDef.name match {
      case q"beforeEach" => renameAndAnnotate(defnDef, "doBeforeEach", mod"@org.junit.jupiter.api.BeforeEach")
      case q"afterEach" => renameAndAnnotate(defnDef, "doAfterEach", mod"@org.junit.jupiter.api.AfterEach")
      case q"beforeAll" => renameAndAnnotate(defnDef, "doBeforeAll", mod"@org.junit.jupiter.api.BeforeAll")
      case q"afterAll" => renameAndAnnotate(defnDef, "doAfterAll", mod"@org.junit.jupiter.api.AfterAll")
      case _ => defnDef
    }
  }

  private def renameAndAnnotate(defnDef: Defn.Def,
                                newName: String,
                                annotation: Mod.Annot): Defn.Def = {
    defnDef.copy(
      mods = annotation +: defnDef.mods,
      name = Term.Name(newName)
    )
  }
}
