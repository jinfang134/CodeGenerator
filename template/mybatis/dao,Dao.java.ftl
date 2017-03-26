package ${table.packagename}.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import ${table.packagename}.entity.${table.classname };
import org.apache.ibatis.annotations.Param;
public interface ${table.classname }Dao {

	/**
	* 计数
	*/
	public int count();
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public ${table.classname } get${table.classname }ById(long id);
	/**
	 * 插入一条新的记录
	 * @param Successkilled
	 * @return
	 */
	public int insert${table.classname }(${table.classname } ${table.classname });

	/**
	* 删除指定id的记录
	* @param id id值
	* @return 返回删除的记录数
	*/
	public int delete${table.classname }(long id);

	/**
	 * 更新一条记录
	 * @param Successkilled
	 * @return 如果返回值》=1，更新成功，否则失败
	 */
	public int update${table.classname }(${table.classname } ${table.classname });

	/**
	 * 根据偏移量查找所有的记录
	 * @param offset 偏移量
	 * @param limit 最大记录数
	 * @return 返回查到的记录
	 */
	public List<${table.classname }> queryAll(@Param("offset") long offset,@Param("limit")int limit);

}
