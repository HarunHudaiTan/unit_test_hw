package com.productManagement.demo.service;

//import org.junit.platform.suite.api.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderServiceTest.class,
        ProductServiceTest.class,
})
public class TestSuite1 {
}
