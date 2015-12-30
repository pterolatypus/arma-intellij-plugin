package com.kaylerrenslow.plugin.lang.sqf;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.kaylerrenslow.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by Kayler on 10/31/2015.
 */
public class SQFFileType extends LanguageFileType{
	public static final SQFFileType INSTANCE = new SQFFileType();

	public SQFFileType() {
		super(SQFLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return SQFStatic.NAME;
	}

	@NotNull
	@Override
	public String getDescription() {
		return SQFStatic.DESCRIPTION;
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return SQFStatic.FILE_EXTENSION_DEFAULT;
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return Plugin.ICON_FILE;
	}



}
