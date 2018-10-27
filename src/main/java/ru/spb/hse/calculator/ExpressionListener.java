// Generated from /home/karvozavr/Dropbox/studies/au/term5/jvm/scala-2018/src/main/antlr/Expression.g4 by ANTLR 4.7
package ru.spb.hse.calculator;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#calc}.
	 * @param ctx the parse tree
	 */
	void enterCalc(ExpressionParser.CalcContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#calc}.
	 * @param ctx the parse tree
	 */
	void exitCalc(ExpressionParser.CalcContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ExpressionParser.ExpressionContext ctx);
}