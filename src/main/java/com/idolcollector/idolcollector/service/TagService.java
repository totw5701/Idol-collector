package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.domain.posttag.PostTagRepository;
import com.idolcollector.idolcollector.domain.tag.Tag;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import com.idolcollector.idolcollector.web.dto.tag.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    public List<TagResponseDto> findAllinPost(Long postId) {
        List<Tag> rowTags = tagRepository.findAllByPostId(postId);

        List<TagResponseDto> tags = new ArrayList<>();
        for (Tag rowTag : rowTags) {
            tags.add(new TagResponseDto(rowTag));
        }

        return tags;
    }

    private Tag createTags(String name) {
        
        String tagName = name.toLowerCase();

        Optional<Tag> find = tagRepository.findByName(tagName);
        
        if (find.isEmpty()) {
            return tagRepository.save(new Tag(tagName));
        } else {
            return find.get();
        }

    }

    @Transactional
    public void createPostTag(List<String> tags, Post post) {

        for (String tagName : tags) {
            Tag tag = createTags(tagName);

            PostTag pt = new PostTag(post, tag);
            postTagRepository.save(pt);
        }
    }
}
