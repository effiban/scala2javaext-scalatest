package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.ScalatestDefnDefTransformer.transform

import scala.meta.{Defn, Mod, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}
class ScalatestDefnDefTransformerTest extends UnitTestSuite {

  private val InputBeforeEach =
    q"""
    override protected def beforeEach(): Unit = {
    }
    """

  private val ExpectedOutputBeforeEach =
    q"""
    @BeforeEach
    override protected def beforeEach(): Unit = {
    }
    """

  private val InputAfterEach =
    q"""
    override protected def afterEach(): Unit = {
    }
    """

  private val ExpectedOutputAfterEach =
    q"""
    @AfterEach
    override protected def afterEach(): Unit = {
    }
    """

  private val InputBeforeAll =
    q"""
    override protected def beforeAll(): Unit = {
    }
    """

  private val ExpectedOutputBeforeAll =
    q"""
    @BeforeAll
    override protected def beforeAll(): Unit = {
    }
    """

  private val InputAfterAll =
    q"""
    override protected def afterAll(): Unit = {
    }
    """

  private val ExpectedOutputAfterAll =
    q"""
    @AfterAll
    override protected def afterAll(): Unit = {
    }
    """

  private val OrdinaryDefnDef =
    q"""
    def doSomething(): Unit = {
    }
    """


  private val TransformedScenarios = Table(
    ("Input Method Name", "Input Method", "Expected Annotation", "Expected Output Method"),
    ("beforeEach", InputBeforeEach, mod"@BeforeEach", ExpectedOutputBeforeEach),
    ("afterEach", InputAfterEach, mod"@AfterEach", ExpectedOutputAfterEach),
    ("beforeAll", InputBeforeAll, mod"@BeforeAll", ExpectedOutputBeforeAll),
    ("afterAll", InputAfterAll, mod"@AfterAll", ExpectedOutputAfterAll)
  )

  forAll(TransformedScenarios) { case (
    inputName: String,
    inputDefnDef: Defn.Def,
    expectedAnnotation: Mod.Annot,
    expectedOutputDefnDef: Defn.Def) =>
    test(s"The method '$inputName' should be transformed to the same method annotated with '$expectedAnnotation'") {
      transform(inputDefnDef).structure shouldBe expectedOutputDefnDef.structure
    }
  }

  test("An ordinary method should not be transformed") {
    transform(OrdinaryDefnDef).structure shouldBe OrdinaryDefnDef.structure
  }
}
