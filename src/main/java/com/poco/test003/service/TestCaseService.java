package com.poco.test003.service;

import com.poco.test003.entity.TestCase;
import com.poco.test003.mapper.TestCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TestCaseService {
    @Autowired
    private TestCaseMapper testCaseMapper;

    public  int saveTestCase(TestCase testCase) {
        if (testCase.getTestcaseid() == null) {
            // 测试用例ID为空，表示需要新增
           return  testCaseMapper.insert(testCase);
        } else {
            // 测试用例ID不为空，表示需要更新
          return  testCaseMapper.updatecase(testCase);
        }
    }
}





