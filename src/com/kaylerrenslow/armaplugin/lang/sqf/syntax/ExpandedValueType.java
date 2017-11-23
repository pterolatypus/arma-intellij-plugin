package com.kaylerrenslow.armaplugin.lang.sqf.syntax;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A way of expanding {@link ValueType} instances from something like {@link ValueType.Lookup#COLOR_RGB}
 * to {NUMBER, NUMBER, NUMBER}. Note that this type isn't just for arrays. This type can contain a single {@link ValueType.Lookup}
 * instance.
 *
 * @author Kayler
 * @since 11/18/2017
 */
public class ExpandedValueType implements ValueType {
	@NotNull
	private final List<ValueType> valueTypes;
	private final boolean isUnbounded;
	private int numOptionalValues;

	/**
	 * Create an instance with the specified {@link ValueType} instances. This will set {@link #getNumOptionalValues()} to 0.
	 *
	 * @param isUnbounded true if the last element in valueTypes is repeating, false otherwise
	 * @param valueTypes  value types to use (this list will be used internally for this class)
	 */
	protected ExpandedValueType(boolean isUnbounded, @NotNull List<ValueType> valueTypes) {
		this.isUnbounded = isUnbounded;
		this.valueTypes = valueTypes;
		numOptionalValues = 0;
	}

	/**
	 * Create an instance with the specified {@link ValueType} instances.
	 * Every value type provided will be marked as required.
	 * <p>
	 * This will invoke {@link ExpandedValueType#ExpandedValueType(boolean, ValueType...)} with unbounded set to false.
	 *
	 * @param valueTypes value types to use
	 */
	public ExpandedValueType(@NotNull ValueType... valueTypes) {
		this(false, valueTypes);
	}

	/**
	 * Create an instance with the specified {@link ValueType} instances. This will set {@link #getNumOptionalValues()} to 0.
	 *
	 * @param isUnbounded true if the last element in valueTypes is repeating, false otherwise
	 * @param valueTypes  value types to use
	 */
	public ExpandedValueType(boolean isUnbounded, @NotNull ValueType... valueTypes) {
		this.isUnbounded = isUnbounded;

		this.valueTypes = new ArrayList<>(valueTypes.length);
		Collections.addAll(this.valueTypes, valueTypes);
		numOptionalValues = 0;
	}

	/**
	 * @return true if this type contains more than one {@link ValueType} or {@link #isUnbounded()} is true,
	 * or {@link #isEmptyArray()} is true.
	 */
	public boolean isArray() {
		return isUnbounded || valueTypes.size() > 1 || isEmptyArray();
	}

	/**
	 * @return mutable list containing value types
	 */
	@NotNull
	public List<ValueType> getValueTypes() {
		return valueTypes;
	}

	/**
	 * Get how many optional values from {@link #getValueTypes()} there are. If 0, all values are required.
	 * If >0, then the optional values are from the end of {@link #getValueTypes()} until this value.
	 * For example, if this returned 1, then the last value is optional. If returns 2, the last 2 are optional.
	 * Etc. If this returns a value >= <code>{@link #getValueTypes()}.size()</code>, then all values are optional.
	 * <p>
	 * Note that if this returns a number >1, then either the last parameter can be omitted, or the last 2. You cannot omit
	 * a value in between other optional values!
	 *
	 * @return number of optional values (>=0)
	 */
	public int getNumOptionalValues() {
		return numOptionalValues;
	}

	/**
	 * Sets {@link #getNumOptionalValues()}
	 *
	 * @param numOptionalValues the new amount of optional value count
	 * @throws IllegalArgumentException when numOptionalValues < 0
	 */
	public void setNumOptionalValues(int numOptionalValues) {
		if (numOptionalValues < 0) {
			throw new IllegalStateException("numOptionalValues is <0");
		}
		this.numOptionalValues = numOptionalValues;
	}

	/**
	 * Adds a {@link ValueType} to {@link #getValueTypes()}
	 */
	public void addValueType(@NotNull ValueType type) {
		valueTypes.add(type);
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return valueTypes.size() == 1 ? valueTypes.get(0).getDisplayName() : valueTypes.toString();
	}

	/**
	 * @return this
	 */
	@NotNull
	@Override
	public ExpandedValueType getExpanded() {
		return this;
	}

	/**
	 * An unbounded array is where the number of elements are between 0 and +infinity.
	 * The last element of an unbounded array is what repeats. If an array is empty and unbounded is true, then this {@link ExpandedValueType}
	 * is representing any size array that can contain literally anything or contain nothing at all.
	 * <p>
	 * If this returns false and {@link #getValueTypes()} is empty, {@link #isEmptyArray()} will return true.
	 *
	 * @return true if the last element in {@link #getValueTypes()} is repeating, false otherwise.
	 */
	public boolean isUnbounded() {
		return isUnbounded;
	}

	/**
	 * @return true if {@link #getValueTypes()} is empty and {@link #isUnbounded()} is false. Otherwise, returns false.
	 */
	public boolean isEmptyArray() {
		return !isUnbounded && valueTypes.isEmpty();
	}

	/**
	 * If obj is an instance of {@link ExpandedValueType}, objects are equal if {@link #getValueTypes()} are equal
	 * (same size and order) and if {@link #isUnbounded()} are equal.
	 * <p>
	 * If obj is an instance of {@link ValueType.Lookup}, the objects are equal if:
	 * <ul>
	 * <li>{@link #getValueTypes()} size is 0, {@link #isArray()} is true, and {@link ValueType.Lookup#ARRAY} is obj</li>
	 * <li>If {@link #getValueTypes()} size is 1 and {@link #getValueTypes()}.get(0) == obj</li>
	 * </ul>
	 *
	 * @return true if equal, false if not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Lookup) {
			if (isArray() && valueTypes.isEmpty()) {
				return obj == Lookup.ARRAY;
			}
			return valueTypes.size() == 1 && valueTypes.get(0) == obj;
		}
		if (obj instanceof ExpandedValueType) {
			ExpandedValueType other = (ExpandedValueType) obj;

			if (getValueTypes().size() != other.getValueTypes().size()) {
				return false;
			}

			Iterator<ValueType> myTypes = getValueTypes().iterator();
			Iterator<ValueType> otherTypes = other.getValueTypes().iterator();
			while (myTypes.hasNext()) {
				if (!myTypes.next().equals(otherTypes.next())) {
					return false;
				}
			}
			return this.isUnbounded == other.isUnbounded;
		}
		return false;
	}

	@NotNull
	public String debugToString() {
		return "ExpandedValueType{" +
				"valueTypes=" + valueTypes +
				", isUnbounded=" + isUnbounded +
				'}';
	}

	@Override
	public String toString() {
		return valueTypes.toString();
	}
}
