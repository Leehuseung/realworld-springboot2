package com.kr.realworldspringboot2.article.tag;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagQueryRepository tagQueryRepository;

    @Override
    public List<String> getTagNameList() {
        List<TagDTO> list = tagQueryRepository.getTags();
        List<String> arr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            arr.add(list.get(i).getName());
        }
        return arr;
    }
}
