package com.blog.itstory;

import com.blog.itstory.domain.post.constant.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItstoryApplicationTests {

    @Test
    public void enumTest(){
        Category category = Category.valueOf("category");
        System.out.println("category = " + category);
    }

}
