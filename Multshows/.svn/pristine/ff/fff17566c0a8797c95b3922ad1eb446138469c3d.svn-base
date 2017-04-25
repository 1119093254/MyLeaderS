package com.multshows.Utils;

/**
 * 对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
import com.multshows.Beans.Friends_friend_Beans;
import java.util.Comparator;

/**
 *
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<Friends_friend_Beans> {

    public int compare(Friends_friend_Beans o1, Friends_friend_Beans o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
