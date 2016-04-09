package com.kaylerrenslow.a3plugin.lang.header.exception;

/**
 * @author Kayler
 * Exception for when the description.ext file doesn't exist
 * Created on 04/08/2016.
 */
public class DescriptionExtNotDefinedException extends GenericConfigException{
	private static final String MESSAGE = "description.ext is not defined";

	public DescriptionExtNotDefinedException() {
		super(MESSAGE);
	}
}
