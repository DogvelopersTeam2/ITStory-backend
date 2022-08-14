package com.blog.itstory.domain.post.service;

import com.blog.itstory.domain.comment.repository.CommentRepository;
import com.blog.itstory.domain.comment.service.CommentService;
import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;

    /**
     * 모든 Post 를 반환하는 메소드이다.
     * 특정 Dto 로 변환하고 싶을 경우 일단 이대로 받은 다음에
     * DTO 클래스의 of() from() 정적 팩토리 메소드로 변환하자.
     * domain 패키지의 서비스는 특정 DTO 를 모르게 하기.
     */
    // readOnly
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public Post save(Post newPost) {
        return postRepository.save(newPost);
    }

    // repository.save()는 Transactional 이 없어도 되지만,
    // delete 관련 메소드는 동작하지 않음
    @Transactional
    public void deleteById(Long postId) {

        Post post = postRepository.findById(postId).get();

        //  글을 삭제하기 위해, 연관 댓글들을 모두 DB에서 내려야 한다.
        commentService.deleteAllByPost(post);

        //  게시글 삭제!
        postRepository.delete(post);
    }

    // 당장은 ApiPostService.updatePost() 로만 호출된다고 해도, 단독 사용될 수도 있기에
    // @Transactional 사용한다.
    @Transactional
    public Post updatePost(Long postId, Post updatePost) {

        /**
         *  이 메소드를 처음 만드는 시점은 ApiPostService.updatePost()를 생성하는 과정에서 만들었다.
         *  그러나 domain 계층의 Service 클래스를 개발중이기 때문에, 단독 사용을 가정하고 개발한다.
         */
        Post post = postRepository.findById(postId).get(); // DB 에서 조회해서 컨텍스트에 올린 post객체

        // 컨텍스트에 없는 updatePost 객체를 파라미터로 받아, 컨텍스트에 있는 객체인 post를 업데이트한다(변경감지).
        post.updatePost(updatePost);
        return post;
    }

    public Page<Post> findAllByCategory(Category category, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByCategory(category, pageable);
        return posts;
    }

    public Page<Post> findAll(String postContent, String postTitle, Pageable pageable) {
        return postRepository.findAllByPostContentIgnoreCaseContainingOrPostTitleContainingIgnoreCase(postContent, postTitle, pageable);
    }
}




















