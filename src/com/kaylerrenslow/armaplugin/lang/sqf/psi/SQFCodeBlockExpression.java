package com.kaylerrenslow.armaplugin.lang.sqf.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kayler
 * @since 05/23/2017
 */
public class SQFCodeBlockExpression extends ASTWrapperPsiElement implements SQFExpression {
	public SQFCodeBlockExpression(@NotNull ASTNode node) {
		super(node);
	}

	@NotNull
	public SQFCodeBlock getCodeBlock() {
		return null;
	}
}
