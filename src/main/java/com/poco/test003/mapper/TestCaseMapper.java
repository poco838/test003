package com.poco.test003.mapper;

import com.poco.test003.entity.TestCase;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Transactional
@Repository
@Component
public interface TestCaseMapper<testcaseids> {
    //查询所有数据

    @Select("select  * from testcase")
    List<TestCase> findAll();
    @Insert("INSERT INTO test.testcase (testcasename, testcasemodule, testcaserunstep, testcaseexpectedresult,\n" +
           "                            testcaserunresult, testcaseactualresult)\n" +
           "VALUES (#{testcasename},#{testcasemodule}, #{testcaserunstep},#{testcaseexpectedresult},#{testcaserunresult},#{testcaseactualresult})")
     int insert(TestCase testCase);
   /* @Update("update testcase set testcasename=#{testcasename}, testcasemodule=#{testcasemodule}, testcaserunstep=#{testcaserunstep}, testcaseexpectedresult=#{testcaseexpectedresult}, " +
            "testcaserunresult=#{testcaserunresult}, testcaseactualresult=#{testcaseactualresult} where testcaseid=#{testcaseid}")

    */
    //上面注释的代码因为在Mapper的TestCase.xml已经配置，此处注释掉
    int updatecase(TestCase testCase);

   @Delete("delete from testcase where testcaseid=#{testcaseid}")
    Integer deletebyId(@Param("testcaseid") Integer testcaseid);

    @Select("SELECT * FROM testcase WHERE testcaseid LIKE CONCAT('%',#{arg0},'%') AND testcasename LIKE CONCAT('%',#{arg1},'%') AND testcasemodule LIKE CONCAT('%',#{arg2},'%') LIMIT #{arg3}, #{arg4}")
    List<TestCase> selectPage(String testcaseid, String testcasename, String testcasemodule, Integer pageNum, Integer pageSize);
   /*
   @Select("SELECT * FROM testcase WHERE testcaseid LIKE #{arg0} AND testcasename LIKE #{arg1} AND testcasemodule LIKE #{arg2} LIMIT #{arg3}, #{arg4}")
   //原查询条件   select * from testcase  limit #{arg0},#{arg1}  仅能实现分页，不能实现带条件查询
    List<TestCase> selectPage(Integer pageNum, Integer pageSize);  */
    @Select("select count(*) from testcase  WHERE testcaseid LIKE CONCAT('%',#{arg0},'%') AND testcasename LIKE CONCAT('%',#{arg1},'%') AND testcasemodule LIKE CONCAT('%',#{arg2},'%')")
    Integer selectCaseTotal(String testcaseid, String testcasename, String testcasemodule);
    //上面代码中注解testcaseid传到sql中的testcaseid

    @Delete(" DELETE FROM testcase\n" +
            "    WHERE testcaseid IN\n" +
            "    <foreach collection=\"testcaseids\" item=\"testcaseid\" open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{testcaseid}\n" +
            "    </foreach>")
    Integer deleteByIds(@Param("testcaseids") Integer[] testcaseids);

  //下面代码实现导出数据
  @Select("SELECT * FROM testcases")
  List<TestCase> getAllTestcases();

    // 其他SQL语句...

    // 其他方法...

    // 添加导出到Excel的方法
    @Select("SELECT * FROM testcase")
    List<TestCase> getTestcasesForExport();













}

