package ru.spb.hse.calculator

import org.antlr.v4.runtime._
import ru.spb.hse.calculator.ExpressionParser.CalcContext

object Calculator {
  private val EXIT = "exit"
  private val evaluator = new Evaluator()
  private val prefix = "> "

  def main(args: Array[String]): Unit = {
    println("Arithmetic and logic evaluator:")
    var input = readNextInput()

    while (input.toLowerCase() != EXIT) {
      try {
        println(eval(input))
      } catch {
        case e: ParserException => println(e.getMessage)
      }

      input = readNextInput()
    }
  }

  def eval(expr: String): Int =
    parse(expr).accept(evaluator)

  private def parse(expr: String): CalcContext = {
    val lexer = new ExpressionLexer(CharStreams.fromString(expr))
    lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
    lexer.addErrorListener(new ErrorListener())
    val parser = new ExpressionParser(new CommonTokenStream(lexer))
    lexer.removeErrorListener(ConsoleErrorListener.INSTANCE)
    parser.addErrorListener(new ErrorListener())
    parser.calc()
  }

  private def readNextInput(): String = {
    print(prefix)
    scala.io.StdIn.readLine()
  }
}

private class ParserException(msg: String) extends RuntimeException(msg)

private class ErrorListener extends BaseErrorListener {

  override def syntaxError(recognizer: Recognizer[_, _],
                           offendingSymbol: Any,
                           line: Int,
                           charPositionInLine: Int,
                           msg: String, e: RecognitionException): Unit =
    throw new ParserException(s"Parsing error at line $line:$charPositionInLine. Reason: $msg")
}
