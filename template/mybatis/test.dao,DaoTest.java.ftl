package ${table.packagename}.test.dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import com.jinfang.${table.classname }.dao.${table.classname }Dao;
import com.jinfang.${table.classname }.entity.${table.classname };

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class ${table.classname }DaoTest extends Assert{
	
	@Resource
	private ${table.classname }Dao ${table.classname }Dao;

	//默认方法
	
	@Test
	public void testCount(){
		int count=${table.classname }Dao.count();
		isTrue(count>0);
		System.out.println(count);
	}
	
	@Test
	public void testGet${table.classname }ById(){
		${table.classname } entity=${table.classname }Dao.get${table.classname }ById(1001l);
		notNull(entity);
		System.out.println("查到的对象为："+entity);
	}
	
	@Test
	public void testInsert${table.classname }(){
		${table.classname } se=new ${table.classname }();
		se.setName("hello");
		se.setNumber(100);
		se.setStarttime(new Date());
		se.setEndtime(new Date());
		int num=${table.classname }Dao.insert${table.classname }(se);
		System.out.println(num);
		System.out.println("新生成的id为："+se.get${table.classname }id());
	}

	@Test
	public void testDelete${table.classname }(){
		int num=${table.classname }Dao.delete${table.classname }(1l);
		System.out.println("删除了"+num+"个对象");
	}

	@Test
	public void testUpdate${table.classname }(){
		${table.classname } entity=${table.classname }Dao.get${table.classname }ById(1001l);
		entity.setName("hello");
		int num=${table.classname }Dao.update${table.classname }(entity);
		System.out.println("更新数量："+num);
		isTrue(num>0);
	}

	@Test
	public void testQueryAll(){
		List<${table.classname }> list=${table.classname }Dao.queryAll(0, 100);
		notNull(list);
		System.out.print("查到的列表");
		for(${table.classname } entity:list){
			System.out.print(kill);
		}
	}
	
	//以下是用户自定义方法
	
	
	
}
