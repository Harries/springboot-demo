package com.et.license;

import com.et.license.license.LicenseVerify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrueLicenseApplicationTests {
    @Autowired
    private LicenseVerify licenseVerify;


    @Test
    void contextLoads() {
        System.out.println("licese是否有效：" + licenseVerify.verify());
    }
}


