package com.priyanshparekh.plated.search;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<SearchResultResponse> getSearchResults(@RequestParam("query") String query) {
        SearchResultResponse searchResponse = searchService.getSearchResult(query);
        return ResponseEntity.ok(searchResponse);
    }

}
