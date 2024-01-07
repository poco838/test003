


package com.poco.test003.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.poco.test003.entity.TestCase;
import com.poco.test003.mapper.TestCaseMapper;
import com.poco.test003.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

//下面实现测试用例全量数据查询 1.注解@RestController  2.@Autowired  private TestCaseMapper testCaseMapper;
//3. @GetMapping
@RestController
@ResponseBody
@RequestMapping("/testcase")
public class TestCaseController {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TestCaseMapper testCaseMapper;
    //引入service   TestCase ，引入后在下面的测试用例接口请求中保存测试用例
    @Autowired
    private TestCaseService testCaseService;
       //编写了一个savetest方法，通过请求的TestCase testCase  使其转为json数据@RequestBody传给savetest
    //通过接口传进来的数据给到testcaes，再将testcase数据写入到TestcaseMapper中的 testCase，使这段代码生效，
    // int insert(TestCase testCase);
    @PostMapping("/testcasepage")
    public Integer saveTestCase(@RequestBody TestCase testCase)
    {
        //新增和更新都使用saveTestCase方法
       return testCaseService.saveTestCase(testCase);   //此处使用TestCaseService中的saveTestCase保存测试用例

    }
    @GetMapping
    public List<TestCase> indexcase()
    {  //查询所有testcase方法
        //TestCase testCase= new TestCase();

        // testCase.setTest_case_id(1);  //通过调用entity包下的testCase中的setTest_case_id方法
        return testCaseMapper.findAll();
    }



    @DeleteMapping("/testcasepage/{testcaseid}")

    //使用testcasemapper中的deletebuid方法，传值为testcaseid
    public Integer delete(@PathVariable Integer testcaseid)
    {
        testCaseMapper.deletebyId(testcaseid);
        return testcaseid;
    }
    //下面实现批量删除
    @PostMapping("/testcasepage/batch")
    public ResponseEntity<Integer[]> deleteBatch(@RequestBody Map<String, Object> requestBody) {
        Integer[] testcaseids = objectMapper.convertValue(requestBody.get("testcaseids"), Integer[].class);
        testCaseMapper.deleteByIds(testcaseids);
        return ResponseEntity.ok().body(testcaseids);
    }


    @GetMapping("/testcasepage")   //测试用例分页查询路径  /testcase/testcasepage
    //@RequestParam  接收pagenum和pagesize
    public Map<String, Object> findtestcasepage(@RequestParam String testcaseid,@RequestParam String testcasename,@RequestParam String testcasemodule,@RequestParam Integer pageNum,@RequestParam Integer pageSize)
    {
        pageNum = (pageNum-1) * pageSize;


        List<TestCase>data =testCaseMapper.selectPage( testcaseid,testcasename,testcasemodule,pageNum,pageSize);
        Integer testcasetotal =testCaseMapper.selectCaseTotal(testcaseid,testcasename,testcasemodule);
        Map<String, Object> res = new HashMap<>();
        res.put("testcasetotal",testcasetotal);
        res.put("data",data);

        return  res;

    }

    //下面代码实现testcase导出


    @GetMapping("/export")
    public void exportTestcases(HttpServletResponse response) throws Exception {
        String filename = "testcases.xlsx"; // 导出文件名
        List<TestCase> testcases = testCaseMapper.getTestcasesForExport(); // 获取测试用例列表
        // 设置导出格式为xlsx，创建ExcelWriter对象
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 添加表头别名（字段名映射到Excel表头）
        writer.addHeaderAlias("testcaseid", "测试用例ID");
        writer.addHeaderAlias("testcasename", "测试用例名称");
        writer.addHeaderAlias("testcasemodule", "测试用例模块");
        writer.addHeaderAlias("testcaserunstep", "测试用例步骤");
        writer.addHeaderAlias("testcaseexpectedresult", "预期结果");
        writer.addHeaderAlias("testcaserunresult", "测试用例运行结果");
        writer.addHeaderAlias("testcaseactualresult", "实际结果");


        // 写入测试用例数据
        writer.write(testcases);
        // 设置响应头，告诉浏览器下载文件
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        // 刷新缓冲区，将Excel数据写入响应流
        writer.flush(response.getOutputStream());
        // 关闭ExcelWriter对象
        writer.close();
    }
    //下面代码实现导入excel功能
    @PostMapping("/import")
    public boolean importtestcase (MultipartFile file) throws  Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);
        List<TestCase> list = reader.readAll(TestCase.class);
        System.out.println(list);
        for (TestCase testCase : list) {
            saveTestCase(testCase);
        }
        return true;
    }
}



