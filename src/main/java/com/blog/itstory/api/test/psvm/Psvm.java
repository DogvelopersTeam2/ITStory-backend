package com.blog.itstory.api.test.psvm;

import com.blog.itstory.domain.post.constant.Category;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class Psvm {
    public static void main(String[] args) {
        //Instant instant = Instant.now().plusSeconds(3600 * 9);
        String uri = "/post/96/comment";

        String[] split = uri.split("/");
        System.out.println("split = " + split.length);
        System.out.println("split[0] = " + split[0].equals(""));

        System.out.println((split[1] +split[3]));
    }
}
