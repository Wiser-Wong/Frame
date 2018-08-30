package com.wiser.library.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERJava {

	/**
	 * 集合是否为空
	 * 
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> boolean isEmpty(List<T> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * 判断一个集合中是否存在一个字符串
	 *
	 * @param name
	 *            字符串
	 * @param list
	 *            集合
	 * @return
	 */
	public static <T> boolean isExistSubset(T name, List<T> list) {
		return list != null && list.contains(name);
	}

	/**
	 * 判断两个集合是否相同
	 *
	 * @param listOne
	 *            第一个集合
	 * @param listTwo
	 *            第二个结合
	 * @return
	 */
	public static <T> boolean isTwoListSame(List<T> listOne, List<T> listTwo) {
		return !(listOne == null || listTwo == null) && listOne.containsAll(listTwo);
	}

	/**
	 * 返回该对象在集合中位置坐标从0开始
	 *
	 * @param name
	 *            对象
	 * @param list
	 *            对象集合
	 * @return 返回-1表示不存在，默认是从0开始
	 */
	public static <T> int listIndex(T name, List<T> list) {
		return list.indexOf(name);
	}

	/**
	 * 返回该对象在集合中位置坐标从末尾开始倒数 它和indexOf的作用是一样的。
	 *
	 * @param name
	 *            对象
	 * @param list
	 *            对象集合
	 * @return
	 */
	public static <T> int listLastIndex(T name, List<T> list) {
		return list.lastIndexOf(name);
	}

	/**
	 * set方法是在集合中指定的位置上插入一个数据
	 *
	 * @return
	 */
	public static <T> List<T> insertObj(int pos, T name, List<T> list) {
		list.set(pos, name);
		return list;
	}

	/**
	 * 删除集合中制定的对象
	 *
	 * @param t
	 *            对象
	 * @param list
	 *            对象集合
	 * @return
	 */
	public static <T> List<T> removeObj(T t, List<T> list) {
		Iterator<T> sListIterator = list.iterator();
		while (sListIterator.hasNext()) {
			T e = sListIterator.next();
			if (e.equals(t)) {
				sListIterator.remove();
			}
		}
		return list;
	}

	/**
	 * 求两个集合的差集
	 *
	 * @param listOne
	 *            第一个集合
	 * @param listTwo
	 *            第二个集合
	 * @return
	 */
	public static <T> List<T> subtractValue(List<T> listOne, List<T> listTwo) {

		List<T> oneTemp = new ArrayList<>(listOne);
		List<T> twoTemp = new ArrayList<>(listTwo);
		List<T> subtractTemp = new ArrayList<>();
		subtractTemp.addAll(oneTemp);
		subtractTemp.addAll(twoTemp);
		oneTemp.retainAll(twoTemp);
		subtractTemp.removeAll(oneTemp);

		return subtractTemp;
	}

	/**
	 * 求两个集合的交集
	 *
	 * @param listOne
	 *            第一个集合
	 * @param listTwo
	 *            第二个集合
	 * @return
	 */
	public static <T> List<T> intersectionValue(List<T> listOne, List<T> listTwo) {
		listOne.retainAll(listTwo);
		return listOne;
	}

	/**
	 * 求两个集合的并集
	 *
	 * @param listOne
	 *            第一个集合
	 * @param listTwo
	 *            第二个集合
	 * @return
	 */
	public static <T> List<T> sumValue(List<T> listOne, List<T> listTwo) {
		listOne.removeAll(listTwo);
		listOne.addAll(listTwo);
		return listOne;
	}

	/**
	 * 去掉集合中重复值
	 *
	 * @param objList
	 *            集合
	 * @return
	 */
	public static <T> List<T> resetValue(List<T> objList) {
		Set s = new HashSet(objList);
		objList.clear();
		objList.addAll(s);
		return objList;
	}

	/**
	 * 升序
	 *
	 * @param list
	 *            字符串集合
	 * @return
	 */
	public static List<String> upSortList(List<String> list) {
		if (list != null && list.size() > 0) Collections.sort(list);
		return list;
	}

	/**
	 * 降序
	 *
	 * @param list
	 *            字符串集合
	 * @return
	 */
	public static List<String> downSortList(List<String> list) {
		if (list != null && list.size() > 0) Collections.reverse(list);
		return list;
	}

}
