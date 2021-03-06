package games.strategy.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A utility class for mapping Objects to ints. <br>
 * Supports adding and comparing of maps.
 */
public class IntegerMap<T> implements Cloneable, Serializable {
  private static final long serialVersionUID = 6856531659284300930L;
  private final HashMap<T, Integer> mapValues;

  /** Creates new IntegerMap. */
  public IntegerMap() {
    mapValues = new HashMap<>();
  }

  public IntegerMap(final int size) {
    mapValues = new HashMap<>(size);
  }

  public IntegerMap(final int size, final float loadFactor) {
    mapValues = new HashMap<>(size, loadFactor);
  }

  public IntegerMap(final T object, final int value) {
    this();
    add(object, value);
  }

  public IntegerMap(final Collection<T> objects, final int value) {
    this(objects.size());
    addAll(objects, value);
  }

  /**
   * This will make a new IntegerMap.
   * The Objects will be linked, but the integers mapped to them will not be linked.
   */
  public IntegerMap(final IntegerMap<T> integerMap) {
    mapValues = new HashMap<>(integerMap.size());
    for (final T t : integerMap.keySet()) {
      mapValues.put(t, integerMap.getInt(t));
    }
  }

  /**
   * This will make a new IntegerMap.
   * The Objects will be linked, but the integers mapped to them will not be linked.
   */
  public IntegerMap(final IntegerMap<T>[] integerMaps) {
    mapValues = new HashMap<>();
    for (final IntegerMap<T> integerMap : integerMaps) {
      this.add(integerMap);
    }
  }

  public int size() {
    return mapValues.size();
  }

  public void put(final T key, final int value) {
    mapValues.put(key, value);
  }

  private void addAll(final Collection<T> keys, final int value) {
    final Iterator<T> iter = keys.iterator();
    while (iter.hasNext()) {
      add(iter.next(), value);
    }
  }

  /**
   * returns 0 if no key found.
   */
  public int getInt(final T key) {
    if (!mapValues.containsKey(key)) {
      return 0;
    }
    return mapValues.get(key);
  }

  public void add(final T key, final int value) {
    if (mapValues.get(key) == null) {
      put(key, value);
    } else {
      final int oldVal = mapValues.get(key);
      final int newVal = oldVal + value;
      put(key, newVal);
    }
  }

  public void add(final IntegerMap<T> map) {
    for (final T key : map.keySet()) {
      add(key, map.getInt(key));
    }
  }

  /**
   * Will multiply all values by a given double.
   * Can be used to divide all numbers, if given a fractional double
   * (ie: to divide by 2, use 0.5 as the double)
   *
   * @param roundType
   *        (1 = floor, 2 = round, 3 = ceil)
   */
  public void multiplyAllValuesBy(final double multiplyBy, final int roundType) {
    for (final T t : keySet()) {
      double val = mapValues.get(t);
      switch (roundType) {
        case 1:
          val = Math.floor(val * multiplyBy);
          break;
        case 2:
          val = Math.round(val * multiplyBy);
          break;
        case 3:
          val = Math.ceil(val * multiplyBy);
          break;
        default:
          val = val * multiplyBy;
          break;
      }
      put(t, (int) val);
    }
  }

  public void clear() {
    mapValues.clear();
  }

  public Set<T> keySet() {
    return mapValues.keySet();
  }

  public Collection<Integer> values() {
    return mapValues.values();
  }

  /**
   * If empty, will return false.
   *
   * @return true if at least one value and all values are the same.
   */
  public boolean allValuesAreSame() {
    if (mapValues.isEmpty()) {
      return false;
    }
    final int first = mapValues.values().iterator().next();
    for (final int value : mapValues.values()) {
      if (first != value) {
        return false;
      }
    }
    return true;
  }

  /**
   * If empty, will return false.
   *
   * @return true if all values are equal to the given integer.
   */
  public boolean allValuesEqual(final int integer) {
    if (mapValues.isEmpty()) {
      return false;
    }
    for (final int value : mapValues.values()) {
      if (integer != value) {
        return false;
      }
    }
    return true;
  }

  /**
   * Will return zero if empty.
   */
  public int highestValue() {
    if (mapValues.isEmpty()) {
      return 0;
    }
    int max = Integer.MIN_VALUE;
    for (final int value : mapValues.values()) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  /**
   * Will return zero if empty.
   */
  public int lowestValue() {
    if (mapValues.isEmpty()) {
      return 0;
    }
    int min = Integer.MAX_VALUE;
    for (final int value : mapValues.values()) {
      if (value < min) {
        min = value;
      }
    }
    return min;
  }

  /**
   * Will return null if empty.
   */
  public T highestKey() {
    if (mapValues.isEmpty()) {
      return null;
    }
    int maxValue = Integer.MIN_VALUE;
    T maxKey = null;
    for (final Entry<T, Integer> entry : mapValues.entrySet()) {
      if (entry.getValue() > maxValue) {
        maxValue = entry.getValue();
        maxKey = entry.getKey();
      }
    }
    return maxKey;
  }

  /**
   * Will return null if empty.
   */
  public T lowestKey() {
    if (mapValues.isEmpty()) {
      return null;
    }
    int minValue = Integer.MAX_VALUE;
    T minKey = null;
    for (final Entry<T, Integer> entry : mapValues.entrySet()) {
      if (entry.getValue() < minValue) {
        minValue = entry.getValue();
        minKey = entry.getKey();
      }
    }
    return minKey;
  }

  /**
   * @return The sum of all keys.
   */
  public int totalValues() {
    int sum = 0;
    for (final int value : mapValues.values()) {
      sum += value;
    }
    return sum;
  }

  public void subtract(final IntegerMap<T> map) {
    for (final T key : map.keySet()) {
      add(key, -map.getInt(key));
    }
  }

  /**
   * By >= we mean that each of our entries is greater
   * than or equal to each entry in the other map. We do not take into
   * account entries that are in our map but not in the second map. <br>
   * It is possible that for two maps a and b
   * a.greaterThanOrEqualTo(b) is false, and b.greaterThanOrEqualTo(a) is false, and
   * that a and b are not equal.
   */
  public boolean greaterThanOrEqualTo(final IntegerMap<T> map) {
    for (final T key : map.keySet()) {
      if (!(this.getInt(key) >= map.getInt(key))) {
        return false;
      }
    }
    return true;
  }

  /**
   * True if all values are >= 0.
   */
  public boolean isPositive() {
    for (final T key : mapValues.keySet()) {
      if (getInt(key) < 0) {
        return false;
      }
    }
    return true;
  }

  public IntegerMap<T> copy() {
    final IntegerMap<T> copy = new IntegerMap<>();
    copy.add(this);
    return copy;
  }

  @Override
  public Object clone() {
    return copy();
  }

  /**
   * Add map * multiple.
   */
  public void addMultiple(final IntegerMap<T> map, final int multiple) {
    for (final T key : map.keySet()) {
      add(key, map.getInt(key) * multiple);
    }
  }

  private Collection<T> getKeyMatches(final Match<T> matcher) {
    final Collection<T> values = new ArrayList<>();
    for (final T obj : mapValues.keySet()) {
      if (matcher.match(obj)) {
        values.add(obj);
      }
    }
    return values;
  }

  public void removeNonMatchingKeys(final Match<T> match) {
    removeMatchingKeys(match.invert());
  }

  public void removeMatchingKeys(final Match<T> match) {
    final Collection<T> badKeys = getKeyMatches(match);
    removeKeys(badKeys);
  }

  public void removeKey(final T key) {
    mapValues.remove(key);
  }

  private void removeKeys(final Collection<T> keys) {
    for (final T key : keys) {
      removeKey(key);
    }
  }

  public boolean containsKey(final T key) {
    return mapValues.containsKey(key);
  }

  public boolean isEmpty() {
    return mapValues.isEmpty();
  }

  public Set<Entry<T, Integer>> entrySet() {
    return mapValues.entrySet();
  }

  @Override
  public String toString() {
    final StringBuilder buf = new StringBuilder();
    buf.append("IntegerMap:\n");
    final Iterator<T> iter = mapValues.keySet().iterator();
    if (!iter.hasNext()) {
      buf.append("empty\n");
    }
    while (iter.hasNext()) {
      final T current = iter.next();
      buf.append(current).append(" -> ").append(getInt(current)).append("\n");
    }
    return buf.toString();
  }

  @Override
  public int hashCode() {
    return mapValues.hashCode();
  }

  /**
   * The equals method will only return true if both the keys and values
   * match exactly. If a has entries that b doesn't have or vice versa,
   * then a and b are not equal.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof IntegerMap)) {
      return false;
    }
    final IntegerMap<T> map = (IntegerMap<T>) o;
    if (!map.keySet().equals(this.keySet())) {
      return false;
    }
    if (!map.mapValues.equals(this.mapValues)) {
      return false;
    }
    for (final T key : map.keySet()) {
      if (!(this.getInt(key) == map.getInt(key))) {
        return false;
      }
    }
    return true;
  }
}
