package cn.konat4.blog.dao;

import java.util.List;

import cn.konat4.blog.entity.BlogConfig;

public interface BlogConfigMapper {
    List<BlogConfig> selectAll();

    BlogConfig selectByPrimaryKey(String configName);

    int updateByPrimaryKeySelective(BlogConfig record);

}