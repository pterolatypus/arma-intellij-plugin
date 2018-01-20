package com.kaylerrenslow.armaplugin.lang.sqf.psi;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * @author Kayler
 * @since 09/18/2017
 */
public class SQFIfHelperStatement implements SQFControlStructure {
	@NotNull
	private final SQFStatement statement;
	@NotNull
	private final SQFExpression conditionExpr;
	@NotNull
	private final SQFBlockOrExpression thenBlock;
	@Nullable
	private final SQFBlockOrExpression elseBlock;

	public SQFIfHelperStatement(@NotNull SQFStatement statement, @NotNull SQFExpression conditionExpr, @NotNull SQFBlockOrExpression thenBlock, @Nullable SQFBlockOrExpression elseBlock) {
		this.statement = statement;
		this.conditionExpr = conditionExpr;
		this.thenBlock = thenBlock;
		this.elseBlock = elseBlock;
	}

	/**
	 * @return the {@link SQFStatement} that contains the if statement
	 */
	@NotNull
	public SQFStatement getStatement() {
		return statement;
	}

	@NotNull
	public SQFExpression getCondition() {
		return conditionExpr;
	}

	@NotNull
	public SQFBlockOrExpression getThen() {
		return thenBlock;
	}

	@Nullable
	public SQFBlockOrExpression getElse() {
		return elseBlock;
	}
}
