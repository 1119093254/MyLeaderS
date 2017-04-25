package com.multshows.Interfaces;

import com.multshows.Beans.Shows;
import com.multshows.Beans.WorkAll_Beans;
/**
 * 描述：主页评论回调
 * 作者：李萧萧
 * 时间：2016.10.28
 */
public interface CommentInterface {
    void execute(String workId,String TargetUserId,String userid,String ReplyId);//ReplyId 传“0”代表是一级评论，否则为二级评论
}
