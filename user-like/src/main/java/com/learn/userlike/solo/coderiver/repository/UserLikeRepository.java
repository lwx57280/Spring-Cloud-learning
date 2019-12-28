package com.learn.userlike.solo.coderiver.repository;

import com.learn.userlike.solo.coderiver.dataobject.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userLikeRepository")
public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {

    Page<UserLike> findByLikedUserIdAndStatus(String likedUserId, Integer status, Pageable pageable);

    Page<UserLike> findByLikedPostIdAndStatus(String likedPostId, Integer status, Pageable pageable);

    UserLike findByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);

}
