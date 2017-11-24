package com.lph.algorithm;

import java.util.Arrays;

/**
 * Created by lph on 2017/11/19.
 */

public class SortUtil {

    /**
     * array 22 32 99 18 20 66
     * 第一趟排序之后：18 32 99 22 20 66
     * 第二趟排序之后：18 20 99 32 22 66
     * 第三趟排序之后：18 20 22 32 99 66
     * 第四趟排序之后: 18 20 22 32 99 66
     * 第五趟排序之后: 18 20 22 32 66 99
     */

    /**
     * 选择排序
     *
     * @param array 需要排序的数组
     */
    public static void selectSort(int[] array) {
        System.out.println("selectSort:--->start" + arrayToString(array));
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = 0; i < array.length - 1; i++) {
            //每次排序选择出最小的,放在最左边
            for (int j = i + 1; j < array.length; j++) {
                //拿着当前arry[i]处的元素一依次和后面的元素比较，如果有比它小的，就交换位置
                if (array[i] > array[j]) {
                    array[i] = array[i] + array[j];
                    array[j] = array[i] - array[j];
                    array[i] = array[i] - array[j];
                }
            }
        }
        System.out.println("selectSort:----->end" + arrayToString(array));
    }

    /**
     * 冒泡排序
     * array 22 32 99 18 20 66
     * 第一趟排序之后: 22 32 18 20 66 99
     * 第二趟排序之后：22 18 20 32 66 99
     * 第三趟排序之后：18 20 22 32 66 99
     * 第四趟排序之后：18 20 22 32 66 99
     *
     * @param array 需要排序的数组
     */
    public static void bubbleSort(int[] array) {
        boolean flag;
        System.out.println("bubbleSort:--->start" + arrayToString(array));
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = 0; i < array.length - 1; i++) {
            flag = true;
            //相邻的两个元素进行比较,如果前者大于后者，就交换顺序，每次选出最大的元素,发在了数组的最右边
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    array[j] = array[j] + array[j + 1];
                    array[j + 1] = array[j] - array[j + 1];
                    array[j] = array[j] - array[j + 1];
                    flag = false;
                }
                if (flag) {
                    //表示当前的序列已经是有序的了,不需要再比较了
                    return;
                }
            }
        }
        System.out.println("bubbleSort:----->end" + arrayToString(array));
    }

    static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                sb.append(array[i])
                        .append(",");
            } else {
                sb.append(array[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 二分法查找,前提是数组必须是有序的
     *
     * @param key   要查找的元素
     * @param array 被查找的集合
     * @return 查找到元素的索引,-1表示没有找到
     */
    public static int binarySearch(int key, int[] array) {
        int low, heigh, mid;
        low = 0;
        heigh = array.length - 1;
        mid = (low + heigh) / 2;
        while (low < heigh) {
            if (key > array[mid]) {
                low = mid + 1;
            } else if (key < array[mid]) {
                heigh = mid - 1;
            } else {
                return mid;
            }
            mid = (low + heigh) / 2;
        }
        return array[mid] == key ? mid : -1;
    }

}
