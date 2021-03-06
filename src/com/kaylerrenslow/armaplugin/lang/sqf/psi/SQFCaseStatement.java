package com.kaylerrenslow.armaplugin.lang.sqf.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import com.kaylerrenslow.armaplugin.lang.sqf.syntax.CommandDescriptorCluster;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Kayler
 * @since 05/23/2017
 */
public class SQFCaseStatement extends SQFStatement {
	public SQFCaseStatement(@NotNull ASTNode node) {
		super(node);
	}

	/**
	 * @return the expression or null if doesn't exist. Will return null because of pin in grammar.
	 */
	@Nullable
	public SQFExpression getCondition() {
		return PsiTreeUtil.getChildOfType(this, SQFExpression.class);
	}

	/**
	 * @return the code block, or null if doesn't exist. Can return null because case 0; is valid
	 */
	@Nullable
	public SQFCodeBlock getBlock() {
		return PsiTreeUtil.getChildOfType(this, SQFCodeBlock.class);
	}

	@NotNull
	@Override
	public Object accept(@NotNull SQFSyntaxVisitor visitor, @NotNull CommandDescriptorCluster cluster) {
		return visitor.visit(this, cluster);
	}
}
