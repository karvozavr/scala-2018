package ru.spb.hse.calculator

import org.antlr.v4.runtime._
import ru.spb.hse.calculator.ExpressionParser.CalcContext

object Calculator {
  private val EXIT = "exit"
  private val evaluator = new Evaluator()
  private val prefix = "> "

  def main(args: Array[String]): Unit = {
    println("Arithmetic and logic evaluator:")
    var input = readNextInput

    while (input.toLowerCase() != EXIT) {
      try {
        println(eval(input))
      } catch {
        case _: Throwable => println("Incorrect expression.")
      }

      input = readNextInput
    }
  }

  def eval(expr: String): Int =
    parse(expr).accept(evaluator)

  private def parse(expr: String): CalcContext = {
    val lexer = new ExpressionLexer(CharStreams.fromString(expr))
    val parser = new ExpressionParser(new CommonTokenStream(lexer))
    parser.calc()
  }

  private def readNextInput: String = {
    print(prefix)
    scala.io.StdIn.readLine()
  }
}
