package ztw.springboot.api.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BookDTO {
    int pages;
    private String title;
    private List<Long> authorIds;
}
