package moe.ahao.spring.boot.mybatis.tk.module.mapper;


import moe.ahao.spring.boot.mybatis.tk.CommonMapper;
import moe.ahao.spring.boot.mybatis.tk.module.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

public interface UserMapper extends CommonMapper<User> {
    @Select("select * from user;")
    Cursor<User> selectCursorAll();
}
