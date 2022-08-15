package com.kr.realworldspringboot2.article.tag;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/api/tags")
    public JSONObject tagList() {
        JSONObject jo = new JSONObject();
        jo.put("tags",tagService.getTagNameList());
        return jo;
    }
}
