package com.blog.itstory.domain.post.service;

import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     *  모든 Post 를 반환하는 메소드이다.
     *  특정 Dto 로 변환하고 싶을 경우 일단 이대로 받은 다음에
     *  DTO 클래스의 of() from() 정적 팩토리 메소드로 변환하자.
     *  domain 패키지의 서비스는 특정 DTO 를 모르게 하기.
     */
    // readOnly
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post save(Post newPost) {
        return postRepository.save(newPost);
    }
}


















